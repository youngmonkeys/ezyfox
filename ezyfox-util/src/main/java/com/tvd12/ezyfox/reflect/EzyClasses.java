package com.tvd12.ezyfox.reflect;

import com.tvd12.ezyfox.collect.Sets;

import java.lang.reflect.Constructor;
import java.util.HashSet;
import java.util.Set;

public final class EzyClasses {

    private static final String DOT = ".";

    private EzyClasses() {}

    @SuppressWarnings("rawtypes")
    public static String getSimpleName(Class clazz) {
        String simpleName = clazz.getSimpleName();
        if (!simpleName.isEmpty()) {
            return simpleName;
        }
        String fullName = clazz.getName();
        if (!fullName.contains(DOT)) {
            return fullName;
        }
        return fullName.substring(fullName.lastIndexOf(DOT) + 1);
    }

    @SuppressWarnings("rawtypes")
    public static String getVariableName(Class clazz) {
        return getVariableName(clazz, "");
    }

    @SuppressWarnings("rawtypes")
    public static String getVariableName(Class clazz, String ignoredSuffix) {
        String name = getSimpleName(clazz);
        String vname = name.substring(0, 1).toLowerCase() + name.substring(1);
        if (ignoredSuffix.isEmpty()
            || !vname.endsWith(ignoredSuffix)
            || vname.length() == ignoredSuffix.length()) {
            return vname;
        }
        int endIndex = vname.indexOf(ignoredSuffix);
        return vname.substring(0, endIndex);

    }

    @SuppressWarnings("rawtypes")
    public static Class getClass(String className) {
        try {
            return Class.forName(className);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    @SuppressWarnings("rawtypes")
    public static Class getClass(String className, ClassLoader classLoader) {
        try {
            return Class.forName(className, true, classLoader);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static <T> T newInstance(String className) {
        return newInstance(className, new Class<?>[0], new Object[0]);
    }

    @SuppressWarnings("unchecked")
    public static <T> T newInstance(
        String className,
        Class<?>[] constructorParameterTypes,
        Object[] constructorParameterValues) {
        try {
            return (T) getClass(className)
                .getDeclaredConstructor(constructorParameterTypes)
                .newInstance(constructorParameterValues);
        } catch (Exception e) {
            try {
                return (T) getClass(className).newInstance();
            } catch (Exception ex) {
                throw new IllegalArgumentException(e);
            }
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T newInstance(String className, ClassLoader classLoader) {
        try {
            return (T) getClass(className, classLoader).newInstance();
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static <T> T newInstance(Class<T> clazz) {
        try {
            return clazz.newInstance();
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static <T> T newInstance(Constructor<T> constructor, Object... arguments) {
        try {
            return constructor.newInstance(arguments);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static <T> Constructor<T> getConstructor(Class<T> clazz, Class<?>... paramTypes) {
        try {
            return clazz.getDeclaredConstructor(paramTypes);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    @SuppressWarnings("rawtypes")
    public static Set<Class> flatSuperClasses(Class clazz) {
        return flatSuperClasses(clazz, false);
    }

    @SuppressWarnings("rawtypes")
    public static Set<Class> flatSuperClasses(Class clazz, boolean includeObject) {
        Set<Class> classes = new HashSet<>();
        Class superClass = clazz.getSuperclass();
        while (superClass != null) {
            if (superClass.equals(Object.class) && !includeObject) {
                break;
            }
            classes.add(superClass);
            superClass = superClass.getSuperclass();
        }
        return classes;
    }

    @SuppressWarnings("rawtypes")
    public static Set<Class> flatInterfaces(Class clazz) {
        Class[] interfaces = clazz.getInterfaces();
        Set<Class> classes = new HashSet<>(Sets.newHashSet(interfaces));
        for (Class itf : interfaces) {
            classes.addAll(flatInterfaces(itf));
        }
        return classes;
    }

    @SuppressWarnings("rawtypes")
    public static Set<Class> flatSuperAndInterfaceClasses(Class clazz) {
        return flatSuperAndInterfaceClasses(clazz, false);
    }

    @SuppressWarnings("rawtypes")
    public static Set<Class> flatSuperAndInterfaceClasses(Class clazz, boolean includeObject) {
        Set<Class> interfaces = flatInterfaces(clazz);
        Set<Class> superClasses = flatSuperClasses(clazz, includeObject);
        Set<Class> classes = new HashSet<>(interfaces);
        for (Class superClass : superClasses) {
            Set<Class> superAndInterfaceClasses = flatSuperAndInterfaceClasses(superClass, includeObject);
            classes.add(superClass);
            classes.addAll(superAndInterfaceClasses);
        }
        return classes;
    }
}
