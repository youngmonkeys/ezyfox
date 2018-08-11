package com.tvd12.ezyfox.elasticsearch;

import java.util.Set;

@SuppressWarnings("rawtypes")
public interface EzyIndexedDataClasses {
	
	static EzyIndexedDataClassesBuilder builder() {
		return EzySimpleIndexedDataClasses.builder();
	}
	
	Set<Class> getIndexedClasses();
	
	EzyIndexTypes getIndexTypes(Class clazz);
	
	
}
