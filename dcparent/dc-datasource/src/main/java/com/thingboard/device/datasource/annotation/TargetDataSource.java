package com.thingboard.device.datasource.annotation;

import java.lang.annotation.*;

/**
 * 数据源切换的注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.METHOD })
@Documented
public @interface TargetDataSource {
	String value();
}
