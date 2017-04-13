package com.github.lmg.brave.dubbox.server.adapter;

import com.alibaba.dubbo.rpc.Result;
import com.github.kristofa.brave.KeyValueAnnotation;
import com.github.kristofa.brave.ServerResponseAdapter;
import com.github.lmg.brave.dubbox.utils.ThrowableUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by liaomengge on 17/4/13.
 */
public class DubboServerResponseAdapter implements ServerResponseAdapter {

    private static final String SERVER_RESULT = "Server Result";
    private static final String SERVER_EXCEPTION = "Server Exception";

    private Result rpcResult;
    private Exception exception;

    public DubboServerResponseAdapter(Result rpcResult) {
        this.rpcResult = rpcResult;
    }

    public DubboServerResponseAdapter(Exception exception) {
        this.exception = exception;
    }

    @Override
    public Collection<KeyValueAnnotation> responseAnnotations() {
        List<KeyValueAnnotation> annotations = new ArrayList<>();
        if (exception != null) {
            KeyValueAnnotation keyValueAnnotation = KeyValueAnnotation.create(SERVER_EXCEPTION, ThrowableUtil.getStackTrace(exception));
            annotations.add(keyValueAnnotation);
        } else {
            if (rpcResult.hasException()) {
                KeyValueAnnotation keyValueAnnotation = KeyValueAnnotation.create(SERVER_EXCEPTION, ThrowableUtil.getStackTrace(rpcResult.getException()));
                annotations.add(keyValueAnnotation);
            } else {
                KeyValueAnnotation keyValueAnnotation = KeyValueAnnotation.create(SERVER_RESULT, "success");
                annotations.add(keyValueAnnotation);
            }
        }
        return annotations;
    }
}
