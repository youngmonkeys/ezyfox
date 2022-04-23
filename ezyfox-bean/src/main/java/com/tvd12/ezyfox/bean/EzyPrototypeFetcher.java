package com.tvd12.ezyfox.bean;

import java.util.List;
import java.util.Map;

@SuppressWarnings("rawtypes")
public interface EzyPrototypeFetcher {

    <T> T getPrototype(Class<T> type);

    <T> T getPrototype(String name, Class<T> type);

    <T> T getAnnotatedPrototype(Class annotationClass);

    <T> T getPrototype(Map properties);

    List getPrototypes(Map properties);

    EzyPrototypeSupplier getPrototypeSupplier(Map properties);

    List<EzyPrototypeSupplier> getPrototypeSuppliers(Map properties);

    List<EzyPrototypeSupplier> getPrototypeSuppliers(Class annotationClass);
}
