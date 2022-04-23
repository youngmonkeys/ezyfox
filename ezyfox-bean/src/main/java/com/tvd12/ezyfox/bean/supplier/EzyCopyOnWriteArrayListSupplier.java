package com.tvd12.ezyfox.bean.supplier;

import java.util.concurrent.CopyOnWriteArrayList;

import com.tvd12.ezyfox.bean.EzyBeanContext;
import com.tvd12.ezyfox.bean.EzyPrototypeSupplier;

public final class EzyCopyOnWriteArrayListSupplier implements EzyPrototypeSupplier {

    private static final EzyCopyOnWriteArrayListSupplier INSTANCE = new EzyCopyOnWriteArrayListSupplier();

    private EzyCopyOnWriteArrayListSupplier() {
    }

    public static EzyCopyOnWriteArrayListSupplier getInstance() {
        return INSTANCE;
    }

    @Override
    public Object supply(EzyBeanContext context) {
        return new CopyOnWriteArrayList<>();
    }

    @Override
    public Class<?> getObjectType() {
        return CopyOnWriteArrayList.class;
    }
}
