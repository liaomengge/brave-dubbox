package com.github.kristofa.brave.dubbo;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.*;
import com.github.kristofa.brave.Brave;
import lombok.Setter;

/**
 * Created by chenjg on 16/7/24.
 */
@Activate(group = Constants.CONSUMER)
public class BraveConsumerFilter implements Filter {

    @Setter
    private Brave brave;

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        brave.clientRequestInterceptor().handle(new DubboClientRequestAdapter(invoker, invocation));
        try {
            Result rpcResult = invoker.invoke(invocation);
            brave.clientResponseInterceptor().handle(new DubboClientResponseAdapter(rpcResult));
            return rpcResult;
        } catch (Exception ex) {
            brave.clientResponseInterceptor().handle(new DubboClientResponseAdapter(ex));
            throw ex;
        } finally {
            brave.clientSpanThreadBinder().setCurrentSpan(null);
        }
    }
}
