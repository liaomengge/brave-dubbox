package com.github.lmg.brave.dubbox.client.adapter;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.RpcInvocation;
import com.github.kristofa.brave.ClientRequestAdapter;
import com.github.kristofa.brave.IdConversion;
import com.github.kristofa.brave.KeyValueAnnotation;
import com.github.kristofa.brave.SpanId;
import com.github.kristofa.brave.internal.Nullable;
import com.github.lmg.brave.dubbox.DubboServerNameProvider;
import com.github.lmg.brave.dubbox.DubboSpanNameProvider;
import com.github.lmg.brave.dubbox.enums.BraveAttachmentEnum;
import com.github.lmg.brave.dubbox.support.DefaultServerNameProvider;
import com.github.lmg.brave.dubbox.support.DefaultSpanNameProvider;
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
    private static final DubboServerNameProvider serverNameProvider = new DefaultServerNameProvider();


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
            rpcInvocation.setAttachment(BraveAttachmentEnum.Sampled.getName(), "0");
            return;
        }
        rpcInvocation.setAttachment(BraveAttachmentEnum.Sampled.getName(), "1");
        rpcInvocation.setAttachment(BraveAttachmentEnum.TraceId.getName(), IdConversion.convertToString(spanId.traceId));
        rpcInvocation.setAttachment(BraveAttachmentEnum.SpanId.getName(), IdConversion.convertToString(spanId.spanId));
        if (spanId.nullableParentId() != null) {
            rpcInvocation.setAttachment(BraveAttachmentEnum.ParentId.getName(), IdConversion.convertToString(spanId.parentId));
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
        String serverName = serverNameProvider.resolveServerName(this.invoker);
        return Endpoint.create(serverName, IPConvertUtil.convertToInt(ip), port);
    }


}
