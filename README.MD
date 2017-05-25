使用步骤：

### maven添加该项目jar包（自己打包安装到本地仓库）
```xml
        <dependency>
            <groupId>io.zipkin.brave</groupId>
            <artifactId>brave-dubbox</artifactId>
            <version>2.0.0-SNAPSHOT</version>
        </dependency>
```

### spring中配置
    
   * 打印日志方式(建议)
```xml
        <bean id="brave" class="com.github.lmg.brave.dubbox.factory.BraveFactoryBean">
            <property name="serviceName" value="liaomengge"/>
            <property name="zipkinHost" value=""/>
            <property name="rate" value="1.0"/>
        </bean>
```
   * http方式（不建议在生产上使用）
```xml
        <bean id="brave" class="com.github.lmg.brave.dubbox.factory.BraveFactoryBean">
            <property name="serviceName" value="liaomengge"/>
            <property name="zipkinHost" value="http://localhost:9411"/>
            <property name="rate" value="1.0"/>
        </bean>
```

### 支持模式

 1、 dubbox客户端 -> dubbox服务端

> 直接使用dubbox进行调用

 2、 非dubbox客户端（restTemplate） -> dubbox服务端

> 在客户端，通过Spring RestTemplate走http调用服务

 3、 非dubbox客户端（httpClient） -> dubbox服务端

> 在客户端，通过HttpCient走http调用服务

 4、 非dubbox客户端（okhttp3） -> dubbox服务端
> 开发中 

 
### 参考文档:
[http://www.tuicool.com/articles/f2qAZnZ](http://www.tuicool.com/articles/f2qAZnZ)
