package com.tvd12.ezyfox.bean.exception;

import java.lang.reflect.Field;

import com.tvd12.ezyfox.reflect.EzyField;

public class EzyMissingSetterException extends IllegalStateException {
    private static final long serialVersionUID = -9120694192282292802L;

    public EzyMissingSetterException(EzyField field) {
        this(field.getField());
    }

    public EzyMissingSetterException(Field field) {
        super("missing setter for field: " + field.getName() + " on " + field.getDeclaringClass());
    }

}
