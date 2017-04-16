package com.github.lmg.brave.http.client.rest_template;

import com.github.kristofa.brave.Brave;
import com.github.kristofa.brave.ClientRequestInterceptor;
import com.github.kristofa.brave.ClientResponseInterceptor;
import com.github.kristofa.brave.http.DefaultSpanNameProvider;
import com.github.kristofa.brave.http.SpanNameProvider;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

/**
 * Created by liaomengge on 17/4/16.
 */
public class BraveClientHttpRequestInterceptor implements InitializingBean, ClientHttpRequestInterceptor {

    private ClientRequestInterceptor clientRequestInterceptor;
    private ClientResponseInterceptor clientResponseInterceptor;
    private SpanNameProvider spanNameProvider;

    @Autowired
    private Brave brave;

    @Override
    public ClientHttpResponse intercept(HttpRequest httpRequest, byte[] bytes, ClientHttpRequestExecution execution) throws IOException {
        this.clientRequestInterceptor.handle(new SpringHttpClientRequestAdapter(new SpringHttpClientRequest(httpRequest), this.spanNameProvider));
        try {
            ClientHttpResponse response = execution.execute(httpRequest, bytes);
            clientResponseInterceptor.handle(new SpringHttpClientResponseAdapter(new SpringHttpClientResponse(response.getRawStatusCode())));
            return response;
        } catch (Exception e) {
            clientResponseInterceptor.handle(new SpringHttpClientResponseAdapter(e));
            throw e;
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.clientRequestInterceptor = this.brave.clientRequestInterceptor();
        this.clientResponseInterceptor = this.brave.clientResponseInterceptor();
        this.spanNameProvider = new DefaultSpanNameProvider();
    }
}
