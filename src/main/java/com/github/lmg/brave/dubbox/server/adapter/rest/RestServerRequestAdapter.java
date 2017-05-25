package com.github.lmg.brave.dubbox.server.adapter.rest;

import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.RpcContext;
import com.github.kristofa.brave.KeyValueAnnotation;
import com.github.kristofa.brave.SpanId;
import com.github.kristofa.brave.TraceData;
import com.github.kristofa.brave.http.BraveHttpHeaders;
import com.github.lmg.brave.dubbox.server.adapter.AbstractServerRequestAdapter;
import com.github.lmg.brave.dubbox.utils.TraceLogUtil;
import lombok.AllArgsConstructor;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Collections;

/**
 * Created by liaomengge on 17/4/16.
 */
@AllArgsConstructor
public class RestServerRequestAdapter extends AbstractServerRequestAdapter {

    private Invocation invocation;

    @Override
    public TraceData getTraceData() {
        HttpServletRequest httpServletRequest = (HttpServletRequest) RpcContext.getContext().getRequest();
        final String sampled = httpServletRequest.getHeader(BraveHttpHeaders.Sampled.getName());
        if (sampled != null) {
            if (sampled.equals("0")) {
                return TraceData.NOT_SAMPLED;
            }
            final String parentId = httpServletRequest.getHeader(BraveHttpHeaders.ParentSpanId.getName());
            final String spanId = httpServletRequest.getHeader(BraveHttpHeaders.SpanId.getName());
            final String traceId = httpServletRequest.getHeader(BraveHttpHeaders.TraceId.getName());
            if (traceId != null && spanId != null) {
                TraceLogUtil.put(TraceLogUtil.generateTraceLogIdPrefix() + BraveHttpHeaders.TraceId.getName(), traceId);
                SpanId span = getSpanId(traceId, spanId, parentId);
                return TraceData.create(span);
            }
        }
        return TraceData.EMPTY;
    }

    @Override
    public String getSpanName() {
        return spanNameProvider.resolveSpanName(this.invocation);
    }

    @Override
    public Collection<KeyValueAnnotation> requestAnnotations() {
        return Collections.emptyList();
    }

}
