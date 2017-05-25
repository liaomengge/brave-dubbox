package com.github.lmg.brave.http.rest_template;

import com.github.kristofa.brave.http.HttpClientRequest;
import org.springframework.http.HttpRequest;

import java.net.URI;

/**
 * Created by liaomengge on 17/4/16.
 */

public class SpringRestTemplateRequest implements HttpClientRequest {

    private final HttpRequest request;

    public SpringRestTemplateRequest(HttpRequest request) {
        this.request = request;
    }

    @Override
    public void addHeader(String header, String value) {
        this.request.getHeaders().add(header, value);
    }

    @Override
    public URI getUri() {
        return this.request.getURI();
    }

    @Override
    public String getHttpMethod() {
        return this.request.getMethod().name();
    }

}
