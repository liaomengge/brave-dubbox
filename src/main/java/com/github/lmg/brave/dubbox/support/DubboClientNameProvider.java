package com.github.lmg.brave.dubbox.support;

import com.alibaba.dubbo.rpc.Invocation;

/**
 * Created by liaomengge on 17/4/13.
 */
public interface DubboClientNameProvider {

    String resolveClientName(Invocation invocation);
}
