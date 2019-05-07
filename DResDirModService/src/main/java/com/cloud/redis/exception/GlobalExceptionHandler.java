package com.cloud.redis.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cloud.redis.model.Result;
import com.cloud.redis.util.ResultUtil;

/**
 * 全局异常捕捉处理类
 *
 * @author ZX
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    private static Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 应用到所有@RequestMapping注解方法，在其执行之前初始化数据绑定器
     *
     * @param binder
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
    }

    /**
     * 把值绑定到Model中，使全局@RequestMapping可以获取到该键值对
     *
     * @param model
     */
    @ModelAttribute
    public void addUser(Model model) {
        model.addAttribute("msg", "错误信息");
    }

    /**
     * 1、定义全局异常处理，value属性可以过滤拦截条件，此处拦截所有的Exception
     * 2、应用到所有@RequestMapping注解的方法，在其抛出Exception异常时执行
     * 3、判断错误是否是已定义的已知错误，不是则由未知错误代替，同时记录在log中
     *
     * @param e
     * @return
     * @throws Exception
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result allExceptionHandler(Exception e) {
        // 已定义的已知错误
        if (e instanceof DescribeException) {
            DescribeException exception = (DescribeException) e;
            return ResultUtil.error(exception.getMessage(), exception.getCode());
        }

        // 未知异常
        LOGGER.error("【未知异常】={}", e);
        return ResultUtil.error(e.getMessage(), 500);
    }
}
