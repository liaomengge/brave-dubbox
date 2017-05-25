package com.github.lmg.brave.http.client.http_client;

import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;

/**
 * Created by liaomengge on 17/5/25.
 */
public class BraveHttpResponseInterceptor extends BraveHttpInterceptor implements HttpResponseInterceptor {

    @Override
    public void process(HttpResponse httpResponse, HttpContext httpContext) throws HttpException, IOException {
        this.clientResponseInterceptor.handle(new SpringHttpClientResponseAdapter(new SpringHttpClientResponse(httpResponse)));
    }
}
