package com.tvd12.ezyfox.bean.supplier;

import com.tvd12.ezyfox.bean.EzyBeanContext;
import com.tvd12.ezyfox.bean.EzyPrototypeSupplier;

import java.util.concurrent.CopyOnWriteArraySet;

public final class EzyCopyOnWriteArraySetSupplier implements EzyPrototypeSupplier {

    private static final EzyCopyOnWriteArraySetSupplier INSTANCE = new EzyCopyOnWriteArraySetSupplier();

    private EzyCopyOnWriteArraySetSupplier() {}

    public static EzyCopyOnWriteArraySetSupplier getInstance() {
        return INSTANCE;
    }

    @Override
    public Object supply(EzyBeanContext context) {
        return new CopyOnWriteArraySet<>();
    }

    @Override
    public Class<?> getObjectType() {
        return CopyOnWriteArraySet.class;
    }
}
