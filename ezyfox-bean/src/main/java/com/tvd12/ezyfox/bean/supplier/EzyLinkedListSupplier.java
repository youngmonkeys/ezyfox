package com.tvd12.ezyfox.bean.supplier;

import java.util.LinkedList;

import com.tvd12.ezyfox.bean.EzyBeanContext;
import com.tvd12.ezyfox.bean.EzyPrototypeSupplier;

public final class EzyLinkedListSupplier implements EzyPrototypeSupplier {

    private static final EzyLinkedListSupplier INSTANCE = new EzyLinkedListSupplier();

    private EzyLinkedListSupplier() {
    }

    public static EzyLinkedListSupplier getInstance() {
        return INSTANCE;
    }

    @Override
    public Object supply(EzyBeanContext context) {
        return new LinkedList<>();
    }

    @Override
    public Class<?> getObjectType() {
        return LinkedList.class;
    }
}