package com.tvd12.ezyfox.reflect;

import java.util.Set;

public final class EzyInterfaces {

    private EzyInterfaces() {
    }

    @SuppressWarnings("rawtypes")
    public static Class getInterface(Class clazz, Class interfaceClass) {
        Set<Class> interfaces = EzyClasses.flatInterfaces(clazz);
        return getInterface(interfaces, interfaceClass);
    }

    @SuppressWarnings("rawtypes")
    public static Class getInterfaceAnyway(Class clazz, Class interfaceClass) {
        Set<Class> classes = EzyClasses.flatSuperAndInterfaceClasses(clazz);
        return getInterface(classes, interfaceClass);
    }

    @SuppressWarnings("rawtypes")
    private static Class getInterface(Set<Class> classes, Class interfaceClass) {
        for (Class itf : classes) {
            if (itf.equals(interfaceClass)) {
                return itf;
            }
        }
        return null;
    }
}
