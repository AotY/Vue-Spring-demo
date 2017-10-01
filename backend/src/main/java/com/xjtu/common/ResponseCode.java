package com.xjtu.common;

/**
 * Created by LeonTao on 2017/9/22.
 * 返回状态码和描述信息
 */
public enum ResponseCode {

    SUCCESS(0, "SUCCESS"),
    ERROR(1, "ERROR"),
    ILLEGAL_ARGUMENT(2, "ILLEGAL ARGUMENT"),
    NEED_LOGIN(3, "NEED LOGIN");

    private int code;
    private String desc;

    ResponseCode(int id, String desc) {
        this.code = id;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
