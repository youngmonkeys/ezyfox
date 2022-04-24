package com.tvd12.ezyfox.bean.supplier;

import com.tvd12.ezyfox.bean.EzyBeanContext;
import com.tvd12.ezyfox.bean.EzyPrototypeSupplier;

import java.util.Stack;

public final class EzyStackSupplier implements EzyPrototypeSupplier {

    private static final EzyStackSupplier INSTANCE = new EzyStackSupplier();

    private EzyStackSupplier() {}

    public static EzyStackSupplier getInstance() {
        return INSTANCE;
    }

    @Override
    public Object supply(EzyBeanContext context) {
        return new Stack<>();
    }

    @Override
    public Class<?> getObjectType() {
        return Stack.class;
    }
}
