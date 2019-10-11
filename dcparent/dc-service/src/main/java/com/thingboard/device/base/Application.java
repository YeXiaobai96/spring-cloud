package com.thingboard.device.base;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;

//扫描该包下所有配置
@ComponentScan({ "com.thingboard.device" })
//Servlet、Filter、Listener可以直接通过@WebServlet、@WebFilter、@WebListener注解自动注册
@ServletComponentScan(value = "com.thingboard.device")
// mapper 接口类扫描包配置
@MapperScan("com.thingboard.device.dao.mapper")
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
