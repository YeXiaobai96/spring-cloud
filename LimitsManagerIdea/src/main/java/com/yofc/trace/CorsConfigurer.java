package com.yofc.trace;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.yofc.trace.interceptor.AuthorizationInterceptor;

@Configuration
public class CorsConfigurer implements WebMvcConfigurer {

    /**
     * 跨域设置
     */
    //@Override
   /* public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**"); // 允许所有IP访问，可在确定
    }*/

    /**
     * 解决@autowired无法注入
     *
     * @return
     */
    @Bean
    public HandlerInterceptor getAuthorizationInterceptor() {
        return new AuthorizationInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getAuthorizationInterceptor()).addPathPatterns("/**")
                .excludePathPatterns("/admin/login", "/admin/register", "/trace/**", "/image/**", "/role/listRole",
                        "/oauth/**", "/swa**/**", "/webjars/**", "/v2/**");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/statics/**").addResourceLocations("classpath*:/statics/");
        // swagger
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }


}
