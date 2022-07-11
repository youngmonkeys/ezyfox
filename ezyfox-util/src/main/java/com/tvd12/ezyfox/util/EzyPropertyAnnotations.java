package com.tvd12.ezyfox.util;

import com.tvd12.ezyfox.annotation.EzyProperty;
import com.tvd12.ezyfox.reflect.EzyClass;
import com.tvd12.ezyfox.reflect.EzyField;
import com.tvd12.ezyfox.reflect.EzyMethod;
import com.tvd12.ezyfox.reflect.EzyReflectElement;
import com.tvd12.properties.file.annotation.Property;

import static com.tvd12.ezyfox.io.EzyStrings.isEmpty;

public final class EzyPropertyAnnotations {

    private EzyPropertyAnnotations() {}

    public static String getPropertyName(
        EzyClass clazz,
        EzyReflectElement element
    ) {
        if (element instanceof EzyField) {
            return getPropertyName((EzyField) element);
        }
        return getPropertyName(clazz, (EzyMethod) element);
    }

    public static String getPropertyName(EzyField field) {
        String propertyName = null;
        Property propertyAnno = field.getAnnotation(Property.class);
        if (propertyAnno != null) {
            propertyName = propertyAnno.value();
        }
        if (isEmpty(propertyName)) {
            EzyProperty ezyPropertyAnno =
                field.getAnnotation(EzyProperty.class);
            if (ezyPropertyAnno != null) {
                propertyName = ezyPropertyAnno.value();
            }
        }
        if (isEmpty(propertyName)) {
            propertyName = field.getName();
        }
        return propertyName;
    }

    public static String getPropertyName(
        EzyClass clazz,
        EzyMethod method
    ) {
        String propertyName = null;
        Property propertyAnno = method.getAnnotation(Property.class);
        if (propertyAnno != null) {
            propertyName = propertyAnno.value();
        }
        if (isEmpty(propertyName)) {
            EzyProperty ezyPropertyAnno =
                method.getAnnotation(EzyProperty.class);
            if (ezyPropertyAnno != null) {
                propertyName = ezyPropertyAnno.value();
            }
        }
        if (isEmpty(propertyName)) {
            String fieldName = method.getFieldName();
            EzyField field = clazz.getField(fieldName);
            propertyName = field != null ? getPropertyName(field) : fieldName;
        }
        return propertyName;
    }
}
