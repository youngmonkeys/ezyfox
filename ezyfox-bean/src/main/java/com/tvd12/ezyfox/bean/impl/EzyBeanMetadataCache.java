package com.tvd12.ezyfox.bean.impl;

import com.tvd12.ezyfox.reflect.EzyClass;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("rawtypes")
public class EzyBeanMetadataCache {

    private final Map<Class, EzyClass> classes;
    private final Map<Class, EzyConstructorMetadata> constructors;
    private final Map<Class, EzyObjectBuilderMetadata> singletonObjectBuilders;
    private final Map<Class, EzyObjectBuilderMetadata> prototypeObjectBuilders;

    public EzyBeanMetadataCache() {
        this.classes = new HashMap<>();
        this.constructors = new HashMap<>();
        this.singletonObjectBuilders = new HashMap<>();
        this.prototypeObjectBuilders = new HashMap<>();
    }

    public EzyClass getClass(Class type) {
        return classes.computeIfAbsent(type, EzyClass::new);
    }

    public EzyConstructorMetadata getConstructor(Class type) {
        return constructors.computeIfAbsent(type, it ->
            new EzyConstructorMetadata(getClass(it))
        );
    }

    public EzyObjectBuilderMetadata getObjectBuilder(
        Class type,
        boolean addMissingSetterFields
    ) {
        return getObjectBuilderMap(addMissingSetterFields)
            .computeIfAbsent(type, it ->
                new EzyObjectBuilderMetadata(
                    getClass(it),
                    addMissingSetterFields
                )
            );
    }

    private Map<Class, EzyObjectBuilderMetadata> getObjectBuilderMap(
        boolean addMissingSetterFields
    ) {
        return addMissingSetterFields
            ? singletonObjectBuilders
            : prototypeObjectBuilders;
    }
}
