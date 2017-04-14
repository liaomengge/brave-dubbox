package com.github.lmg.brave.dubbox.support.defaults;

import com.alibaba.dubbo.rpc.Invocation;
import com.github.lmg.brave.dubbox.support.DubboClientNameProvider;

/**
 * Created by liaomengge on 17/4/13.
 */
public class DefaultClientNameProvider implements DubboClientNameProvider {

    @Override
    public String resolveClientName(Invocation invocation) {
        return invocation.getAttachment("clientName");
    }
}
