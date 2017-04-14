package com.github.lmg.brave.dubbox.client;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.*;
import com.github.kristofa.brave.Brave;
import com.github.kristofa.brave.ClientRequestInterceptor;
import com.github.kristofa.brave.ClientResponseInterceptor;
import com.github.kristofa.brave.LocalTracer;
import com.github.lmg.brave.dubbox.client.adapter.DubboClientRequestAdapter;
import com.github.lmg.brave.dubbox.client.adapter.DubboClientResponseAdapter;

/**
 * Created by liaomengge on 17/4/13.
 */
@Activate(group = Constants.CONSUMER)
public class BraveConsumerFilter implements Filter {

    private ClientRequestInterceptor clientRequestInterceptor;
    private ClientResponseInterceptor clientResponseInterceptor;
    private LocalTracer localTracer;

    private Brave brave;

    public void setBrave(Brave brave) {
        this.brave = brave;
        this.clientRequestInterceptor = this.brave.clientRequestInterceptor();
        this.clientResponseInterceptor = this.brave.clientResponseInterceptor();
        this.localTracer = this.brave.localTracer();
    }

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        clientRequestInterceptor.handle(new DubboClientRequestAdapter(invoker, invocation));
        try {
            Result rpcResult = invoker.invoke(invocation);
            brave.clientResponseInterceptor().handle(new DubboClientResponseAdapter(rpcResult));
            return rpcResult;
        } catch (Exception e) {
            clientResponseInterceptor.handle(new DubboClientResponseAdapter(e));
            throw e;
        } finally {
            localTracer.finishSpan();
        }
    }
}
