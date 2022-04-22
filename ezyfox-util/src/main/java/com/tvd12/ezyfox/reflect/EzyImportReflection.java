package com.tvd12.ezyfox.reflect;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.tvd12.ezyfox.annotation.EzyImport;

public class EzyImportReflection implements EzyReflection {

    private final Set<Class<?>> classes = new HashSet<>();

    public EzyImportReflection(Set<Class<?>> annotatedClasses) {
        for(Class<?> clazz : annotatedClasses) {
            EzyImport ann = clazz.getAnnotation(EzyImport.class);
            if(ann != null)
                classes.addAll(Arrays.asList(ann.value()));
        }
    }

    @Override
    public Set<Class<?>> getExtendsClasses(Class<?> parentClass) {
        Set<Class<?>> answer = new HashSet<>();
        for(Class<?> clazz : classes) {
            if(parentClass.isAssignableFrom(clazz))
                answer.add(clazz);
        }
        return answer;
    }

    @Override
    public Set<Class<?>> getAnnotatedClasses(Class<? extends Annotation> annotationClass) {
        Set<Class<?>> answer = new HashSet<>();
        for(Class<?> clazz : classes) {
            if(clazz.isAnnotationPresent(annotationClass))
                answer.add(clazz);
        }
        return answer;
    }

}
