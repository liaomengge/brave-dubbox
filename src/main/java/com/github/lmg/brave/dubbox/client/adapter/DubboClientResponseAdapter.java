package com.github.lmg.brave.dubbox.client.adapter;

import com.alibaba.dubbo.rpc.Result;
import com.github.kristofa.brave.ClientResponseAdapter;
import com.github.kristofa.brave.KeyValueAnnotation;
import com.github.lmg.brave.dubbox.utils.ThrowableUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by liaomengge on 17/4/13.
 */
public class DubboClientResponseAdapter implements ClientResponseAdapter {

    private static final String CLIENT_RESULT = "Client Result";
    private static final String CLIENT_EXCEPTION = "Client Exception";

    private Result rpcResult;
    private Exception exception;

    public DubboClientResponseAdapter(Exception exception) {
        this.exception = exception;
    }


    public DubboClientResponseAdapter(Result rpcResult) {
        this.rpcResult = rpcResult;
    }

    @Override
    public Collection<KeyValueAnnotation> responseAnnotations() {
        List<KeyValueAnnotation> annotations = new ArrayList<>();
        if (exception != null) {
            KeyValueAnnotation keyValueAnnotation = KeyValueAnnotation.create(CLIENT_EXCEPTION, ThrowableUtil.getStackTrace(exception));
            annotations.add(keyValueAnnotation);
        } else {
            if (rpcResult.hasException()) {
                KeyValueAnnotation keyValueAnnotation = KeyValueAnnotation.create(CLIENT_EXCEPTION, ThrowableUtil.getStackTrace(rpcResult.getException()));
                annotations.add(keyValueAnnotation);
            } else {
                KeyValueAnnotation keyValueAnnotation = KeyValueAnnotation.create(CLIENT_RESULT, rpcResult.getValue().toString());
                annotations.add(keyValueAnnotation);
            }
        }
        return annotations;
    }

}
