package com.mts.springboot.common.feign;

import feign.Request;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import feign.Retryer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

@Configuration
public class FeignConfig {

	/**
	 * 
	 * Desc: feign传递header
	 *
	 * @return
	 */
	@Bean
	public RequestInterceptor headerInterceptor() {
		return new RequestInterceptor() {
			@Override
			public void apply(RequestTemplate requestTemplate) {
				ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
						.getRequestAttributes();
				HttpServletRequest request = attributes.getRequest();
				Enumeration<String> headerNames = request.getHeaderNames();
				if (headerNames != null) {
					while (headerNames.hasMoreElements()) {
						String name = headerNames.nextElement();
						String values = request.getHeader(name);
						requestTemplate.header(name, values);
					}
				}
			}
		};
	}

	/**
	 * 配置请求重试
	 * 
	 */
	@Bean
	public Retryer feignRetryer() {
		return Retryer.NEVER_RETRY;
	}

	/**
	 * 设置请求超时时间 默认 public Options() { this(10 * 1000, 60 * 1000); }
	 *
	 */
	@Bean
	Request.Options feignOptions() {

		return new Request.Options(60 * 1000, 60 * 1000);
	}

	/**
	 * 打印请求日志
	 * 
	 * @return
	 */
	@Bean
	public feign.Logger.Level multipartLoggerLevel() {
		return feign.Logger.Level.FULL;
	}

}
