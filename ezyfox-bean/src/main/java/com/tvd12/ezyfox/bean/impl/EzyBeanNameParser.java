package com.tvd12.ezyfox.bean.impl;

import com.tvd12.ezyfox.bean.annotation.EzyPrototype;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyfox.io.EzyStrings;
import com.tvd12.ezyfox.reflect.EzyClasses;
import com.tvd12.ezyfox.reflect.EzyField;
import com.tvd12.ezyfox.reflect.EzyMethod;

public final class EzyBeanNameParser {

    private EzyBeanNameParser() {}

    public static String getBeanName(Class<?> clazz) {
        if (clazz.isAnnotationPresent(EzySingleton.class)) {
            return getSingletonName(clazz);
        }
        if (clazz.isAnnotationPresent(EzyPrototype.class)) {
            return getPrototypeName(clazz);
        }
        return EzyClasses.getVariableName(clazz);
    }

    // ============== parse singleton ==================
    public static String getSingletonName(EzyField field) {
        return getSingletonName(field.getAnnotation(EzySingleton.class), field.getName());
    }

    public static String getSingletonName(EzyMethod method) {
        return getSingletonName(method.getAnnotation(EzySingleton.class), method.getFieldName());
    }

    public static String getSingletonName(Class<?> clazz) {
        return getSingletonName(clazz, clazz.getAnnotation(EzySingleton.class));
    }

    public static String getSingletonName(Class<?> clazz, EzySingleton annotation) {
        return getSingletonName(annotation, EzyClasses.getVariableName(clazz, "Impl"));
    }

    public static String getSingletonName(EzySingleton annotation, String defaultName) {
        if (annotation == null) {
            return defaultName;
        }
        String value = annotation.value();
        if (EzyStrings.isNoContent(value)) {
            return defaultName;
        }
        return value;
    }

    // ============ parse prototype ==========
    public static String getPrototypeName(EzyField field) {
        return getPrototypeName(field.getAnnotation(EzyPrototype.class), field.getName());
    }

    public static String getPrototypeName(EzyMethod method) {
        return getPrototypeName(method.getAnnotation(EzyPrototype.class), method.getFieldName());
    }

    public static String getPrototypeName(Class<?> clazz) {
        return getPrototypeName(clazz, clazz.getAnnotation(EzyPrototype.class));
    }

    public static String getPrototypeName(Class<?> clazz, EzyPrototype annotation) {
        return getPrototypeName(annotation, EzyClasses.getVariableName(clazz, "Impl"));
    }

    public static String getPrototypeName(EzyPrototype annotation, String defaultName) {
        if (annotation == null) {
            return defaultName;
        }
        String value = annotation.value();
        if (EzyStrings.isNoContent(value)) {
            return defaultName;
        }
        return value;
    }
}
