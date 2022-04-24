package com.tvd12.ezyfox.bean.supplier;

import com.tvd12.ezyfox.bean.EzyBeanContext;
import com.tvd12.ezyfox.bean.EzyPrototypeSupplier;

import java.util.ArrayList;
import java.util.Collection;

public final class EzyCollectionSupplier implements EzyPrototypeSupplier {

    private static final EzyCollectionSupplier INSTANCE = new EzyCollectionSupplier();

    private EzyCollectionSupplier() {}

    public static EzyCollectionSupplier getInstance() {
        return INSTANCE;
    }

    @Override
    public Object supply(EzyBeanContext context) {
        return new ArrayList<>();
    }

    @Override
    public Class<?> getObjectType() {
        return Collection.class;
    }
}
