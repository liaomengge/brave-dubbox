package com.github.lmg.brave.dubbox.support.defaults;

import com.alibaba.dubbo.rpc.Invocation;
import com.github.lmg.brave.dubbox.support.DubboAppIdProvider;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by liaomengge on 17/6/12.
 */
public class DefaultDubboAppIdProvider implements DubboAppIdProvider {

    private final String DEFAULT_APP_ID = "000";

    @Override
    public String resolveAppId(Invocation invocation) {
        String appId = DEFAULT_APP_ID;
        Object[] args = invocation.getArguments();
        if (ArrayUtils.isNotEmpty(args)) {
            Object obj = args[0];
            if (obj != null) {
                try {
                    appId = StringUtils.defaultString(BeanUtils.getProperty(obj, "appId"), DEFAULT_APP_ID);
                } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                    e.printStackTrace();
                }
            }
        }
        return appId;
    }
}
