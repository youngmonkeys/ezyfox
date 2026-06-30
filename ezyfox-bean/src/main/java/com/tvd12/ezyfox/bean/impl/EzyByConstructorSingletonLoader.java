package com.tvd12.ezyfox.bean.impl;

import com.tvd12.ezyfox.bean.EzyBeanContext;
import com.tvd12.ezyfox.reflect.EzyClass;

import java.lang.reflect.Constructor;
import java.util.List;

@SuppressWarnings("rawtypes")
public class EzyByConstructorSingletonLoader
    extends EzySimpleSingletonLoader
    implements EzySingletonLoader {

    protected final EzyConstructorMetadata metadata;
    protected final Constructor<?> constructor;

    protected EzyByConstructorSingletonLoader(
        String beanName, EzyClass clazz,
        List<Class<?>> stackCallClasses,
        EzyBeanMetadataCache metadataCache
    ) {
        super(
            beanName,
            clazz,
            stackCallClasses, metadataCache
        );
        this.metadata = metadataCache
            .getConstructor(clazz.getClazz());
        this.constructor = metadata.getConstructor();
    }

    @Override
    protected String[] getConstructorArgumentNames() {
        return metadata.getArgumentNames();
    }

    @Override
    protected Class<?>[] getConstructorParameterTypes() {
        return metadata.getParameterTypes();
    }

    @Override
    protected Object newSingletonByConstructor(
        EzyBeanContext context, Class[] parameterTypes) throws Exception {
        if (parameterTypes.length == 0) {
            return clazz.newInstance();
        }
        return constructor.newInstance(getArguments(parameterTypes, context));
    }
}
