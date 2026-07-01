package com.tvd12.ezyfox.bean.supplier;

import com.tvd12.ezyfox.bean.EzyBeanContext;
import com.tvd12.ezyfox.bean.EzyPrototypeSupplier;

import java.util.ArrayDeque;
import java.util.Deque;

public final class EzyDequeSupplier implements EzyPrototypeSupplier {

    private static final EzyDequeSupplier INSTANCE = new EzyDequeSupplier();

    private EzyDequeSupplier() {}

    public static EzyDequeSupplier getInstance() {
        return INSTANCE;
    }

    @Override
    public Object supply(EzyBeanContext context) {
        return new ArrayDeque<>();
    }

    @Override
    public Class<?> getObjectType() {
        return Deque.class;
    }
}
