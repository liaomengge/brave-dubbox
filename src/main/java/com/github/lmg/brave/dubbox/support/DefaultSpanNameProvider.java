package com.github.lmg.brave.dubbox.support;

import com.alibaba.dubbo.rpc.RpcContext;
import com.github.lmg.brave.dubbox.DubboSpanNameProvider;

/**
 * Created by liaomengge on 17/4/13.
 */
public class DefaultSpanNameProvider implements DubboSpanNameProvider {

    @Override
    public String resolveSpanName(RpcContext rpcContext) {
        String className = rpcContext.getUrl().getPath();
        String simpleName = className.substring(className.lastIndexOf(".") + 1);
        return simpleName + "." + rpcContext.getMethodName();

    }
}
