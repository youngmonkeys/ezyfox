package com.tvd12.ezyfox.reflect;

import java.lang.annotation.Annotation;
import java.util.Set;

public interface EzyReflection {

	Set<Class<?>> getAnnotatedClasses(Class<? extends Annotation> annotationClass);
	
}
