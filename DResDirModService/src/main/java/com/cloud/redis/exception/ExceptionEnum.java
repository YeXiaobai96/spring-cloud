package com.cloud.redis.exception;

/**
 * 异常枚举类
 *
 * @author ZX
 */
public enum ExceptionEnum {
    UNKNOW_ERROR(-1, "未知异常"), PARAM_NULL(400, "参数不能为空");

    private Integer code;

    private String msg;

    ExceptionEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
