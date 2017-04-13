package com.github.kristofa.brave.dubbo;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.*;
import com.github.kristofa.brave.*;
import lombok.Setter;

/**
 * Created by chenjg on 16/7/24.
 */
@Activate(group = Constants.PROVIDER)
public class BraveProviderFilter implements Filter {

    @Setter
    private Brave brave;

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        brave.serverRequestInterceptor().handle(new DubboServerRequestAdapter(invoker, invocation, brave.serverTracer()));
        Result rpcResult = invoker.invoke(invocation);
        brave.serverResponseInterceptor().handle(new DubboServerResponseAdapter(rpcResult));
        return rpcResult;
    }
}
