package com.github.lmg.brave.dubbox;

import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;

/**
 * Created by liaomengge on 17/4/13.
 */
public interface DubboSpanNameProvider {

    String resolveSpanName(Invoker<?> invoker, Invocation invocation);
}
