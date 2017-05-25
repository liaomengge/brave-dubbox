package com.github.lmg.brave.http.http_client;

import com.github.kristofa.brave.http.HttpClientRequest;
import com.github.kristofa.brave.http.HttpClientRequestAdapter;
import com.github.kristofa.brave.http.SpanNameProvider;

/**
 * Created by liaomengge on 17/5/25.
 */
public class SpringHttpClientRequestAdapter extends HttpClientRequestAdapter {

    public SpringHttpClientRequestAdapter(HttpClientRequest request, SpanNameProvider spanNameProvider) {
        super(request, spanNameProvider);
    }
}
