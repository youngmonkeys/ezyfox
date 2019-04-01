package com.tvd12.ezyfox.elasticsearch;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.tvd12.ezyfox.data.EzyIndexedDataClassesFetcher;
import com.tvd12.ezyfox.data.EzySimpleIndexedDataClassesFetcher;
import com.tvd12.ezyfox.elasticsearch.util.EzyDataIndexesAnnotations;

@SuppressWarnings("rawtypes")
public class EzySimpleIndexedDataClasses implements EzyIndexedDataClasses {
	
	protected Map<Class, EzyEsIndexTypes> map = new ConcurrentHashMap<>();
	
	protected EzySimpleIndexedDataClasses(Builder builder) {
		this.map.putAll(builder.indexedClassMap);
	}
	
	@Override
	public Set<Class> getIndexedClasses() {
		return new HashSet<>(map.keySet());
	}
	
	@Override
	public EzyEsIndexTypes getIndexTypes(Class clazz) {
		if(map.containsKey(clazz))
			return map.get(clazz);
		throw new IllegalArgumentException(clazz.getName() + " is not indexed data");
	}
	
	@Override
	public String toString() {
		return map.toString();
	}
	
	public static Builder builder() {
		return new Builder();
	}

	public static class Builder implements EzyIndexedDataClassesBuilder {
		
		protected Map<Class, EzyEsIndexTypes> indexedClassMap
				= new ConcurrentHashMap<>();
		protected EzyIndexedDataClassesFetcher indexedDataClassFetcher 
				= newIndexedDataClassesFetcher();
		
		@Override
		public Builder addIndexedDataClass(Class clazz) {
			this.indexedDataClassFetcher.addIndexedDataClass(clazz);
			return this;
		}
		
		@Override
		public Builder addIndexedDataClasses(Class... classes) {
			return addIndexedDataClasses(Arrays.asList(classes));
		}
		
		@Override
		public Builder addIndexedDataClasses(Iterable<Class> classes) {
			classes.forEach(this::addIndexedDataClass);
			return this;
		}
		
		@Override
		public EzyIndexedDataClassesBuilder addIndexedDataClasses(Object reflection) {
			this.indexedDataClassFetcher.addIndexedDataClasses(reflection);
			return this;
		}
		
		@Override
		public Builder addIndexedDataClasses(Map<Class, EzyEsIndexTypes> map) {
			this.indexedClassMap.putAll(map);
			return this;
		}
		
		public Builder addIndexedDataClass(Class clazz, EzyEsIndexTypes indexTypes) {
			this.indexedClassMap.put(clazz, indexTypes);
			return this;
		}
		
		@Override
		public EzyIndexedDataClasses build() {
			Set<Class> classes = indexedDataClassFetcher.getIndexedDataClasses();
			for(Class clazz : classes)
				addIndexedDataClass(clazz, EzyDataIndexesAnnotations.getIndexTypes(clazz));
			return new EzySimpleIndexedDataClasses(this);
		}
		
		protected EzyIndexedDataClassesFetcher newIndexedDataClassesFetcher() {
			return new EzySimpleIndexedDataClassesFetcher();
		}
		
	}
	
}
