package com.tvd12.ezyfox.bean.supplier;

import com.tvd12.ezyfox.bean.EzyBeanContext;
import com.tvd12.ezyfox.bean.EzyPrototypeSupplier;

import java.util.HashSet;

public final class EzyHashSetSupplier implements EzyPrototypeSupplier {

    private static final EzyHashSetSupplier INSTANCE = new EzyHashSetSupplier();

    private EzyHashSetSupplier() {}

    public static EzyHashSetSupplier getInstance() {
        return INSTANCE;
    }

    @Override
    public Object supply(EzyBeanContext context) {
        return new HashSet<>();
    }

    @Override
    public Class<?> getObjectType() {
        return HashSet.class;
    }
}
