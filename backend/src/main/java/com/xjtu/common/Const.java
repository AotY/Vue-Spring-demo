package com.xjtu.common;

import com.google.common.collect.Sets;

import java.util.Set;

/**
 * Created by LeonTao on 2017/9/22.
 * 一些常量
 */
public class Const {

    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String EMAIL = "email";

    public static final String CURRENT_USER = "cur_user";

    public static final String PRODUCT_DELETED = "商品已删除";
    public static final String PRODUCT_NOT_ON_SALE = "商品已下架";
    public static final String NO_CHILDREN_CATEGORIES = "未找到当前分类的子分类";

    public static final String PROPERTIES_FILENAME = "vue-spring-demo.properties";
    public static final String READ_PROPERTIES_ERROR = "读取配置文件错误";
    public static final String FTP_SERVER_HTTP_PREFIX = "ftp.server.http.prefix";
    public static final String ALIPAY_CALLBACK_URL = "alipay.callback.url";
    public static final String ZFBINFO_PROPERTIES = "zfbinfo.properties";


    public static final String USER_NOT_EXISTS = "用户名不存在";
    public static final String USER_EXISTS = "用户名已存在";
    public static final String EMAIL_EXISTS = "该邮箱已注册";
    public static final String USERNAME_OR_PASSWORD_ERROR = "用户名或密码错误";
    public static final String PASSWORD_SALT = "password.salt";
    public static final String REGISTER_SUCCESS = "注册成功";
    public static final String REGISTER_FAILED = "注册失败";


    // 支付宝相关提示
    public static final String ALIPAY_PRE_ORDER_SUCCESS = "支付宝预下单成功。";
    public static final String ALIPAY_PRE_ORDER_FAILED = "支付宝预下单失败。";
    public static final String ALIPAY_PRE_ORDER_UNKNOWN = "系统异常，预下单状态未知。";
    public static final String ALIPAY_PRE_ORDER_DEFAULT = "不支持的交易状态，交易返回异常。";
    public static final String ALIPAY_CALLBACK_ERROR = "支付宝验证回调异常";
    public static final String ALIPAY_RESPONSE_SUCCESS = "success";
    public static final String ALIPAY_RESPONSE_FAILED = "failed";
    public static final String ALIPAY_TRADE_STATUS_WAIT_BUYER_PAY = "WAIT_BUYER_PAY";
    public static final String ALIPAY_TRADE_STATUS_TRADE_SUCCESS = "TRADE_SUCCESS";
    public static final String ALIPAY_ORDER_DOESNOT_BELOGNTO_US = "不是本网址订单";
    public static final String ALIPAY_REPEATED_REQUEST = "重复调用";


    // 搜索提示返回词数量
    public static final Integer KEYWORDS_LIMIT = 50;

    /*
    WAIT_BUYER_PAY	交易创建，等待买家付款
    TRADE_CLOSED	未付款交易超时关闭，或支付完成后全额退款
    TRADE_SUCCESS	交易支付成功
    TRADE_FINISHED	交易结束，不可退款
    */

    public static final String UPLOAD_QR_ERROR = "上传二维码到FTP异常";

    public static final String ILLEGAL_REQUEST = "非法请求";
    public static final String FTP_SERVER_IP = "ftp.server.ip";
    public static final String FTP_USER = "ftp.user";
    public static final String FTP_PASS = "ftp.pass";
    public static final String FTP_PORT = "ftp.port";
    public static final String FTP_REMOTE_PATH = "ftp.remote_path";


    /**
     * 用户
     */
    public interface UserRole {
        //普通用户
        int NORMAL = 0;
        //管理员
        int ADMIN = 1;
    }


    /**
     * 商品状态枚举
     */
    public enum ProductStatusEnum {
        ONSLAE(1, "在线");
        private int status;
        private String value;

        ProductStatusEnum(int status, String value) {
            this.status = status;
            this.value = value;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    /**
     * 定义商品排序方式
     */
    public interface ProductListOrderBy {
        Set<String> PRICE_ASC_DESC = Sets.newHashSet("price_desc", "price_asc");
    }


    /**
     * 购物车提示
     */
    public interface Cart {
        String LIMIT_NUM_SUCCESS = "LIMIT_NUM_SUCCESS";
        String LIMIT_NUM_FAIL = "LIMIT_NUM_FAIL";


        String DELETE_SUCCESS = "删除成功";
        String DELETE_FAILED = "删除失败";
        int ALL = 1;
        int CHECKED = 2;
    }

    /**
     * 地址提示
     */
    public interface Shipping {
        String No_Exists = "地址不存在";
    }


    /**
     * 订单提示
     */
    public interface Order {
        int postage = 0;
        String NO_SELECTED_PRODUCT = "没有选择商品";
        String NO_AVAILABLE_PRODUCT = "没有可购买商品";
        String PRODUCT_NON_EXISTENT = "商品编号: %d不存在";
        String PRODUCT_OUT_OF_STOCK = "%s库存不足";
        String PRODUCT_OFF_THE_MARKET = "%s已下架";
        String ORDERNO_DOESNOT_BELONGTO_USER = "用户没有该订单";
        String ORDER_CANNOT_BE_PAY = "订单不可支付";
    }


    /**
     * 订单状态枚举
     */
    //0-已取消-10-未付款，20-已付款，40-已发货，50-交易成功，60-交易关闭'
    public enum OrderStatus {
        CANCELED(0, "已取消"),
        NO_PAY(10, "未付款"),
        PAID(20, "已付款"),
        SHIPPED(40, "已发货"),
        ORDER_SUCCESS(40, "交易成功"),
        ORDER_CLOSE(40, "交易关闭");

        private int code;
        private String value;

        OrderStatus(int code, String value) {
            this.code = code;
            this.value = value;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public static OrderStatus codeOf(int code) {
            for (OrderStatus orderStatus: values()) {
                if (orderStatus.getCode() == code) {
                    return orderStatus;
                }
            }
            throw new RuntimeException("没有对应的订单状态");
        }
    }

    /**
     * 支付平台枚举
     */
    public enum PayPlatform {
        ALIPAY(1, "支付宝");
        private int code;
        private String value;

        PayPlatform(int code, String value) {
            this.code = code;
            this.value = value;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

    }


    /**
     * 支付方式枚举
     */
    public enum PaymentType {
        ONLINE_PAY(1, "在线支付");
        private int code;
        private String value;

        PaymentType(int code, String value) {
            this.code = code;
            this.value = value;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public static PaymentType codeOf(int code) {
            for (PaymentType paymentType: values()) {
                if (paymentType.getCode() == code) {
                    return paymentType;
                }
            }
            throw new RuntimeException("没有对应的支付方式");
        }

    }

















}
