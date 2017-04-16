package com.github.lmg.brave.dubbox.server.adapter;

import com.github.kristofa.brave.ServerRequestAdapter;
import com.github.kristofa.brave.SpanId;
import com.github.lmg.brave.dubbox.support.DubboSpanNameProvider;
import com.github.lmg.brave.dubbox.support.defaults.DefaultSpanNameProvider;

import static com.github.kristofa.brave.IdConversion.convertToLong;

/**
 * Created by liaomengge on 17/4/16.
 */
public abstract class AbstractServerRequestAdapter implements ServerRequestAdapter {

    protected static final DubboSpanNameProvider spanNameProvider = new DefaultSpanNameProvider();

    protected SpanId getSpanId(String traceId, String spanId, String parentSpanId) {
        return SpanId.builder()
                .traceId(convertToLong(traceId))
                .spanId(convertToLong(spanId))
                .parentId(parentSpanId == null ? null : convertToLong(parentSpanId))
                .sampled(Boolean.TRUE).build();
    }
}
