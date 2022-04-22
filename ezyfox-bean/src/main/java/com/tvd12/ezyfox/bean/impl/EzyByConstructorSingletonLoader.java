package com.tvd12.ezyfox.bean.impl;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import com.tvd12.ezyfox.bean.EzyBeanContext;
import com.tvd12.ezyfox.reflect.EzyClass;

@SuppressWarnings("rawtypes")
public class EzyByConstructorSingletonLoader
        extends EzySimpleSingletonLoader
        implements EzySingletonLoader {

    protected final Constructor<?> constructor;

    protected EzyByConstructorSingletonLoader(String beanName, EzyClass clazz) {
        this(beanName, clazz, new ArrayList<>());
    }

    protected EzyByConstructorSingletonLoader(
            String beanName, EzyClass clazz, List<Class<?>> stackCallClasses) {
        super(beanName, clazz, stackCallClasses);
        this.constructor = getConstructor(clazz);
    }

    @Override
    protected String[] getConstructorArgumentNames() {
        return getConstructorArgumentNames(constructor);
    }

    @Override
    protected Class<?>[] getConstructorParameterTypes() {
        return constructor.getParameterTypes();
    }

    @Override
    protected Object newSingletonByConstructor(
            EzyBeanContext context, Class[] parameterTypes) throws Exception {
        if(parameterTypes.length == 0)
            return clazz.newInstance();
        return constructor.newInstance(getArguments(parameterTypes, context));
    }

}
