
## refresh刷新机制说明
1.`springboot1.5.x`以上默认开通了安全认证，所以需要在配置文件application.properties添加以下配置
                 
                 management.security.enabled=false
curl命令改为：curl -v -X POST "http://localhost:8002/refresh"

2.`springboot2.x`后这么配是不得行的，需要在~~server~~(可不加)和client上都加上：

```yaml
management:
  endpoints:
    web:
      exposure:
        include: "*" #or  refresh
        
```
   （他们写的bus-refresh，一样的）
  curl命令改为：curl -v -X POST "http://localhost:8002/actuator/refresh"