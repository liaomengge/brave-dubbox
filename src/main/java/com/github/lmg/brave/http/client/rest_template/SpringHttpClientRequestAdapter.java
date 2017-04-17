package com.github.lmg.brave.http.client.rest_template;

import com.github.kristofa.brave.http.HttpClientRequest;
import com.github.kristofa.brave.http.HttpClientRequestAdapter;
import com.github.kristofa.brave.http.SpanNameProvider;

/**
 * Created by liaomengge on 17/4/16.
 */
public class SpringHttpClientRequestAdapter extends HttpClientRequestAdapter {

    public SpringHttpClientRequestAdapter(HttpClientRequest request, SpanNameProvider spanNameProvider) {
        super(request, spanNameProvider);
    }

}
