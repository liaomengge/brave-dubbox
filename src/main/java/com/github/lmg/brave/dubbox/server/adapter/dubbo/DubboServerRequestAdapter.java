package com.github.lmg.brave.dubbox.server.adapter.dubbo;

import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.RpcContext;
import com.github.kristofa.brave.KeyValueAnnotation;
import com.github.kristofa.brave.SpanId;
import com.github.kristofa.brave.TraceData;
import com.github.kristofa.brave.http.BraveHttpHeaders;
import com.github.lmg.brave.dubbox.server.adapter.AbstractServerRequestAdapter;
import com.github.lmg.brave.dubbox.utils.TraceLogUtil;
import lombok.AllArgsConstructor;

import java.util.Collection;
import java.util.Collections;

/**
 * Created by liaomengge on 17/4/13.
 */
@AllArgsConstructor
public class DubboServerRequestAdapter extends AbstractServerRequestAdapter {

    private static final String CLIENT_ADDRESS = "Client Address";

    private Invoker<?> invoker;
    private Invocation invocation;

    @Override
    public TraceData getTraceData() {
        final String sampled = invocation.getAttachment(BraveHttpHeaders.Sampled.getName());
        if (sampled != null) {
            if (sampled.equals("0")) {
                return TraceData.NOT_SAMPLED;
            }
            final String parentId = invocation.getAttachment(BraveHttpHeaders.ParentSpanId.getName());
            final String spanId = invocation.getAttachment(BraveHttpHeaders.SpanId.getName());
            final String traceId = invocation.getAttachment(BraveHttpHeaders.TraceId.getName());
            if (traceId != null && spanId != null) {
                TraceLogUtil.put(BraveHttpHeaders.TraceId.getName(), appIdProvider.resolveAppId(invocation) + "_" + TraceLogUtil.generateTraceLogIdPrefix() + traceId);
                SpanId span = getSpanId(traceId, spanId, parentId);
                return TraceData.create(span);
            }
        }
        return TraceData.EMPTY;

    }

    @Override
    public String getSpanName() {
        return spanNameProvider.resolveSpanName(this.invoker, this.invocation);
    }

    @Override
    public Collection<KeyValueAnnotation> requestAnnotations() {
        KeyValueAnnotation remoteAddrAnnotation = KeyValueAnnotation.create(CLIENT_ADDRESS, RpcContext.getContext().getRemoteAddressString());
        return Collections.singleton(remoteAddrAnnotation);
    }

}
