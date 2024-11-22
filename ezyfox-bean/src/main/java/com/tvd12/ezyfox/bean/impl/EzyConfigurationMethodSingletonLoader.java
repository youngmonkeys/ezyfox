package com.tvd12.ezyfox.bean.impl;

import com.tvd12.ezyfox.bean.EzyBeanContext;
import com.tvd12.ezyfox.reflect.EzyMethod;

import java.util.Map;

public class EzyConfigurationMethodSingletonLoader
    extends EzyByMethodSingletonLoader {

    public EzyConfigurationMethodSingletonLoader(
        String beanName,
        EzyMethod method,
        Object configurator,
        Map<Class<?>, EzyMethod> methodsByType
    ) {
        super(
            beanName,
            method,
            configurator,
            methodsByType
        );
    }

    @SuppressWarnings("rawtypes")
    @Override
    protected Object getOrCreateSingleton(
        EzyBeanContext context,
        String name,
        Class[] parameterTypes
    ) {
        Object singleton = newSingletonByConstructor(
            context,
            parameterTypes
        );
        logger.debug(
            "add singleton with name {} of {}, object = {}",
            name,
            singleton.getClass(),
            singleton
        );
        return singleton;
    }
}
