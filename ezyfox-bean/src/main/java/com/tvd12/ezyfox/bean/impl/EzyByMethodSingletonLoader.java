package com.tvd12.ezyfox.bean.impl;

import com.tvd12.ezyfox.bean.EzyBeanContext;
import com.tvd12.ezyfox.bean.EzySingletonFactory;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyfox.reflect.EzyMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SuppressWarnings("rawtypes")
public class EzyByMethodSingletonLoader
    extends EzySimpleSingletonLoader
    implements EzySingletonLoader {

    protected final EzyMethod method;

    public EzyByMethodSingletonLoader(
        String beanName,
        EzyMethod method,
        Object configurator,
        Map<EzyBeanKey, EzyMethod> methodsByKey,
        EzyBeanMetadataCache metadataCache
    ) {
        this(
            beanName,
            method,
            configurator,
            methodsByKey,
            new ArrayList<>(),
            metadataCache
        );
    }

    public EzyByMethodSingletonLoader(
        String beanName,
        EzyMethod method,
        Object configurator,
        Map<EzyBeanKey, EzyMethod> methodsByKey,
        List<Class<?>> stackCallClasses,
        EzyBeanMetadataCache metadataCache
    ) {
        super(beanName,
            metadataCache.getClass(method.getReturnType()),
            configurator,
            methodsByKey,
            stackCallClasses,
            metadataCache
        );
        this.method = method;
    }

    @Override
    protected Map getAnnotationProperties() {
        return EzyKeyValueParser.getSingletonProperties(
            method.getAnnotation(EzySingleton.class)
        );
    }

    @Override
    protected Class<?>[] getConstructorParameterTypes() {
        return method.getParameterTypes();
    }

    @Override
    protected Class[] getConstructorParameterTypes(Class clazz) {
        EzyMethod method = getSingletonMethod(clazz);
        return method != null ? method.getParameterTypes() : new Class[0];
    }

    @Override
    protected Object getOrCreateSingleton(
        EzyBeanContext context,
        String name,
        Class[] parameterTypes
    ) {
        EzySingletonFactory factory = context.getSingletonFactory();
        Object singleton = factory.getSingleton(
            EzyBeanKey.of(name, getSingletonClass())
        );
        if (singleton == null) {
            singleton = newSingletonByConstructor(context, parameterTypes);
            logger.debug(
                "add singleton with name {} of {}, object = {}",
                name,
                singleton.getClass(),
                singleton
            );
        }
        return singleton;
    }

    @Override
    protected Object newSingletonByConstructor(
        EzyBeanContext context,
        Class[] parameterTypes
    ) {
        if (parameterTypes.length == 0) {
            return method.invoke(configurator);
        }
        return method.invoke(configurator, getArguments(parameterTypes, context));
    }
}
