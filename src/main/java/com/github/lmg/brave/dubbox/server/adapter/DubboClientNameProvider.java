package com.github.lmg.brave.dubbox.server.adapter;

import com.alibaba.dubbo.rpc.RpcContext;

/**
 * Created by liaomengge on 17/4/13.
 */
public interface DubboClientNameProvider {
    public String resolveClientName(RpcContext rpcContext);
}
