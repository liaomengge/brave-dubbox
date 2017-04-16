package com.github.lmg.brave.dubbox.client.adapter;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.RpcInvocation;
import com.github.kristofa.brave.ClientRequestAdapter;
import com.github.kristofa.brave.IdConversion;
import com.github.kristofa.brave.KeyValueAnnotation;
import com.github.kristofa.brave.SpanId;
import com.github.kristofa.brave.http.BraveHttpHeaders;
import com.github.kristofa.brave.internal.Nullable;
import com.github.lmg.brave.dubbox.support.DubboSpanNameProvider;
import com.github.lmg.brave.dubbox.support.defaults.DefaultSpanNameProvider;
import com.github.lmg.brave.dubbox.utils.IPConvertUtil;
import com.twitter.zipkin.gen.Endpoint;

import java.util.Collection;
import java.util.Collections;

/**
 * Created by liaomengge on 17/4/13.
 */
public class DubboClientRequestAdapter implements ClientRequestAdapter {

    private Invoker<?> invoker;
    private Invocation invocation;

    private static final DubboSpanNameProvider spanNameProvider = new DefaultSpanNameProvider();


    public DubboClientRequestAdapter(Invoker<?> invoker, Invocation invocation) {
        this.invoker = invoker;
        this.invocation = invocation;
    }

    @Override
    public String getSpanName() {
        return spanNameProvider.resolveSpanName(this.invoker, this.invocation);
    }

    @Override
    public void addSpanIdToRequest(@Nullable SpanId spanId) {
        String application = this.invoker.getUrl().getParameter("application");
        RpcInvocation rpcInvocation = (RpcInvocation) this.invocation;
        rpcInvocation.setAttachment("clientName", application);
        if (spanId == null) {
            rpcInvocation.setAttachment(BraveHttpHeaders.Sampled.getName(), "0");
            return;
        }
        rpcInvocation.setAttachment(BraveHttpHeaders.Sampled.getName(), "1");
        rpcInvocation.setAttachment(BraveHttpHeaders.TraceId.getName(), IdConversion.convertToString(spanId.traceId));
        rpcInvocation.setAttachment(BraveHttpHeaders.SpanId.getName(), IdConversion.convertToString(spanId.spanId));
        if (spanId.nullableParentId() != null) {
            rpcInvocation.setAttachment(BraveHttpHeaders.ParentSpanId.getName(), IdConversion.convertToString(spanId.parentId));
        }
    }

    @Override
    public Collection<KeyValueAnnotation> requestAnnotations() {
        String protocol = this.invoker.getUrl().getProtocol();
        String key = protocol + ".url";
        return Collections.singletonList(KeyValueAnnotation.create(key, this.invoker.getUrl().toString()));
    }

    @Override
    public Endpoint serverAddress() {
        URL url = this.invoker.getUrl();
        String ip = url.getIp();
        int port = url.getPort();
        //此处是拿不到服务端的serverName的
        return Endpoint.builder().serviceName("").ipv4(IPConvertUtil.convertToInt(ip)).port(port).build();
    }


}
