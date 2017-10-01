package com.xjtu.service.impl;

import com.google.common.collect.Lists;
import com.xjtu.common.Const;
import com.xjtu.common.ResponseCode;
import com.xjtu.common.ServerResponse;
import com.xjtu.dao.CartMapper;
import com.xjtu.dao.ProductMapper;
import com.xjtu.pojo.Cart;
import com.xjtu.pojo.Product;
import com.xjtu.service.ICartService;
import com.xjtu.util.BigDecimalUtil;
import com.xjtu.util.PropertiesUtil;
import com.xjtu.vo.CartProductVo;
import com.xjtu.vo.CartVo;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by LeonTao on 2017/9/28.
 */
@Service("iCartService")
public class CartServiceImpl implements ICartService {

    @Autowired
    private CartMapper cartMapper;


    @Autowired
    private ProductMapper productMapper;

    /**
     * 获取购物车商品数量
     *
     * @param userId
     * @return
     */
    @Override
    public ServerResponse<Integer> count(Integer userId) {
        Integer quantitySum = cartMapper.selectQuantitySumByUserId(userId);
        return ServerResponse.createBySuccess(quantitySum);
    }

    /**
     * 获取购物车商品列表
     *
     * @param userId
     * @return
     */
    @Override
    public ServerResponse<CartVo> list(Integer userId) {
        CartVo cartVo = this.getCartVo(userId);
        return ServerResponse.createBySuccess(cartVo);
    }


    /**
     * 添加购物车记录
     *
     * @param userId
     * @param productId
     * @param quantity
     * @return
     */
    @Override
    public ServerResponse add(Integer userId, Integer productId, Integer quantity) {

        // 判断之前是否添加该商品到购物车
        Cart cart = cartMapper.selectByUserIdAndProductId(userId, productId);
        Product product = productMapper.selectByPrimaryKey(productId);

        if (quantity < 1) {
            quantity = 1;
        }
        int effectRow = 0;
        if (cart != null) {
            // 更新，检查库存
            Integer updateQuantity = quantity + cart.getQuantity();
            effectRow = this.updateQuantity(cart.getId(), product.getStock(), updateQuantity);
        }
        // 添加
        else {
            Cart insertCart = new Cart();
            if (quantity > product.getStock())
                quantity = product.getStock();
            insertCart.setQuantity(quantity);
            // 默认为选中
            insertCart.setChecked(true);

            insertCart.setProductId(productId);
            insertCart.setUserId(userId);
            effectRow = cartMapper.insert(insertCart);
        }
        if (effectRow > 0) {
            return ServerResponse.createBySuccess();
        }
        return ServerResponse.createByError();
    }


