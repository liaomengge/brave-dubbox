package com.github.lmg.brave.http.internal;

import com.github.kristofa.brave.ClientResponseAdapter;
import com.github.kristofa.brave.KeyValueAnnotation;
import com.github.kristofa.brave.http.HttpResponse;

import java.util.Collection;
import java.util.Collections;

/**
 * Created by liaomengge on 17/6/8.
 */
public class HttpResponseAdapter implements ClientResponseAdapter {

    private static final String HTTP_CLIENT_EXCEPTION = "http.client.exception";

    private HttpResponse response;
    private Exception exception;

    public HttpResponseAdapter(HttpResponse response) {
        this.response = response;
    }

    public HttpResponseAdapter(Exception exception) {
        this.exception = exception;
    }

    public Collection<KeyValueAnnotation> responseAnnotations() {
        if (exception != null) {
            KeyValueAnnotation keyValueAnnotation = KeyValueAnnotation.create(HTTP_CLIENT_EXCEPTION, exception.getMessage());
            return Collections.singleton(keyValueAnnotation);
        }
        int httpStatus = this.response.getHttpStatusCode();
        return (Collection) (httpStatus >= 200 && httpStatus <= 299 ? Collections.emptyList() : Collections.singleton(KeyValueAnnotation.create("http.status_code", String.valueOf(httpStatus))));
    }
}
