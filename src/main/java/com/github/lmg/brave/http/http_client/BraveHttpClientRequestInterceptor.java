package com.github.lmg.brave.http.http_client;

import com.github.lmg.brave.http.internal.BraveHttpInterceptor;
import com.github.lmg.brave.http.internal.HttpRequestAdapter;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;

/**
 * Created by liaomengge on 17/5/25.
 */
public class BraveHttpClientRequestInterceptor extends BraveHttpInterceptor implements HttpRequestInterceptor {

    @Override
    public void process(HttpRequest httpRequest, HttpContext httpContext) throws HttpException, IOException {
        this.clientRequestInterceptor.handle(new HttpRequestAdapter(new SpringHttpClientRequest(httpRequest), this.spanNameProvider));
    }
}
