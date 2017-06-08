package com.github.lmg.brave.http.http_client;

import com.github.lmg.brave.http.internal.BraveHttpInterceptor;
import com.github.lmg.brave.http.internal.HttpResponseAdapter;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;

/**
 * Created by liaomengge on 17/5/25.
 */
public class BraveHttpClientResponseInterceptor extends BraveHttpInterceptor implements HttpResponseInterceptor {

    @Override
    public void process(HttpResponse httpResponse, HttpContext httpContext) throws HttpException, IOException {
        this.clientResponseInterceptor.handle(new HttpResponseAdapter(new SpringHttpClientResponse(httpResponse)));
    }
}
