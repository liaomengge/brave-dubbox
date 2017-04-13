package com.github.lmg.brave.dubbox.server;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.*;
import com.github.kristofa.brave.*;
import com.github.lmg.brave.dubbox.DubboServerRequestAdapter;
import com.github.lmg.brave.dubbox.server.adapter.DubboServerResponseAdapter;
import lombok.Setter;

/**
 * Created by liaomengge on 17/4/13.
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
