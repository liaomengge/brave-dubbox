package com.github.lmg.brave.dubbox.server.adapter.rest;

import com.alibaba.dubbo.rpc.Result;
import com.github.kristofa.brave.KeyValueAnnotation;
import com.github.lmg.brave.dubbox.server.adapter.AbstractServerResponseAdapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by liaomengge on 17/4/16.
 */
public class RestServerResponseAdapter extends AbstractServerResponseAdapter {

    private static final String HTTP_SERVER_RESULT = "http.server.result";
    private static final String HTTP_SERVER_EXCEPTION = "http.server.exception";

    private Result rpcResult;
    private Exception exception;

    public RestServerResponseAdapter(Result rpcResult) {
        this.rpcResult = rpcResult;
    }

    public RestServerResponseAdapter(Exception exception) {
        this.exception = exception;
    }

    @Override
    public Collection<KeyValueAnnotation> responseAnnotations() {
        List<KeyValueAnnotation> annotations = new ArrayList<>();
        if (exception != null) {
            KeyValueAnnotation keyValueAnnotation = KeyValueAnnotation.create(HTTP_SERVER_EXCEPTION, exception.getMessage());
            annotations.add(keyValueAnnotation);
        } else {
            if (rpcResult.hasException()) {
                KeyValueAnnotation keyValueAnnotation = KeyValueAnnotation.create(HTTP_SERVER_EXCEPTION, rpcResult.getException().getMessage());
                annotations.add(keyValueAnnotation);
            } else {
                KeyValueAnnotation keyValueAnnotation = KeyValueAnnotation.create(HTTP_SERVER_RESULT, "success");
                annotations.add(keyValueAnnotation);
            }
        }
        return annotations;
    }
}
