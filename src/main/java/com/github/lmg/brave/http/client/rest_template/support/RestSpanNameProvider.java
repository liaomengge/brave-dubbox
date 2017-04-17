package com.github.lmg.brave.http.client.rest_template.support;

import com.github.kristofa.brave.http.HttpRequest;
import com.github.kristofa.brave.http.SpanNameProvider;

/**
 * Created by liaomengge on 17/4/17.
 */
public class RestSpanNameProvider implements SpanNameProvider {

    private static final String URL_SEPARATOR = "/";

    @Override
    public String spanName(HttpRequest httpRequest) {
        String path = httpRequest.getUri().getPath();
        int length = path.length();
        if (URL_SEPARATOR.equals(path.charAt(length - 1) + "")) {
            return path.substring(path.lastIndexOf(URL_SEPARATOR, length - 2) + 1, length - 1);
        } else {
            return path.substring(path.lastIndexOf(URL_SEPARATOR) + 1);
        }
    }
}
