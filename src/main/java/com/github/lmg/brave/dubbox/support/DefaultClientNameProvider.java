package com.github.lmg.brave.dubbox.support;

import com.alibaba.dubbo.rpc.RpcContext;
import com.github.lmg.brave.dubbox.server.adapter.DubboClientNameProvider;

/**
 * Created by liaomengge on 17/4/13.
 */
public class DefaultClientNameProvider implements DubboClientNameProvider {

    @Override
    public String resolveClientName(RpcContext rpcContext) {
        String application = RpcContext.getContext().getUrl().getParameter("clientName");
        return application;
    }
}
