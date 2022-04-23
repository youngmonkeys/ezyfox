package com.tvd12.ezyfox.bean.impl;

import java.lang.reflect.Constructor;

import com.tvd12.ezyfox.reflect.EzyClass;

public class EzyByConstructorPrototypeSupplierLoader 
        extends EzySimplePrototypeSupplierLoader
        implements EzyPrototypeSupplierLoader {

    protected final Constructor<?> constructor;

    public EzyByConstructorPrototypeSupplierLoader(String beanName, EzyClass clazz) {
        super(beanName, clazz);
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
}
