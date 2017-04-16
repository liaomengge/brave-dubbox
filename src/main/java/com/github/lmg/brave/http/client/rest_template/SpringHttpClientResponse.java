package com.github.lmg.brave.http.client.rest_template;

import com.github.kristofa.brave.http.HttpResponse;

/**
 * Created by liaomengge on 17/4/16.
 */
public class SpringHttpClientResponse implements HttpResponse {

    private final int status;

    public SpringHttpClientResponse(int status) {
        this.status = status;
    }

    @Override
    public int getHttpStatusCode() {
        return this.status;
    }
}
