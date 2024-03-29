package com.tvd12.ezyfox.bean.supplier;

import com.tvd12.ezyfox.bean.EzyBeanContext;
import com.tvd12.ezyfox.bean.EzyPrototypeSupplier;

import java.util.LinkedList;
import java.util.Queue;

public final class EzyQueueSupplier implements EzyPrototypeSupplier {

    private static final EzyQueueSupplier INSTANCE = new EzyQueueSupplier();

    private EzyQueueSupplier() {}

    public static EzyQueueSupplier getInstance() {
        return INSTANCE;
    }

    @Override
    public Object supply(EzyBeanContext context) {
        return new LinkedList<>();
    }

    @Override
    public Class<?> getObjectType() {
        return Queue.class;
    }
}
