package com.tvd12.ezyfox.reflect;

import java.lang.annotation.Annotation;
import java.util.Set;

import com.tvd12.reflections.Reflections;

public class EzyReflectionProxy implements EzyReflection {
	
	private final Reflections reflections;
	
	public EzyReflectionProxy(String packet) {
		this.reflections = new Reflections(packet);
	}
	
	public EzyReflectionProxy(Set<String> packages) {
		this.reflections = new Reflections(packages);
	}
	
	public EzyReflectionProxy(Iterable<String> packages) {
		this.reflections = new Reflections(packages);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Set<Class<?>> getExtendsClasses(Class parentClass) {
		Set<Class<?>> classes = reflections.getSubTypesOf(parentClass);
		return classes;
	}

	@Override
	public Set<Class<?>> getAnnotatedClasses(Class<? extends Annotation> annClass) {
		Set<Class<?>> classes = reflections.getTypesAnnotatedWith(annClass);
		return classes;
	}

	
	
}
