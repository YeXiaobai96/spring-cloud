package com.cloud.redis.util;

import com.cloud.redis.model.Result;
import com.cloud.redis.exception.ExceptionEnum;

/**
 * 返回报文工具类
 *
 * @author ZX
 */
public class ResultUtil {

    /**
     * 返回成功，传入返回体具体出参
     *
     * @param code
     * @param data
     * @return
     */
    public static Result success(Integer code, Object data) {
        Result result = new Result();
        result.setFlag(true);
        result.setMsg("success");
        result.setCode(code);
        result.setData(data);
        return result;
    }

    /**
     * 自定义错误信息
     *
     * @param code
     * @param msg
     * @return
     */
    public static Result error(String msg, Integer code) {
        Result result = new Result();
        result.setFlag(false);
        result.setMsg(msg);
        result.setCode(code);
        result.setData(null);
        return result;
    }

    /**
     * 返回异常信息，在已知的范围内
     *
     * @param ex
     * @return
     */
    public static Result error(ExceptionEnum ex) {
        Result result = new Result();
        result.setFlag(false);
        result.setMsg(ex.getMsg());
        result.setCode(ex.getCode());
        result.setData(null);
        return result;
    }
}
