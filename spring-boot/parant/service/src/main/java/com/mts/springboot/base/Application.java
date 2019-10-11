package com.mts.springboot.base;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

//扫描该包下所有配置
@ComponentScan({ "com.mts.springboot" })
//Servlet、Filter、Listener可以直接通过@WebServlet、@WebFilter、@WebListener注解自动注册
@ServletComponentScan(value = "com.mts.springboot")
// mapper 接口类扫描包配置
@MapperScan("com.mts.springboot.dao.mapper")
//扫描接口所在包或者父包
@EnableFeignClients(basePackages = { "com.mts.springboot" })
@EnableEurekaClient
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
