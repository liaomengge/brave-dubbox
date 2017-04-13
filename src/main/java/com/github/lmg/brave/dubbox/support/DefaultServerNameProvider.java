package com.github.lmg.brave.dubbox.support;

import com.alibaba.dubbo.rpc.RpcContext;
import com.github.lmg.brave.dubbox.DubboServerNameProvider;

/**
 * Created by liaomengge on 17/4/13.
 */
public class DefaultServerNameProvider implements DubboServerNameProvider {

    @Override
    public String resolveServerName(RpcContext rpcContext) {
        String application = RpcContext.getContext().getUrl().getParameter("application");
        return application;
    }
}
