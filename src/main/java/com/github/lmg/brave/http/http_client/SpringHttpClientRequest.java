package com.github.lmg.brave.http.http_client;

import com.github.kristofa.brave.http.HttpClientRequest;
import org.apache.http.HttpRequest;
import org.apache.http.client.methods.HttpRequestWrapper;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by liaomengge on 17/5/25.
 */
public class SpringHttpClientRequest implements HttpClientRequest {

    private final HttpRequest request;

    public SpringHttpClientRequest(HttpRequest request) {
        this.request = request;
    }

    @Override
    public void addHeader(String header, String value) {
        this.request.addHeader(header, value);
    }

    @Override
    public URI getUri() {
        String uri = null;
        if (request instanceof HttpRequestWrapper) {
            uri = ((HttpRequestWrapper) request).getOriginal().getRequestLine().getUri();
        } else {
            request.getRequestLine().getUri();
        }
        try {
            return new URI(uri);
        } catch (URISyntaxException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public String getHttpMethod() {
        return this.request.getRequestLine().getMethod();
    }
}
