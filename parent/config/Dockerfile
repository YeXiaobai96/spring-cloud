#依赖的jre环境
FROM openjdk:8-jre
#创建人员和邮箱地址
MAINTAINER wm xxxx@qq.com
#复制jar到Docker镜像中，注意jar名称必须使用小写
COPY target/config-1.0-SNAPSHOT.jar /config.jar
#映射项目端口
EXPOSE 8033
#项目启动命令
ENTRYPOINT ["java","-jar","/config.jar"]