package com.xjtu.service.impl;

import com.alipay.api.AlipayResponse;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.demo.trade.config.Configs;
import com.alipay.demo.trade.model.ExtendParams;
import com.alipay.demo.trade.model.GoodsDetail;
import com.alipay.demo.trade.model.builder.AlipayTradePrecreateRequestBuilder;
import com.alipay.demo.trade.model.result.AlipayF2FPrecreateResult;
import com.alipay.demo.trade.service.AlipayTradeService;
import com.alipay.demo.trade.service.impl.AlipayTradeServiceImpl;
import com.alipay.demo.trade.utils.ZxingUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mysql.fabric.Server;
import com.xjtu.common.Const;
import com.xjtu.common.ResponseCode;
import com.xjtu.common.ServerResponse;
import com.xjtu.dao.*;
import com.xjtu.pojo.*;
import com.xjtu.service.IOrderService;
import com.xjtu.util.*;
import com.xjtu.vo.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by LeonTao on 2017/10/2.
 */
@Service("iOrderService")
public class OrderServiceImpl implements IOrderService {


    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private ShippingMapper shippingMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;


    @Autowired
    private PayInfoMapper payInfoMapper;

    private static AlipayTradeService tradeService;

    static {
        /** 一定要在创建AlipayTradeService之前调用Configs.init()设置默认参数
         *  Configs会读取classpath下的zfbinfo.properties文件配置信息，如果找不到该文件则确认该文件是否在classpath目录
         */
        Configs.init(Const.ZFBINFO_PROPERTIES);

        /** 使用Configs提供的默认参数
         *  AlipayTradeService可以使用单例或者为静态成员对象，不需要反复new
         */

        tradeService = new AlipayTradeServiceImpl.ClientBuilder().build();
    }


