package com.github.lmg.brave.http.http_client;

import com.github.kristofa.brave.http.HttpResponse;

/**
 * Created by liaomengge on 17/5/25.
 */
public class SpringHttpClientResponse implements HttpResponse {

    private final org.apache.http.HttpResponse response;

    public SpringHttpClientResponse(org.apache.http.HttpResponse response) {
        this.response = response;
    }

    @Override
    public int getHttpStatusCode() {
        return this.response.getStatusLine().getStatusCode();
    }
}
