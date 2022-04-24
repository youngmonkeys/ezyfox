package com.tvd12.ezyfox.bean.impl;

import com.tvd12.ezyfox.bean.EzyBeanContext;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyfox.reflect.EzyClass;
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
        Map<Class<?>, EzyMethod> methodsByType
    ) {
        this(beanName, method, configurator, methodsByType, new ArrayList<>());
    }

    public EzyByMethodSingletonLoader(
        String beanName,
        EzyMethod method,
        Object configurator,
        Map<Class<?>, EzyMethod> methodsByType,
        List<Class<?>> stackCallClasses
    ) {
        super(beanName,
            new EzyClass(method.getReturnType()),
            configurator,
            methodsByType,
            stackCallClasses
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
        EzyMethod method = methodsByType.get(clazz);
        return method != null ? method.getParameterTypes() : new Class[0];
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
