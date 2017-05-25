package com.github.lmg.brave.dubbox.server;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.*;
import com.github.kristofa.brave.Brave;
import com.github.kristofa.brave.ServerRequestInterceptor;
import com.github.kristofa.brave.ServerResponseInterceptor;
import com.github.lmg.brave.dubbox.server.adapter.dubbo.DubboServerRequestAdapter;
import com.github.lmg.brave.dubbox.server.adapter.dubbo.DubboServerResponseAdapter;
import com.github.lmg.brave.dubbox.server.adapter.rest.RestServerRequestAdapter;
import com.github.lmg.brave.dubbox.server.adapter.rest.RestServerResponseAdapter;

import static com.github.lmg.brave.enums.ProtocolEnum.*;

/**
 * Created by liaomengge on 17/4/13.
 */
@Activate(group = Constants.PROVIDER)
public class BraveProviderFilter implements Filter {

    private ServerRequestInterceptor serverRequestInterceptor;
    private ServerResponseInterceptor serverResponseInterceptor;
    private Brave brave;

    public void setBrave(Brave brave) {
        this.brave = brave;
        this.serverRequestInterceptor = this.brave.serverRequestInterceptor();
        this.serverResponseInterceptor = this.brave.serverResponseInterceptor();
    }

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        String protocol = invoker.getUrl().getProtocol();
        if (protocol.equals(REST.getName())) {
            serverRequestInterceptor.handle(new RestServerRequestAdapter(invocation));
            try {
                Result rpcResult = invoker.invoke(invocation);
                serverResponseInterceptor.handle(new RestServerResponseAdapter(rpcResult));
                return rpcResult;
            } catch (Exception e) {
                serverResponseInterceptor.handle(new RestServerResponseAdapter(e));
                throw e;
            }
        } else if (protocol.equals(DUBBO.getName()) || protocol.equals(THRIFT.getName()) || protocol.equals(THRIFT2.getName())) {
            serverRequestInterceptor.handle(new DubboServerRequestAdapter(invoker, invocation));
            try {
                Result rpcResult = invoker.invoke(invocation);
                serverResponseInterceptor.handle(new DubboServerResponseAdapter(rpcResult));
                return rpcResult;
            } catch (Exception e) {
                serverResponseInterceptor.handle(new DubboServerResponseAdapter(e));
                throw e;
            }
        } else {
            return invoker.invoke(invocation);
        }
    }
}
