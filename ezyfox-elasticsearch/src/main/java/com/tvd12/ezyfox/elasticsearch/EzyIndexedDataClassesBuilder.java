package com.tvd12.ezyfox.elasticsearch;

import java.util.Map;
import java.util.Set;

import com.tvd12.ezyfox.builder.EzyBuilder;

@SuppressWarnings("rawtypes")
public interface EzyIndexedDataClassesBuilder 
		extends EzyBuilder<EzyIndexedDataClasses> {

	EzyIndexedDataClassesBuilder addIndexedDataClass(Class clazz);
	
	EzyIndexedDataClassesBuilder addIndexedDataClasses(Class... classes);
	
	EzyIndexedDataClassesBuilder addIndexedDataClasses(Iterable<Class> classes);
	
	EzyIndexedDataClassesBuilder addIndexedDataClasses(Object reflection);
	
	EzyIndexedDataClassesBuilder addIndexedDataClasses(Map<Class, Set<String>> map);
	
	EzyIndexedDataClassesBuilder addIndexedDataClass(Class clazz, Set<String> indexes);
	
}
