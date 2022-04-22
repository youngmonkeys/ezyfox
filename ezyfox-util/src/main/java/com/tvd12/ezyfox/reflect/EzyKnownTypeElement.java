package com.tvd12.ezyfox.reflect;

public interface EzyKnownTypeElement {

    @SuppressWarnings("rawtypes")
    Class getType();

    default String getTypeName() {
        return getType().getTypeName();
    }
}