package com.github.lmg.brave.dubbox;

import com.alibaba.dubbo.rpc.Invoker;

/**
 * Created by liaomengge on 17/4/13.
 */
public interface DubboServerNameProvider {

    String resolveServerName(Invoker<?> invoker);
}
