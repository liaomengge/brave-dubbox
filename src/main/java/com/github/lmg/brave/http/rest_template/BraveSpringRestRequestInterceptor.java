package com.github.lmg.brave.http.rest_template;

import com.github.lmg.brave.http.internal.BraveHttpInterceptor;
import com.github.lmg.brave.http.internal.HttpRequestAdapter;
import com.github.lmg.brave.http.internal.HttpResponseAdapter;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

/**
 * Created by liaomengge on 17/4/16.
 */
public class BraveSpringRestRequestInterceptor extends BraveHttpInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(HttpRequest httpRequest, byte[] bytes, ClientHttpRequestExecution execution) throws IOException {
        this.clientRequestInterceptor.handle(new HttpRequestAdapter(new SpringRestTemplateRequest(httpRequest), this.spanNameProvider));
        try {
            ClientHttpResponse response = execution.execute(httpRequest, bytes);
            clientResponseInterceptor.handle(new HttpResponseAdapter(new SpringRestTemplateResponse(response.getRawStatusCode())));
            return response;
        } catch (Exception e) {
            clientResponseInterceptor.handle(new HttpResponseAdapter(e));
            throw e;
        }
    }
}
