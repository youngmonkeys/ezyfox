package com.tvd12.ezyfox.database.util;

import static com.tvd12.ezyfox.reflect.EzyClasses.getVariableName;

import com.tvd12.ezyfox.database.annotation.EzyMapstore;
import com.tvd12.ezyfox.io.EzyStrings;

public final class EzyMapstoreAnnotations {

    private EzyMapstoreAnnotations() {}

    public static String getMapName(Object mapstore) {
        return getMapName(mapstore.getClass());
    }

    public static String getMapName(Class<?> clazz) {
        EzyMapstore anno = clazz.getAnnotation(EzyMapstore.class);
        String name = anno.value();
        return EzyStrings.isNoContent(name) ? getVariableName(clazz) : name;
    }
}