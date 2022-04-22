package com.tvd12.ezyfox.bean.supplier;

import java.util.HashMap;

import com.tvd12.ezyfox.bean.EzyBeanContext;
import com.tvd12.ezyfox.bean.EzyPrototypeSupplier;

public final class EzyHashMapSupplier implements EzyPrototypeSupplier {

    private static final EzyHashMapSupplier INSTANCE = new EzyHashMapSupplier();

    private EzyHashMapSupplier() {
    }

    public static EzyHashMapSupplier getInstance() {
        return INSTANCE;
    }

    @Override
    public Object supply(EzyBeanContext context) {
        return new HashMap<>();
    }

    @Override
    public Class<?> getObjectType() {
        return HashMap.class;
    }

}
