package com.github.lmg.brave.dubbox.factory;

import com.github.kristofa.brave.Brave;
import com.github.kristofa.brave.EmptySpanCollectorMetricsHandler;
import com.github.kristofa.brave.LoggingSpanCollector;
import com.github.kristofa.brave.Sampler;
import com.github.kristofa.brave.http.HttpSpanCollector;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.FactoryBean;

import java.util.logging.Logger;

/**
 * Created by liaomengge on 17/4/13.
 */
public class BraveFactoryBean implements FactoryBean<Brave> {

    private static final Logger logger = Logger.getLogger(BraveFactoryBean.class.getName());
    /**
     * 服务名
     */
    @Getter
    @Setter
    private String serviceName;

    /**
     * zipkin服务器ip及端口，不配置默认打印日志
     */
    @Getter
    @Setter
    private String zipkinHost;

    /**
     * 采样率 0~1 之间
     */
    private float rate = 1.0f;

    /**
     * 单例模式
     */
    private Brave instance;

    public String getRate() {
        return String.valueOf(rate);
    }

    public void setRate(String rate) {
        this.rate = Float.parseFloat(rate);
    }

    @Override
    public Brave getObject() throws Exception {
        if (this.instance == null) {
            this.createInstance();
        }
        return this.instance;
    }

    @Override
    public Class<?> getObjectType() {
        return Brave.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    private void createInstance() {
        if (this.serviceName == null) {
            throw new BeanInitializationException("Property serviceName must be set.");
        }
        Brave.Builder builder = new Brave.Builder(this.serviceName);
        if (StringUtils.isNotBlank(this.zipkinHost)) {
            builder.spanCollector(HttpSpanCollector.create(this.zipkinHost, new EmptySpanCollectorMetricsHandler())).traceSampler(Sampler.create(rate)).build();
            logger.info("brave dubbox config collect with httpSpanCollector , rate is " + rate);
        } else {
            builder.spanCollector(new LoggingSpanCollector()).traceSampler(Sampler.create(rate)).build();
            logger.info("brave dubbox config collect with loggingSpanCollector , rate is " + rate);
        }
        this.instance = builder.build();
    }
}
