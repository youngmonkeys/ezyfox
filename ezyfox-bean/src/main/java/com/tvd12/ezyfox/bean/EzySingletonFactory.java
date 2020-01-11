package com.tvd12.ezyfox.bean;

import java.util.List;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("rawtypes")
public interface EzySingletonFactory {

	Object getSingleton(Class type);
	
	Object getSingleton(String name, Class type);
	
	Object getAnnotatedSingleton(Class annotationClass);
	
	Object getSingleton(Map properties);
	
	List getSingletons(Map properties);
	
	List getSingletons(Class annotationClass);
	
	Map getProperties(Object singleton);
	
	Object addSingleton(Object singleton);
	
	Object addSingleton(String name, Object singleton);
	
	Object addSingleton(String name, Object singleton, Map properties);
	
	Set<Class> getSingletonClasses();
	
}
