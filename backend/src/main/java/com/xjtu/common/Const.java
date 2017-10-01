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

    public static final String PROPERTIES_FILENAME = "vue-spring-demo.properties";
    public static final String READ_PROPERTIES_ERROR = "读取配置文件错误";
    public static final String FTP_SERVER_HTTP_PREFIX = "ftp.server.http.prefix";
    public static final String NO_CHILDREN_CATEGORIES = "未找到当前分类的子分类";


    public static final String USER_NOT_EXISTS = "用户名不存在";
    public static final String USER_EXISTS = "用户名已存在";

    public static final String EMAIL_EXISTS = "该邮箱已注册";

    public static final String USERNAME_OR_PASSWORD_ERROR = "用户名或密码错误";
    public static final String PASSWORD_SALT = "password.salt";
    public static final String REGISTER_SUCCESS = "注册成功";
    public static final String REGISTER_FAILED = "注册失败";
    public static final Integer KEYWORDS_LIMIT = 50;


    public interface UserRole {
        //普通用户
        int NORMAL = 0;
        //管理员
        int ADMIN = 1;
    }

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


    public interface Cart {
        String LIMIT_NUM_SUCCESS = "LIMIT_NUM_SUCCESS";
        String LIMIT_NUM_FAIL = "LIMIT_NUM_FAIL";


        String DELETE_SUCCESS = "删除成功";
        String DELETE_FAILED = "删除失败";
    }
}