    /**
     * 创建订单
     *
     * @param userId
     * @param shippingId
     * @return
     */
    @Override
    public ServerResponse<OrderVo> createOrder(Integer userId, Integer shippingId) {
        if (shippingId == null) {
            return ServerResponse.createByError(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        // TODO: 获取购物车勾选商品
        List<Cart> cartList = cartMapper.selectedListByUserId(userId);
        if (CollectionUtils.isEmpty(cartList)) {
            return ServerResponse.createByError(Const.Order.NO_SELECTED_PRODUCT);
        }

        // TODO: 检查购物车数据并且生成OrderItem列表
        ServerResponse orderItemListResponse = assembleOrderItemList(cartList, userId);
        if (orderItemListResponse.getStatus() != 0) {
            return orderItemListResponse;
        }
        List<OrderItem> orderItemList = (List<OrderItem>) orderItemListResponse.getData();
        if (CollectionUtils.isEmpty(orderItemList)) {
            return ServerResponse.createByError(Const.Order.NO_AVAILABLE_PRODUCT);
        }

        // TODO: 生成订单
        Order order = this.assembleOrder(orderItemList, userId, shippingId);
        if (order == null) {
            return ServerResponse.createByError();
        }

        // TODO: order插入数据库
        int affectedRows = orderMapper.insert(order);
//        int affectedRows = orderMapper.insertSelective(order);

        if (affectedRows == 0) {
            return ServerResponse.createByError();
        }
        // TODO: 将orderItem插入数据库
        for (OrderItem orderItem : orderItemList) {
            orderItem.setOrderNo(order.getOrderNo());
//            orderItemMapper.insertSelective(orderItem);
        }
        // 或者使用批量插入
        affectedRows = orderItemMapper.batchInsert(orderItemList);
        if (affectedRows == 0) {
            return ServerResponse.createByError();
        }

        // TODO: 清除购物车中选中的商品
        affectedRows = cartMapper.deleteSelectedListByUserId(userId);
        if (affectedRows == 0) {
            return ServerResponse.createByError();
        }

        // TODO: 减少库存
        this.reduceProductStock(orderItemList);

        // TODO: 组装OrderVO 返回
        OrderVo orderVo = this.assembleOrderVo(order, orderItemList);
        if (orderVo == null) {
            return ServerResponse.createByError();
        }
        return ServerResponse.createBySuccess(orderVo);
    }

    /**
     * 支付订单
     *
     * @param userId
     * @param orderNo
     * @param uploadPath
     * @return
     */
    @Override
    public ServerResponse pay(Integer userId, Long orderNo, String uploadPath) {
        // 判断orderNo是否存在
        ServerResponse checkResponse = this.checkUserIdAndOrderNo(userId, orderNo);
        if (!checkResponse.isSuccess()) {
            return checkResponse;
        }

        Order order = (Order) checkResponse.getData();

        // 判断订单是否处于未支付状态
        if (order.getStatus() != Const.OrderStatus.NO_PAY.getCode()) {
            return ServerResponse.createByError(Const.Order.ORDER_CANNOT_BE_PAY);
        }

        // 返回给前端数据map
        Map<String, String> responseMap = Maps.newHashMap();
        // 支付宝，获取二维码
        responseMap.put("orderNo", String.valueOf(order.getOrderNo()));

        // 需保证商户系统端不能重复，建议通过数据库sequence生成，
        String outTradeNo = order.getOrderNo().toString();


        // (必填) 订单标题，粗略描述用户的支付目的。如“xxx品牌xxx门店当面付扫码消费”
        String subject = new StringBuilder().append("Vue-Spring-demo测试扫码支付，订单号：").append(outTradeNo).toString();


        // (必填) 订单总金额，单位为元，不能超过1亿元
        // 如果同时传入了【打折金额】,【不可打折金额】,【订单总金额】三者,则必须满足如下条件:【订单总金额】=【打折金额】+【不可打折金额】
        String totalAmount = order.getPayment().toString();

        // (可选) 订单不可打折金额，可以配合商家平台配置折扣活动，如果酒水不参与打折，则将对应金额填写至此字段
        // 如果该值未传入,但传入了【订单总金额】,【打折金额】,则该值默认为【订单总金额】-【打折金额】
        String undiscountableAmount = "0";


        // 卖家支付宝账号ID，用于支持一个签约账号下支持打款到不同的收款账号，(打款到sellerId对应的支付宝账号)
        // 如果该字段为空，则默认为与支付宝签约的商户的PID，也就是appid对应的PID
        String sellerId = "";

        // 订单描述，可以对交易或商品进行一个详细地描述，比如填写"购买商品2件共15.00元"
        String body = new StringBuilder().append("订单：").append(outTradeNo).append("购买商品共").append(totalAmount).append("元").toString();


        // 商户操作员编号，添加此参数可以为商户操作员做销售统计
        String operatorId = "test_operator_id";

        // (必填) 商户门店编号，通过门店号和商家后台可以配置精准到门店的折扣信息，详询支付宝技术支持
        String storeId = "test_store_id";

        // 业务扩展参数，目前可添加由支付宝分配的系统商编号(通过setSysServiceProviderId方法)，详情请咨询支付宝技术支持
        ExtendParams extendParams = new ExtendParams();

        //系统商编号 （默认字段）
        extendParams.setSysServiceProviderId("2088100200300400500");

        // 支付超时，定义为120分钟
        String timeoutExpress = "120m";

        // 商品明细列表，填写购买商品详细信息
        List<GoodsDetail> goodsDetailList = Lists.newArrayList();
        List<OrderItem> orderItemList = orderItemMapper.selectByUserIdAndOrderNo(userId, orderNo);

        for (OrderItem orderItem : orderItemList) {
            //String goodsId, String goodsName, long price, int quantity
            GoodsDetail goodsDetail = GoodsDetail.newInstance(orderItem.getProductId().toString(), orderItem.getProductName(),
                    BigDecimalUtil.mul(orderItem.getCurrentUnitPrice().doubleValue(), new Double(100).doubleValue()).longValue(), orderItem.getQuantity());
            goodsDetailList.add(goodsDetail);
        }

        // 创建扫码支付请求builder
        AlipayTradePrecreateRequestBuilder builder = new AlipayTradePrecreateRequestBuilder()
                .setOutTradeNo(outTradeNo).setSubject(subject).setTotalAmount(totalAmount)
                .setUndiscountableAmount(undiscountableAmount).setSellerId(sellerId).setBody(body)
                .setOperatorId(operatorId).setStoreId(storeId).setExtendParams(extendParams)
                .setTimeoutExpress(timeoutExpress)
                .setNotifyUrl(PropertiesUtil.getProperty(Const.ALIPAY_CALLBACK_URL))
                .setGoodsDetailList(goodsDetailList);


        AlipayF2FPrecreateResult result = tradeService.tradePrecreate(builder);

        switch (result.getTradeStatus()) {
            case SUCCESS:
                logger.info(Const.ALIPAY_PRE_ORDER_SUCCESS);
                AlipayTradePrecreateResponse response = result.getResponse();
                this.dumpResponse(response);

                // 获取上传文件夹
                File folder = new File(uploadPath);
                if (!folder.exists()) {
                    folder.setWritable(true);
                    folder.mkdirs();
                }
                String qrPath = String.format(uploadPath + "/qr-%s.png", response.getOutTradeNo());
                String qrFileName = String.format("qr-%s.png", response.getOutTradeNo());
                ZxingUtils.getQRCodeImge(response.getQrCode(), 256, qrPath);

                File targetFile = new File(uploadPath, qrFileName);

                try {
                    FTPUtil.uploadFile(Lists.newArrayList(targetFile));
                } catch (IOException e) {
                    logger.error(Const.UPLOAD_QR_ERROR);
                }
                logger.info("qrPath: " + qrPath);

                String qrUrl = PropertiesUtil.getProperty(Const.FTP_SERVER_HTTP_PREFIX) + targetFile.getName();
                responseMap.put("qrUrl", qrUrl);

                // 删除upload中的临时文件
                targetFile.delete();

                return ServerResponse.createBySuccess(responseMap);

            case FAILED:
                logger.error(Const.ALIPAY_PRE_ORDER_FAILED);
                return ServerResponse.createByError(Const.ALIPAY_PRE_ORDER_FAILED);
            case UNKNOWN:
                logger.error(Const.ALIPAY_PRE_ORDER_UNKNOWN);
                return ServerResponse.createByError(Const.ALIPAY_PRE_ORDER_UNKNOWN);
            default:
                logger.error(Const.ALIPAY_PRE_ORDER_DEFAULT);
                return ServerResponse.createByError(Const.ALIPAY_PRE_ORDER_DEFAULT);
        }
    }

    /**
     * 处理支付宝回调
     * @param params
     * @return
     */
    @Override
    public ServerResponse alipayCallback(Map<String, String> params) {
        // 商户订单号 (自己的）
        Long orderNo = Long.parseLong(params.get("out_trade_no"));

        // 支付宝交易号
        String tradeNo = params.get("trade_no");

        // 交易状态
        String tradeStatus = params.get("trade_status");

        // 检查订单号是否正确
        Order order = orderMapper.selectByOrderNo(orderNo);

        if (order == null) {
            return ServerResponse.createByError(Const.ALIPAY_ORDER_DOESNOT_BELOGNTO_US);
        }

        // 检查订单状态
        if (order.getStatus() >= Const.OrderStatus.PAID.getCode()) {

            // return ServerResponse.createByError(Const.ALIPAY_REPEATED_REQUEST);
            // 应该返回success，防止支付宝再次重复调用
            return ServerResponse.createBySuccess(Const.ALIPAY_REPEATED_REQUEST);
        }

        // 付款成功
        if (Const.ALIPAY_TRADE_STATUS_TRADE_SUCCESS.equals(tradeStatus)) {
            order.setPaymentTime(DateTimeUtil.strToDate(params.get("gmt_payment")));
            order.setStatus(Const.OrderStatus.PAID.getCode());
            orderMapper.updateByPrimaryKeySelective(order);
        }

        // 记录支付信息
        PayInfo payInfo = new PayInfo();
        // 网站订单号
        payInfo.setOrderNo(orderNo);
        payInfo.setPayPlatform(Const.PayPlatform.ALIPAY.getCode());
        // 支付平台订单号
        payInfo.setPlatformNumber(tradeNo);
        // 支付平台状态
        payInfo.setPlatformStatus(tradeStatus);

        int affectedRows = payInfoMapper.insert(payInfo);
        if (affectedRows > 0) {
            return ServerResponse.createBySuccess();
        }

        return ServerResponse.createByError();
    }

    /**
     * 用户订单列表（分页返回）
     * @param userId
     * @return
     */
    @Override
    public ServerResponse getOrderList(Integer userId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Order> orderList = orderMapper.selectByUserId(userId);
        List<OrderVo> orderVoList = assembleOrderVoList(orderList, userId);

        PageInfo pageInfo = new PageInfo(orderList);
        pageInfo.setList(orderVoList);
        return ServerResponse.createBySuccess(pageInfo);
    }


    /**
     * 检查订单是否是该用户的
     * @param userId
     * @param orderNo
     * @return
     */
    private ServerResponse checkUserIdAndOrderNo(Integer userId, Long orderNo) {
        if (orderNo == null) {
            return ServerResponse.createByError(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }

        // 检查订单是否是当前用户的
        Order order = orderMapper.selectByUserIdAndOrderNo(userId, orderNo);
        if (order == null) {
            return ServerResponse.createByError(Const.Order.ORDERNO_DOESNOT_BELONGTO_USER);
        }
        return ServerResponse.createBySuccess(order);
    }

    /**
     * 获取订单详情
     * @param userId
     * @param orderNo
     * @return
     */
    @Override
    public ServerResponse getOrderDetail(Integer userId, Long orderNo) {

        ServerResponse checkResponse = this.checkUserIdAndOrderNo(userId, orderNo);
        if (!checkResponse.isSuccess()) {
            return checkResponse;
        }

        Order order = (Order) checkResponse.getData();

        List<OrderItem> orderItemList = orderItemMapper.selectByOrderNo(orderNo);
        OrderVo orderVo = this.assembleOrderVo(order, orderItemList);
        return ServerResponse.createBySuccess(orderVo);
    }


    /**
     * 取消用户订单
     * @param userId
     * @param orderNo
     * @return
     */
    @Override
    public ServerResponse cancelOrder(Integer userId, Long orderNo) {
        ServerResponse checkResponse = this.checkUserIdAndOrderNo(userId, orderNo);
        if (!checkResponse.isSuccess()) {
            return checkResponse;
        }
        Order order = (Order) checkResponse.getData();

        order.setStatus(Const.OrderStatus.CANCELED.getCode());
        int affectedRows = orderMapper.updateByPrimaryKeySelective(order);
        if (affectedRows > 0) {
            return ServerResponse.createBySuccess();
        }
        return ServerResponse.createByError();
    }


    /**
     * 用户删除订单
     * @param userId
     * @param orderNo
     * @return
     */
    @Override
    public ServerResponse deleteOrder(Integer userId, Long orderNo) {
        ServerResponse checkResponse = this.checkUserIdAndOrderNo(userId, orderNo);
        if (!checkResponse.isSuccess()) {
            return checkResponse;
        }
        Order order = (Order) checkResponse.getData();

        // 只能删除已经取消的、交易关闭、交易成功的订单
        if (
                order.getStatus() == Const.OrderStatus.CANCELED.getCode() ||
                order.getStatus() == Const.OrderStatus.ORDER_CLOSE.getCode() ||
                order.getStatus() == Const.OrderStatus.ORDER_SUCCESS.getCode()
                ) {
            order.setStatus(Const.OrderStatus.ORDER_DELETED.getCode());
            int affectedRows = orderMapper.updateByPrimaryKeySelective(order);
            if (affectedRows > 0) {
                return ServerResponse.createBySuccess();
            }
        }
        return ServerResponse.createByError();
    }

    /**
     * 组装OrderVo List
     * @param orderList
     * @param userId
     * @return
     */
    private List<OrderVo> assembleOrderVoList(List<Order> orderList, Integer userId) {
        List<OrderVo> orderVoList = Lists.newArrayList();
        if (orderList.size() > 0) {
            for (Order order: orderList) {
                // 不返回用户已经删除的订单
                if (order.getStatus() == Const.OrderStatus.ORDER_DELETED.getCode())
                    continue;
                List<OrderItem> orderItemList = orderItemMapper.selectByOrderNo(order.getOrderNo());
                OrderVo orderVo = this.assembleOrderVo(order, orderItemList);
                orderVoList.add(orderVo);
            }
        }
        return orderVoList;
    }


    // 简单Alipay打印应答
    private void dumpResponse(AlipayResponse response) {
        if (response != null) {
            logger.info(String.format("code:%s, msg:%s", response.getCode(), response.getMsg()));
            if (StringUtils.isNotEmpty(response.getSubCode())) {
                logger.info(String.format("subCode:%s, subMsg:%s", response.getSubCode(),
                        response.getSubMsg()));
            }
            logger.info("body:" + response.getBody());
        }
    }

    /**
     * 组装OrderVo对象
     *
     * @param order
     * @param orderItemList
     * @return
     */
    private OrderVo assembleOrderVo(Order order, List<OrderItem> orderItemList) {
        OrderVo orderVo = new OrderVo();

        orderVo.setPayment(order.getPayment());
        orderVo.setOrderNo(order.getOrderNo());
        orderVo.setPostage(order.getPostage());


        // 支付方式
        orderVo.setPaymentType(order.getPaymentType());
        orderVo.setPaymentTypeDesc(Const.PaymentType.codeOf(order.getPaymentType()).getValue());


        // 订单状态
        orderVo.setStatus(order.getStatus());
        orderVo.setStatusDesc(Const.OrderStatus.codeOf(order.getStatus()).getValue());

        // 设置图片服务器
        orderVo.setImageHost(PropertiesUtil.getProperty(Const.FTP_SERVER_HTTP_PREFIX));


        // 收货地址
        Shipping shipping = shippingMapper.selectByPrimaryKey(order.getShippingId());
        orderVo.setShippingId(order.getShippingId());
        if (shipping != null) {
            orderVo.setReceiverName(shipping.getReceiverName());
            orderVo.setShippingVo(AssembleUtil.assembleShippingVo(shipping));
        }


        // 订单时间
        orderVo.setPaymentTime(DateTimeUtil.dateToStr(order.getPaymentTime()));
        orderVo.setSendTime(DateTimeUtil.dateToStr(order.getSendTime()));
        orderVo.setEndTime(DateTimeUtil.dateToStr(order.getEndTime()));
        orderVo.setCreateTime(DateTimeUtil.dateToStr(order.getCreateTime()));
        orderVo.setCloseTime(DateTimeUtil.dateToStr(order.getCloseTime()));


        // 子订单vo
        List<OrderItemVo> orderItemVoList = Lists.newArrayList();
        for (OrderItem orderItem : orderItemList) {
            OrderItemVo orderItemVo = this.assembleOrderItemVo(orderItem);
            orderItemVoList.add(orderItemVo);
        }
        orderVo.setOrderItemVoList(orderItemVoList);

        return orderVo;
    }


    /**
     * 减少商品库存
     *
     * @param orderItemList
     */
    private void reduceProductStock(List<OrderItem> orderItemList) {
        for (OrderItem orderItem : orderItemList) {
            Product product = productMapper.selectByPrimaryKey(orderItem.getProductId());
            product.setStock(product.getStock() - orderItem.getQuantity());
            productMapper.updateByPrimaryKeySelective(product);
        }
    }

    /**
     * 生成订单（入库）
     */
    private Order assembleOrder(List<OrderItem> orderItemList, Integer userId, Integer shippingId) {
        Order order = new Order();

        // TODO: 检查收货地址是否为当前用户的
        Shipping shipping = shippingMapper.selectByUserIdAndShippingId(userId, shippingId);
        if (shipping == null) {
            return null;
        }

        // TODO: 计算总价格
        BigDecimal payment = this.getOrderItemListPayment(orderItemList);

        // TODO: 生成订单号（一定要注意）
        Long orderNo = OrderUtil.generateOrderNo();

        order.setUserId(userId);
        order.setShippingId(shippingId);
        order.setOrderNo(orderNo);
        order.setPayment(payment);
        order.setPostage(Const.Order.postage);
        // 默认设置为未付款状态
        order.setStatus(Const.OrderStatus.NO_PAY.getCode());
        // 默认是在线支付
        order.setPaymentType(Const.PaymentType.ONLINE_PAY.getCode());

        // TODO: 设置付款时间、发货时间等等(以后设置)
        return order;
    }

    /**
     * 计算订单总价格
     *
     * @param orderItemList
     * @return
     */
    private BigDecimal getOrderItemListPayment(List<OrderItem> orderItemList) {
        BigDecimal payment = new BigDecimal('0');
        for (OrderItem orderItem : orderItemList) {
            payment = BigDecimalUtil.add(payment.doubleValue(), orderItem.getTotalPrice().doubleValue());
        }
        return payment;
    }

    /**
     * 组装OrderItemVo
     *
     * @param orderItem
     * @return
     */
    private OrderItemVo assembleOrderItemVo(OrderItem orderItem) {
        OrderItemVo orderItemVo = new OrderItemVo();
        orderItemVo.setTotalPrice(orderItem.getTotalPrice());
        orderItemVo.setCurrentUnitPrice(orderItem.getCurrentUnitPrice());
        orderItemVo.setOrderNo(orderItem.getOrderNo());
        orderItemVo.setProductId(orderItem.getProductId());
        orderItemVo.setQuantity(orderItem.getQuantity());
        orderItemVo.setProductImage(orderItem.getProductImage());
        orderItemVo.setProductName(orderItem.getProductName());
        orderItemVo.setCreateTime(DateTimeUtil.dateToStr(orderItem.getCreateTime()));
        return orderItemVo;
    }


    /**
     * 组装入库的OrderItem
     *
     * @param userId
     * @return
     */
    private ServerResponse assembleOrderItemList(List<Cart> cartList, Integer userId) {
        List<OrderItem> orderItemList = Lists.newArrayList();

        if (cartList.size() > 0) {
            for (Cart cart : cartList) {
                OrderItem orderItem = new OrderItem();

                Integer productId = cart.getProductId();

                Product product = productMapper.selectByPrimaryKey(productId);
                if (product == null) {
                    return ServerResponse.createByError(String.format(Const.Order.PRODUCT_NON_EXISTENT, productId));
                }

                //TODO: 检查在售状态和库存
                if (cart.getQuantity() > product.getStock()) {
                    return ServerResponse.createByError(String.format(Const.Order.PRODUCT_OUT_OF_STOCK, product.getName()));
                }
                if (product.getStatus() != Const.ProductStatusEnum.ONSLAE.getStatus()) {
                    return ServerResponse.createByError(String.format(Const.Order.PRODUCT_OFF_THE_MARKET, product.getName()));
                }

                orderItem.setUserId(userId);
                orderItem.setProductId(productId);
                orderItem.setQuantity(cart.getQuantity());
                orderItem.setProductName(product.getName());
                orderItem.setCurrentUnitPrice(product.getPrice());
                orderItem.setProductImage(product.getMainImage());
                orderItem.setTotalPrice(BigDecimalUtil.mul(cart.getQuantity().doubleValue(), product.getPrice().doubleValue()));

                orderItemList.add(orderItem);
            }
        }

        return ServerResponse.createBySuccess(orderItemList);
    }
}
