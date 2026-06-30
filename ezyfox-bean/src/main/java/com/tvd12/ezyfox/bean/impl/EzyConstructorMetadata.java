package com.tvd12.ezyfox.bean.impl;

import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.reflect.EzyClass;
import com.tvd12.ezyfox.reflect.EzyClasses;
import lombok.Getter;

import java.lang.reflect.Constructor;
import java.util.List;

public final class EzyConstructorMetadata {

    @Getter
    private final Constructor<?> constructor;
    private final Class<?>[] parameterTypes;
    private final String[] argumentNames;

    public EzyConstructorMetadata(EzyClass clazz) {
        Constructor<?> constructor = getConstructor(clazz);
        this.constructor = constructor;
        this.parameterTypes = constructor.getParameterTypes();
        this.argumentNames = getArgumentNames(
            parameterTypes,
            constructor.getAnnotation(EzyAutoBind.class)
        );
    }

    @SuppressWarnings("rawtypes")
    private static Constructor<?> getConstructor(EzyClass clazz) {
        List<Constructor> constructors = clazz.getDeclaredConstructors();
        for (Constructor con : constructors) {
            if (con.isAnnotationPresent(EzyAutoBind.class)) {
                return con;
            }
        }
        Constructor con = clazz.getNoArgsDeclaredConstructor();
        if (con == null) {
            con = clazz.getMaxArgsDeclaredConstructor();
        }
        return con;
    }

    public Class<?>[] getParameterTypes() {
        return parameterTypes.clone();
    }

    public String[] getArgumentNames() {
        return argumentNames.clone();
    }

    private static String[] getArgumentNames(
        Class<?>[] parameterTypes,
        EzyAutoBind annotation
    ) {
        String[] names = getArgumentNames(parameterTypes);
        if (annotation == null) {
            return names;
        }
        String[] fixNames = annotation.value();
        for (int i = 0; i < fixNames.length; i++) {
            if (i < names.length) {
                names[i] = fixNames[i];
            }
        }
        return names;
    }

    private static String[] getArgumentNames(
        Class<?>[] parameterTypes
    ) {
        String[] names = new String[parameterTypes.length];
        for (int i = 0; i < parameterTypes.length; i++) {
            names[i] = EzyClasses.getVariableName(parameterTypes[i]);
        }
        return names;
    }
}
