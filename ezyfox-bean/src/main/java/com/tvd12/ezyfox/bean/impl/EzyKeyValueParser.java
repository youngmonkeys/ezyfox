package com.tvd12.ezyfox.bean.impl;

import com.tvd12.ezyfox.annotation.EzyKeyValue;
import com.tvd12.ezyfox.bean.annotation.EzyPrototype;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyfox.util.EzyKeyValueAnnotations;

import java.util.Map;

@SuppressWarnings({"rawtypes"})
public final class EzyKeyValueParser {

    private EzyKeyValueParser() {}

    public static Map getSingletonProperties(Class<?> clazz) {
        return getSingletonProperties(clazz.getAnnotation(EzySingleton.class));
    }

    public static Map getSingletonProperties(EzySingleton annotation) {
        EzyKeyValue[] keyValues = annotation != null ? annotation.properties() : new EzyKeyValue[0];
        return EzyKeyValueAnnotations.getProperties(keyValues);
    }

    public static Map getPrototypeProperties(Class<?> clazz) {
        return getPrototypeProperties(clazz.getAnnotation(EzyPrototype.class));
    }

    public static Map getPrototypeProperties(EzyPrototype annotation) {
        EzyKeyValue[] keyValues = annotation != null ? annotation.properties() : new EzyKeyValue[0];
        return EzyKeyValueAnnotations.getProperties(keyValues);
    }
}
