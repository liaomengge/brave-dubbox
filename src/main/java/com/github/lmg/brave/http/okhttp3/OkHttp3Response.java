package com.github.lmg.brave.http.okhttp3;

import com.github.kristofa.brave.http.HttpResponse;
import okhttp3.Response;

/**
 * Created by liaomengge on 17/6/8.
 */
public class OkHttp3Response implements HttpResponse {

    private final Response response;

    public OkHttp3Response(Response response) {
        this.response = response;
    }

    @Override
    public int getHttpStatusCode() {
        return response.code();
    }
}
