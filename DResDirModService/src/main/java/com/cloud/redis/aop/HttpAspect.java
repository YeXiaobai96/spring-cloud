package com.cloud.redis.aop;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.cloud.redis.util.ResultUtil;

/**
 * AOP切面类，记录方法的调用，入参以及出参
 *
 * @author ZX
 */
@Aspect
@Component
public class HttpAspect {

    private final static Logger LOGGER = LoggerFactory.getLogger(HttpAspect.class);

    /**
     * 指定切点： 匹配com.cloud.redis.controller包及其子包下的所有类的所有方法
     */
    @Pointcut("execution(* com.cloud.redis.controller.*.*(..))")
    public void log() {
    }

    /**
     * 前置通知，方法调用前被调用
     *
     * @param joinPoint
     */
    @Before("log()")
    public void doBefore(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        // url
        LOGGER.info("url={}", request.getRequestURL());
        // method
        LOGGER.info("method={}", request.getMethod());
        // ip
        LOGGER.info("ip={}", request.getRemoteAddr());
        // class_method
        LOGGER.info("class_method={}",
                joinPoint.getSignature().getDeclaringTypeName() + "," + joinPoint.getSignature().getName());
        // args[]
        LOGGER.info("args={}", joinPoint.getArgs());
        // Signature
        LOGGER.info("Signature={}", joinPoint.getSignature());
    }

    /**
     * 环绕通知
     *
     * @param pjp
     * @return
     */
    @Around("log()")
    public Object doAround(ProceedingJoinPoint pjp) {
        try {
            Object proceed = pjp.proceed();
            return proceed;
        } catch (Throwable e) {
            LOGGER.error("运行异常，e={}", e);
            return ResultUtil.error(e.getLocalizedMessage(), 500);
        }
    }

}
