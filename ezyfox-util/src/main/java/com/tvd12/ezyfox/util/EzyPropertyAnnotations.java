package com.tvd12.ezyfox.util;

import com.tvd12.ezyfox.annotation.EzyProperty;
import com.tvd12.ezyfox.reflect.EzyClass;
import com.tvd12.ezyfox.reflect.EzyField;
import com.tvd12.ezyfox.reflect.EzyMethod;
import com.tvd12.ezyfox.reflect.EzyReflectElement;

public final class EzyPropertyAnnotations {

    private EzyPropertyAnnotations() {
    }

    public static String getPropertyName(EzyClass clazz, EzyReflectElement element) {
        if (element instanceof EzyField) {
            return getPropertyName(clazz, (EzyField) element);
        }
        return getPropertyName(clazz, (EzyMethod) element);
    }

    public static String getPropertyName(EzyClass clazz, EzyField field) {
        EzyProperty annotation = field.getAnnotation(EzyProperty.class);
        if (annotation.value().length() > 0) {
            return annotation.value();
        }
        return field.getName();
    }

    public static String getPropertyName(EzyClass clazz, EzyMethod method) {
        EzyProperty annotation = method.getAnnotation(EzyProperty.class);
        if (annotation != null && annotation.value().length() > 0) {
            return annotation.value();
        }
        String fieldName = method.getFieldName();
        EzyField field = clazz.getField(fieldName);
        return field != null ? getPropertyName(clazz, field) : fieldName;
    }
}
