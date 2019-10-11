package com.mts.springboot.apifeign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

//扫描该包下所有配置
@ComponentScan({ "com.mts.springboot" })
//扫描接口所在包或者父包
@EnableFeignClients(basePackages = { "com.mts.springboot" })
@EnableEurekaClient
@SpringBootApplication
public class ApiFeignApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiFeignApplication.class, args);
    }

}
