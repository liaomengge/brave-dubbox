package com.github.lmg.brave.http.internal;

import com.github.kristofa.brave.http.HttpClientRequest;
import com.github.kristofa.brave.http.HttpClientRequestAdapter;
import com.github.kristofa.brave.http.SpanNameProvider;

/**
 * Created by liaomengge on 17/6/8.
 */
public class HttpRequestAdapter extends HttpClientRequestAdapter {
    
    public HttpRequestAdapter(HttpClientRequest request, SpanNameProvider spanNameProvider) {
        super(request, spanNameProvider);
    }
}
