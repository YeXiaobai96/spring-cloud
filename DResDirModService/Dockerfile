# 依赖的jre环境
FROM 192.168.3.34:80/library/openjdk:8-jre-alpine
# 创建人员和邮箱地址
MAINTAINER xxx xxxx@qq.com
# 复制jar到Docker镜像中，注意jar名称必须使用小写
COPY target/DResDirModService-0.0.1-SNAPSHOT.jar /redis-service.jar
# 映射项目端口
EXPOSE 8082
# 项目启动命令
ENTRYPOINT ["java", "-jar", "/redis-service.jar"]