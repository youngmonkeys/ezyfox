package com.tvd12.ezyfox.reflect;

import java.lang.annotation.Annotation;
import java.util.Set;

public interface EzyReflection {
	
	Set<Class<?>> getExtendsClasses(Class<?> parentClass);
	
	Set<Class<?>> getAnnotatedClasses(Set<?> annotationClasses);
	
	Set<Class<?>> getAnnotatedClasses(Class<? extends Annotation> annotationClass);
	
	default Class<?> getExtendsClass(Class<?> parentClass) {
		Set<Class<?>> set = getExtendsClasses(parentClass);
		for(Class<?> clazz : set)
			return clazz;
		return null;
	}
	
	default Class<?> getAnnotatedClass(Class<? extends Annotation> annotationClasses) {
		Set<Class<?>> set = getAnnotatedClasses(annotationClasses);
		for(Class<?> clazz : set)
			return clazz;
		return null;
	}
	
}
