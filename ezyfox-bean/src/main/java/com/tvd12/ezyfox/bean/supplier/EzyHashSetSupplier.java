package com.tvd12.ezyfox.bean.supplier;

import java.util.HashSet;

import com.tvd12.ezyfox.bean.EzyBeanContext;
import com.tvd12.ezyfox.bean.EzyPrototypeSupplier;

public final class EzyHashSetSupplier implements EzyPrototypeSupplier {

    private static final EzyHashSetSupplier INSTANCE = new EzyHashSetSupplier();

    private EzyHashSetSupplier() {
    }

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
