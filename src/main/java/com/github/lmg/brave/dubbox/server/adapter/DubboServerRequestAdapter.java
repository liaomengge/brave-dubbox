package com.github.lmg.brave.dubbox.server.adapter;

import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.RpcContext;
import com.github.kristofa.brave.*;
import com.github.lmg.brave.dubbox.support.DubboClientNameProvider;
import com.github.lmg.brave.dubbox.support.DubboSpanNameProvider;
import com.github.lmg.brave.dubbox.enums.BraveAttachmentEnum;
import com.github.lmg.brave.dubbox.support.defaults.DefaultClientNameProvider;
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
    private ServerTracer serverTracer;

    private static final DubboSpanNameProvider spanNameProvider = new DefaultSpanNameProvider();
    private static final DubboClientNameProvider clientNameProvider = new DefaultClientNameProvider();


    public DubboServerRequestAdapter(Invoker<?> invoker, Invocation invocation, ServerTracer serverTracer) {
        this.invoker = invoker;
        this.invocation = invocation;
        this.serverTracer = serverTracer;
    }

    @Override
    public TraceData getTraceData() {
        final String sampled = invocation.getAttachment(BraveAttachmentEnum.Sampled.getName());
        if (sampled != null) {
            if (sampled.equals("0")) {
                return TraceData.builder().sample(false).build();
            }
            final String parentId = invocation.getAttachment(BraveAttachmentEnum.ParentId.getName());
            final String spanId = invocation.getAttachment(BraveAttachmentEnum.SpanId.getName());
            final String traceId = invocation.getAttachment(BraveAttachmentEnum.TraceId.getName());
            if (traceId != null && spanId != null) {
                SpanId span = getSpanId(traceId, spanId, parentId);
                return TraceData.builder().sample(true).spanId(span).build();
            }
        }
        return TraceData.builder().build();

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
                .parentId(parentSpanId == null ? null : convertToLong(parentSpanId)).build();
    }


}
