package com.tvd12.ezyfox.reflect;

import com.tvd12.ezyfox.io.EzySets;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

public interface EzyReflection {

    Set<Class<?>> getExtendsClasses(Class<?> parentClass);

    default Class<?> getExtendsClass(Class<?> parentClass) {
        Set<Class<?>> set = getExtendsClasses(parentClass);
        for (Class<?> clazz : set) {
            return clazz;
        }
        return null;
    }

    default Class<?> getAnnotatedClass(Class<? extends Annotation> annotationClasses) {
        Set<Class<?>> set = getAnnotatedClasses(annotationClasses);
        for (Class<?> clazz : set) {
            return clazz;
        }
        return null;
    }

    Set<Class<?>> getAnnotatedClasses(Class<? extends Annotation> annotationClass);

    @SuppressWarnings({"rawtypes", "unchecked"})
    default Set<Class<?>> getAnnotatedClasses(Set<?> annotationClasses) {
        Set<Class<?>> answer = new HashSet<>();
        for (Object annotationClass : annotationClasses) {
            answer.addAll(getAnnotatedClasses((Class) annotationClass));
        }
        return answer;
    }

    default Set<Class<?>> getAnnotatedClasses(
        Class<? extends Annotation> annotationClass,
        Predicate<Class<?>> filter) {
        return EzySets.filter(getAnnotatedClasses(annotationClass), filter);
    }

    default Set<Class<?>> getAnnotatedExtendsClasses(
        Class<? extends Annotation> annotationClass,
        Class<?> parentClass) {
        return getAnnotatedClasses(annotationClass, parentClass::isAssignableFrom);
    }
}
