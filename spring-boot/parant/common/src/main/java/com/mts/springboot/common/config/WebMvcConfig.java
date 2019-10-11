package com.mts.springboot.common.config;


import com.mts.springboot.common.xss.XssFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import java.util.ArrayList;
import java.util.List;

/**
 * Spring MVC 配置
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	protected Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private SqlInjectInterceptor sqlInjectInterceptor;

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		logger.debug("--> 静态资源");
		registry.addResourceHandler("/statics/**").addResourceLocations("classpath*:/statics/");
		// swagger
		registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
	}

	// 添加拦截器 可添加自定义拦截器
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		logger.debug("--> 添加拦截器");
		registry.addInterceptor(sqlInjectInterceptor).addPathPatterns("/**");
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Bean
	public FilterRegistrationBean xssFilterRegistration() {
		FilterRegistrationBean registration = new FilterRegistrationBean();
		registration.setDispatcherTypes(DispatcherType.REQUEST);
		registration.setFilter(new XssFilter());
		registration.addUrlPatterns("/*");
		//过滤器免过滤
		//registration.addInitParameter("exclusions","/druid/*");
		registration.setName("xssFilter");
		registration.setOrder(Integer.MAX_VALUE);
		return registration;
	}

	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", buildConfig());
		return new CorsFilter(source);
	}

	private CorsConfiguration buildConfig() {

		logger.debug("--> 增加跨域访问");
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		List<String> list = new ArrayList<>();
		list.add("*");
		corsConfiguration.setAllowedOrigins(list);
		/*
		 * 请求常用的三种配置，*代表允许所有，也可以自定义属性（比如header只能带什么，只能是post方式等等）
		 */
		corsConfiguration.addAllowedOrigin("*");
		corsConfiguration.addAllowedHeader("*");
		corsConfiguration.addAllowedMethod("*");
		return corsConfiguration;
	}

	/**
	 * 用于处理编码问题
	 *
	 * @return
	 */
	@Bean
	public Filter characterEncodingFilter() {
		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
		characterEncodingFilter.setEncoding("UTF-8");
		characterEncodingFilter.setForceEncoding(true);
		return characterEncodingFilter;
	}

}