    /**
     * 反转全选
     *
     * @param userId
     * @return
     */
    @Override
    public ServerResponse reverseAllSelect(Integer userId, Boolean checked) {
        if (checked == null) {
            return ServerResponse.createByError(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        int count = cartMapper.reverseAllSelect(userId, checked);
        if (count > 0) {
            return ServerResponse.createBySuccess();
        }
        return ServerResponse.createByError();
    }

    /**
     * 反转选择
     *
     * @param userId
     * @return
     */
    @Override
    public ServerResponse reverseSelect(Integer userId, Integer productId, Boolean checked) {
        if (checked == null || productId == null) {
            return ServerResponse.createByError(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        int count = cartMapper.reverseSelect(userId, productId, checked);
        if (count > 0) {
            return ServerResponse.createBySuccess();
        }
        return ServerResponse.createByError();
    }

    /**
     *
     */
    @Override
    public ServerResponse changeQuantity(Integer userId, Integer productId, Integer quantity) {
        if (quantity == null || productId == null) {
            return ServerResponse.createByError(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Cart cart = cartMapper.selectByUserIdAndProductId(userId, productId);
        Product product = productMapper.selectByPrimaryKey(productId);
        if (quantity < 1) {
            quantity = 1;
        }
        int effectRow = 0;
        if (cart != null && product != null) {
            // 更新，检查库存
            effectRow = this.updateQuantity(cart.getId(), product.getStock(), quantity);
            if (effectRow > 0)
                return ServerResponse.createBySuccess();
        }
        return ServerResponse.createByError();

    }

    /**
     * 通过ID删除购物车记录
     *
     * @param userId
     * @return
     * @Param productId
     */
    @Override
    public ServerResponse delete(Integer userId, Integer productId) {
        if (productId == null) {
            return ServerResponse.createByError(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        int effectRow = cartMapper.deleteByUserIdAndProductId(userId, productId);
        if (effectRow > 0)
            return ServerResponse.createBySuccess(Const.Cart.DELETE_SUCCESS);
        return ServerResponse.createByError(Const.Cart.DELETE_FAILED);

    }

    /**
     * 删除选中商品
     *
     * @param userId
     * @return
     */
    @Override
    public ServerResponse deleteSelect(Integer userId) {
        List<Cart> cartList = cartMapper.selectListByUserId(userId);
        int effectRow = 0;
        for (Cart cart : cartList) {
            // 如果选中，则删除
            if (cart.getChecked()) {
                effectRow += cartMapper.deleteByPrimaryKey(cart.getId());
            }
        }
        if (effectRow > 0)
            return ServerResponse.createBySuccess(Const.Cart.DELETE_SUCCESS);
        return ServerResponse.createByError(Const.Cart.DELETE_FAILED);

    }

    private int updateQuantity(Integer id, Integer stock, Integer quantity) {
        int effectRow = 0;
        Cart updateCart = new Cart();
        updateCart.setId(id);
        if (quantity > stock)
            quantity = stock;
        updateCart.setQuantity(quantity);
        effectRow = cartMapper.updateByPrimaryKeySelective(updateCart);
        return effectRow;
    }





    /**
     * 获取返回给客户端的CartVo对象
     *
     * @return
     */
    private CartVo getCartVo(Integer userId) {
        CartVo cartVo = new CartVo();
        //
        List<Cart> cartList = cartMapper.selectListByUserId(userId);
        List<CartProductVo> cartProductVoList = Lists.newArrayList();
        // 计算总金额
        BigDecimal totalPrice = new BigDecimal("0");
        // 记录是否全选
        Boolean isAllChecked = true;

        if (CollectionUtils.isNotEmpty(cartList)) {
            // 如果不为空，则组装CarVo对象
            for (Cart cart : cartList) {

                CartProductVo cartProductVo = new CartProductVo();
                cartProductVo.setId(cart.getId());
                cartProductVo.setUserId(userId);

                Integer productId = cart.getProductId();
                cartProductVo.setProductId(productId);
                Product product = productMapper.selectByPrimaryKey(productId);

                // 如果商品不为空
                if (product != null) {
                    cartProductVo.setProductName(product.getName());
                    cartProductVo.setProductSubtitle(product.getSubtitle());
                    cartProductVo.setProductPrice(product.getPrice());
                    cartProductVo.setProductMainImage(product.getMainImage());
                    cartProductVo.setProductStatus(product.getStatus());
                    cartProductVo.setProductStock(product.getStock());

                    // 如果当初加入购物车的商品数量大于现在商品的库存，就需要调整购物车内商品数量
                    int buyLimitCount = 0;
                    if (cart.getQuantity() > product.getStock()) {
                        buyLimitCount = product.getStock();
                        cartProductVo.setLimitQuantity(Const.Cart.LIMIT_NUM_SUCCESS);

                        // 更新购物车中商品数量，选择性更新字段
                        Cart cartForQuantity = new Cart();
                        cartForQuantity.setQuantity(buyLimitCount);
                        cartForQuantity.setId(cart.getId());
                        cartMapper.updateByPrimaryKeySelective(cartForQuantity);
                    } else {
                        buyLimitCount = cart.getQuantity();
                        cartProductVo.setLimitQuantity(Const.Cart.LIMIT_NUM_FAIL);
                    }

                    // 设置购物车中商品数量（判断后）
                    cartProductVo.setQuantity(buyLimitCount);


                    // 计算当前商品总价格， 商品价格 * 商品数量
//                    cartProductVo.setProductTotalPrice(BigDecimalUtil.mul(product.getPrice().doubleValue(),cartProductVo.getQuantity()));
                    cartProductVo.setProductTotalPrice(BigDecimalUtil.mul(product.getPrice().doubleValue(), cartProductVo.getQuantity()));


                    // 是否选择当前商品
                    cartProductVo.setProductChecked(cart.getChecked());

                    // 计算购物车中商品总价格
                    if (cartProductVo.getProductChecked()) {
                        totalPrice = BigDecimalUtil.add(totalPrice.doubleValue(), cartProductVo.getProductTotalPrice().doubleValue());
                    } else {
                        isAllChecked = false;
                    }
                    cartProductVoList.add(cartProductVo);
                }
            }
        }
        cartVo.setCartProductVoList(cartProductVoList);
        cartVo.setImageHost(PropertiesUtil.getProperty(Const.FTP_SERVER_HTTP_PREFIX));
        cartVo.setCartTotalPrice(totalPrice);
        cartVo.setAllChecked(isAllChecked);
        return cartVo;
    }


}
