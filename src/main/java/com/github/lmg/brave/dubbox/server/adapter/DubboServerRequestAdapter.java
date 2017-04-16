package com.github.lmg.brave.dubbox.server.adapter;

import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.RpcContext;
import com.github.kristofa.brave.*;
import com.github.kristofa.brave.http.BraveHttpHeaders;
import com.github.lmg.brave.dubbox.support.DubboSpanNameProvider;
import com.github.lmg.brave.dubbox.support.defaults.DefaultSpanNameProvider;

import java.util.Collection;
import java.util.Collections;

import static com.github.kristofa.brave.IdConversion.convertToLong;

/**
 * Created by liaomengge on 17/4/13.
 */
public class DubboServerRequestAdapter implements ServerRequestAdapter {

    private Invoker<?> invoker;
    private Invocation invocation;

    private static final DubboSpanNameProvider spanNameProvider = new DefaultSpanNameProvider();


    public DubboServerRequestAdapter(Invoker<?> invoker, Invocation invocation) {
        this.invoker = invoker;
        this.invocation = invocation;
    }

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
        KeyValueAnnotation remoteAddrAnnotation = KeyValueAnnotation.create("Client Address", RpcContext.getContext().getRemoteAddressString());
        return Collections.singleton(remoteAddrAnnotation);
    }

    static SpanId getSpanId(String traceId, String spanId, String parentSpanId) {
        return SpanId.builder()
                .traceId(convertToLong(traceId))
                .spanId(convertToLong(spanId))
                .parentId(parentSpanId == null ? null : convertToLong(parentSpanId))
                .sampled(Boolean.TRUE).build();
    }


}
