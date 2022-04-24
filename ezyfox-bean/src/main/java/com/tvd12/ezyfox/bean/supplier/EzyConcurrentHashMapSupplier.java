package com.tvd12.ezyfox.bean.supplier;

import com.tvd12.ezyfox.bean.EzyBeanContext;
import com.tvd12.ezyfox.bean.EzyPrototypeSupplier;

import java.util.concurrent.ConcurrentHashMap;

public final class EzyConcurrentHashMapSupplier implements EzyPrototypeSupplier {

    private static final EzyConcurrentHashMapSupplier INSTANCE = new EzyConcurrentHashMapSupplier();

    private EzyConcurrentHashMapSupplier() {}

    public static EzyConcurrentHashMapSupplier getInstance() {
        return INSTANCE;
    }

    @Override
    public Object supply(EzyBeanContext context) {
        return new ConcurrentHashMap<>();
    }

    @Override
    public Class<?> getObjectType() {
        return ConcurrentHashMap.class;
    }
}
