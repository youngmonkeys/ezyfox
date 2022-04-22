package com.tvd12.ezyfox.bean;

public interface EzyPrototypeSupplier {

    Class<?> getObjectType();

    Object supply(EzyBeanContext context);
}