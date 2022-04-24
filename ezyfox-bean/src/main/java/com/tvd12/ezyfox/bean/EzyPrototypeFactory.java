package com.tvd12.ezyfox.bean;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

@SuppressWarnings("rawtypes")
public interface EzyPrototypeFactory {

    EzyPrototypeSupplier getSupplier(Class objectType);

    EzyPrototypeSupplier getSupplier(String objectName, Class objectType);

    EzyPrototypeSupplier getSupplier(Map properties);

    EzyPrototypeSupplier getAnnotatedSupplier(Class annotationClass);

    List<EzyPrototypeSupplier> getSuppliers();

    List<EzyPrototypeSupplier> getSuppliers(Map properties);

    List<EzyPrototypeSupplier> getSuppliers(Class... annotationClasses);

    List<EzyPrototypeSupplier> getSuppliers(Predicate<EzyPrototypeSupplier> filter);

    List<EzyPrototypeSupplier> getSuppliersOf(Class parentClass);

    Map getProperties(EzyPrototypeSupplier supplier);

    void addSupplier(EzyPrototypeSupplier supplier);

    void addSupplier(String objectName, EzyPrototypeSupplier supplier);

    void addSupplier(String objectName, EzyPrototypeSupplier supplier, Map properties);
}
