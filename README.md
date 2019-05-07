# 微服务demo模板

## 环境配置

jdk：1.8.0
spring-boot-starter-parent：2.1.2.RELEASE
spring-cloud-dependencies：Greenwich.RC2

## 模块组成

1. eureka: `eureka`注册中心
2. DesDirModService:服务提供者（注册方）含有多数据源配置
3. config：简单配置中心
4. geteway:简单的网关配置