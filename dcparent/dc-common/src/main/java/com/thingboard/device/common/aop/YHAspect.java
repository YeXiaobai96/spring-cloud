package com.thingboard.device.common.aop;

import com.thingboard.device.common.model.Result;
import com.thingboard.device.common.exception.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Aspect
@Configuration
public class YHAspect {



    @Pointcut("execution(* com.thingboard.device.base.controller.*Controller.*(..))")
    public void excudeService() {

    }




    @Around("excudeService()")
    public Object doAround(ProceedingJoinPoint pjp) throws Exception{
        Throwable ex=null;
        Object result = null;
        try {
            result = pjp.proceed();
            if (result instanceof Result) {
                return result;
            }
            return new Result(result);
        } catch (Throwable e) {
            ex = e;
            if (e instanceof BaseException) {
                throw (BaseException)e;
            } else {
                result = new Result(false, 500, "System error", e.getLocalizedMessage());
            }
        }

        if (ex!=null && ex instanceof  Exception){
            throw new Exception(ex);
        }
        return result;
    }

}
