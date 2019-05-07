package com.cloud.redis.exception;

/**
 * 自定义异常描述类
 *
 * @author ZX
 */
public class DescribeException extends Exception {

    private static final long serialVersionUID = 1L;

    private Integer code;

    private String msg;

    /**
     * 继承Exception，加入异常状态值
     *
     * @param exceptionEnum
     */
    public DescribeException(ExceptionEnum exceptionEnum) {
        super(exceptionEnum.getMsg());
        this.code = exceptionEnum.getCode();
    }

    /**
     * 自定义错误信息
     *
     * @param message
     * @param code
     */
    public DescribeException(String msg, Integer code) {
        super(msg);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
