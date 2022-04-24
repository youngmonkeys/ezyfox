package com.tvd12.ezyfox.bean.supplier;

import com.tvd12.ezyfox.bean.EzyBeanContext;
import com.tvd12.ezyfox.bean.EzyPrototypeSupplier;

import java.util.ArrayList;

public final class EzyArrayListSupplier implements EzyPrototypeSupplier {

    private static final EzyArrayListSupplier INSTANCE = new EzyArrayListSupplier();

    private EzyArrayListSupplier() {}

    public static EzyArrayListSupplier getInstance() {
        return INSTANCE;
    }

    @Override
    public Object supply(EzyBeanContext context) {
        return new ArrayList<>();
    }

    @Override
    public Class<?> getObjectType() {
        return ArrayList.class;
    }
}
