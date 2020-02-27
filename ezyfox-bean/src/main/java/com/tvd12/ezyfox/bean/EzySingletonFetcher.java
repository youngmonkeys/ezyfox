package com.tvd12.ezyfox.bean;

import java.util.List;
import java.util.Map;

@SuppressWarnings("rawtypes")
public interface EzySingletonFetcher {

	<T> T getSingleton(Class<T> type);
	
	<T> T getSingleton(String name, Class<T> type);
	
	<T> T getSingleton(Map properties);
	
	List getSingletons();
	
	List getSingletons(Map properties);
	
	List getSingletons(Class annotationClass);
	
	<T> T getAnnotatedSingleton(Class annotationClass);
	
}
