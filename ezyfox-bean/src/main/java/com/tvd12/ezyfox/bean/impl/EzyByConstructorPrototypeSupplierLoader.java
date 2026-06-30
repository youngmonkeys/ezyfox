package com.tvd12.ezyfox.bean.impl;

import com.tvd12.ezyfox.reflect.EzyClass;

import java.lang.reflect.Constructor;

public class EzyByConstructorPrototypeSupplierLoader
    extends EzySimplePrototypeSupplierLoader
    implements EzyPrototypeSupplierLoader {

    protected final EzyConstructorMetadata metadata;
    protected final Constructor<?> constructor;

    public EzyByConstructorPrototypeSupplierLoader(
        String beanName,
        EzyClass clazz,
        EzyBeanMetadataCache metadataCache
    ) {
        super(beanName, clazz, metadataCache);
        this.metadata = metadataCache.getConstructor(clazz.getClazz());
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
}
