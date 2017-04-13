package com.github.lmg.brave.dubbox.support;

import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.github.lmg.brave.dubbox.DubboSpanNameProvider;

/**
 * Created by liaomengge on 17/4/13.
 */
public class DefaultSpanNameProvider implements DubboSpanNameProvider {

    @Override
    public String resolveSpanName(Invoker<?> invoker, Invocation invocation) {
        return invoker.getInterface().getSimpleName() + "." + invocation.getMethodName();
    }

}
