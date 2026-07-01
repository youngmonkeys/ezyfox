package com.tvd12.ezyfox.bean.impl;

import com.tvd12.ezyfox.bean.EzyBeanContext;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyfox.reflect.EzyField;
import com.tvd12.ezyfox.reflect.EzyMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SuppressWarnings("rawtypes")
public class EzyByFieldSingletonLoader
    extends EzySimpleSingletonLoader
    implements EzySingletonLoader {

    protected final EzyField field;

    public EzyByFieldSingletonLoader(
        String beanName,
        EzyField field,
        Object configurator,
        Map<Class<?>, EzyMethod> methodsByType,
        EzyBeanMetadataCache metadataCache
    ) {
        this(
            beanName,
            field,
            configurator,
            methodsByType,
            new ArrayList<>(),
            metadataCache
        );
    }

    public EzyByFieldSingletonLoader(
        String beanName,
        EzyField field,
        Object configurator,
        Map<Class<?>, EzyMethod> methodsByType,
        List<Class<?>> stackCallClasses,
        EzyBeanMetadataCache metadataCache
    ) {
        super(
            beanName,
            metadataCache.getClass(field.getType()),
            configurator,
            methodsByType,
            stackCallClasses,
            metadataCache
        );
        this.field = field;
    }

    @Override
    protected Map getAnnotationProperties() {
        return EzyKeyValueParser.getSingletonProperties(
            field.getAnnotation(EzySingleton.class)
        );
    }

    @Override
    protected Class<?>[] getConstructorParameterTypes() {
        return new Class[0];
    }

    @Override
    protected Class[] getConstructorParameterTypes(Class clazz) {
        return new Class[0];
    }

    @Override
    protected Object newSingletonByConstructor(
        EzyBeanContext context,
        Class[] parameterTypes
    ) {
        return field.get(configurator);
    }

    @Override
    protected String[] getConstructorArgumentNames() {
        return new String[0];
    }

    @Override
    protected void detectCircularDependency(Class[] parameterTypes, StringBuilder log) {}
}
