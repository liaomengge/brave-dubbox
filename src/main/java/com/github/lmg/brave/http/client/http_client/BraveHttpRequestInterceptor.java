package com.github.lmg.brave.http.client.http_client;

import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;

/**
 * Created by liaomengge on 17/5/25.
 */
public class BraveHttpRequestInterceptor extends BraveHttpInterceptor implements HttpRequestInterceptor {

    @Override
    public void process(HttpRequest httpRequest, HttpContext httpContext) throws HttpException, IOException {
        this.clientRequestInterceptor.handle(new SpringHttpClientRequestAdapter(new SpringHttpClientRequest(httpRequest), this.spanNameProvider));
    }
}
