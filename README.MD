使用步骤：

### maven添加该项目jar包（自己打包安装到本地仓库）
```xml
        <dependency>
            <groupId>io.zipkin.brave</groupId>
            <artifactId>brave-dubbox</artifactId>
            <version>1.0.0-SNAPSHOT</version>
        </dependency>
```

### spring中配置
    
   * 打印日志方式
```xml
        <bean id="brave" class="com.github.lmg.brave.dubbox.factory.BraveFactoryBean">
            <property name="serviceName" value="liaomengge"/>
            <property name="zipkinHost" value=""/>
            <property name="rate" value="1.0"/>
        </bean>
```
   * http方式
```xml
        <bean id="brave" class="com.github.lmg.brave.dubbox.factory.BraveFactoryBean">
            <property name="serviceName" value="liaomengge"/>
            <property name="zipkinHost" value="http://localhost:9411"/>
            <property name="rate" value="1.0"/>
        </bean>
```



### 参考文档:
[http://www.tuicool.com/articles/f2qAZnZ](http://www.tuicool.com/articles/f2qAZnZ)
