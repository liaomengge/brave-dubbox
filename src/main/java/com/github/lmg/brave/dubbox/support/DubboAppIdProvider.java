package com.github.lmg.brave.dubbox.support;

import com.alibaba.dubbo.rpc.Invocation;

/**
 * Created by liaomengge on 17/6/12.
 */
public interface DubboAppIdProvider {

    String resolveAppId(Invocation invocation);
}
