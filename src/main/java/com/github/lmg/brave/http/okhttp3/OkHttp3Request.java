package com.github.lmg.brave.http.okhttp3;

import com.github.kristofa.brave.http.HttpClientRequest;
import okhttp3.Request;

import java.net.URI;

/**
 * Created by liaomengge on 17/6/8.
 */
public class OkHttp3Request implements HttpClientRequest {

    private final Request.Builder requestBuilder;
    private final Request request;

    public OkHttp3Request(Request.Builder requestBuilder, Request request) {
        this.requestBuilder = requestBuilder;
        this.request = request;
    }

    @Override
    public void addHeader(String name, String value) {
        requestBuilder.addHeader(name, value);
    }

    @Override
    public URI getUri() {
        return request.url().uri();
    }

    @Override
    public String getHttpMethod() {
        return request.method();
    }
}
