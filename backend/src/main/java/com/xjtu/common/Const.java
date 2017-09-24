package com.xjtu.common;

/**
 * Created by LeonTao on 2017/9/22.
 * 一些常量
 */
public class Const {

    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String EMAIL = "email";

    public static final String PRODUCTDELETED = "商品已删除";
    public static final String PRODUCTNOTONSALE = "商品已下架";

    public static final String PROPERTIESFILENAME = "vue-spring-demo.properties";
    public static final String READPROPERTIESERROR = "读取配置文件错误";
    public static final java.lang.String FTPSERVERHTTPPREFIX = "ftp.server.http.prefix";


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
}
