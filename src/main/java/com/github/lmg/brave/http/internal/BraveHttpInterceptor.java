package com.github.lmg.brave.http.internal;

import com.github.kristofa.brave.Brave;
import com.github.kristofa.brave.ClientRequestInterceptor;
import com.github.kristofa.brave.ClientResponseInterceptor;
import com.github.kristofa.brave.http.SpanNameProvider;
import com.github.lmg.brave.http.support.RestSpanNameProvider;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by liaomengge on 17/5/25.
 */
public abstract class BraveHttpInterceptor implements InitializingBean {

    protected ClientRequestInterceptor clientRequestInterceptor;
    protected ClientResponseInterceptor clientResponseInterceptor;
    protected SpanNameProvider spanNameProvider;

    @Autowired
    protected Brave brave;

    @Override
    public void afterPropertiesSet() throws Exception {
        this.clientRequestInterceptor = this.brave.clientRequestInterceptor();
        this.clientResponseInterceptor = this.brave.clientResponseInterceptor();
        this.spanNameProvider = new RestSpanNameProvider();
    }
}
