package com.tvd12.ezyfox.bean;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

@SuppressWarnings("rawtypes")
public interface EzySingletonFactory {

	Object getSingleton(Class type);
	
	Object getSingleton(String name, Class type);
	
	Object getAnnotatedSingleton(Class annotationClass);
	
	Object getSingleton(Map properties);
	
	List getSingletons();
	
	List getSingletons(Map properties);
	
	List getSingletons(Class annotationClass);

	List getSingletons(Predicate filter);
	
	List getSingletonsOf(Class parentClass);
	
	Map getProperties(Object singleton);
	
	Object addSingleton(Object singleton);
	
	Object addSingleton(String name, Object singleton);
	
	Object addSingleton(String name, Object singleton, Map properties);
	
	void addSingletons(Map<String, Object> singletons);
	
	Set<Class> getSingletonClasses();
	
}
