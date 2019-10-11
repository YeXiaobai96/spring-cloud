package com.thingboard.device.common.config;

/**
 * 异常枚举类
 */
public enum ExceptionEnum {
    UNKNOW_ERROR(-1, "未知异常，请联系管理员"),
    PARAM_NULL(400, "参数不能为空"),
    ERROR(4000, "Error"),
    PARAM_ERROR(1400, "参数错误"),
    MOBILE_EXIST(1010, "手机号已注册"),
    LOGCODE_PWD_LOGIN_ERROR(10012, "手机号或密码不正确"),
    Mobile_FORMAT_ERROR(10011, "手机号格式不正确"),
    THIS_TIME_NO_DATA(10013, "该时间段无数据"),
    THIS_TIME_NO_inventory(10014, "该天无库存");
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
