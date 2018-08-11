package com.tvd12.ezyfox.data;

import java.util.Set;

@SuppressWarnings("rawtypes")
public interface EzyIndexedDataClassesFetcher {
	
	static EzyIndexedDataClassesFetcher newInstance() {
		return new EzySimpleIndexedDataClassesFetcher(); 
	}

	EzyIndexedDataClassesFetcher scan(String packageName);
	
	EzyIndexedDataClassesFetcher scan(String... packageNames);

	EzyIndexedDataClassesFetcher scan(Iterable<String> packageNames);
	
	EzyIndexedDataClassesFetcher addIndexedDataClass(Class clazz);
	
	EzyIndexedDataClassesFetcher addIndexedDataClasses(Class... classes);
	
	EzyIndexedDataClassesFetcher addIndexedDataClasses(Iterable<Class> classes);
	
	Set<Class> getIndexedDataClasses();
	
}
