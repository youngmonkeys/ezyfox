package com.tvd12.ezyfox.reflect;

import java.lang.annotation.Annotation;
import java.util.Set;

public interface EzyReflection {

	Set<Class<?>> getExtendsClasses(Class<?> parentClass);
	
	Set<Class<?>> getAnnotatedClasses(Set<?> annotationClasses);

	Set<Class<?>> getAnnotatedClasses(Class<? extends Annotation> annotationClass);
	
}
