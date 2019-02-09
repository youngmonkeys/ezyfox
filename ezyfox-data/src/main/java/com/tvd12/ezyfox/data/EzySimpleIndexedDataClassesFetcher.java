package com.tvd12.ezyfox.data;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.tvd12.ezyfox.data.annotation.EzyIndexedData;
import com.tvd12.ezyfox.reflect.EzyReflection;
import com.tvd12.ezyfox.reflect.EzyReflectionProxy;
import com.tvd12.ezyfox.util.EzyLoggable;

@SuppressWarnings("rawtypes")
public class EzySimpleIndexedDataClassesFetcher
		extends EzyLoggable
		implements EzyIndexedDataClassesFetcher {

	protected Set<Class> classes = new HashSet<>();
	protected Set<String> packagesToScan = new HashSet<>();
	
	public EzyIndexedDataClassesFetcher scan(String packageName) {
		packagesToScan.add(packageName);
		return this;
	}
	
	public EzyIndexedDataClassesFetcher scan(String... packageNames) {
		return scan(Arrays.asList(packageNames));
	}
	
	public EzyIndexedDataClassesFetcher scan(Iterable<String> packageNames) {
		packageNames.forEach(this::scan);
		return this;
	}
	
	@Override
	public EzyIndexedDataClassesFetcher addIndexedDataClass(Class clazz) {
		this.classes.add(clazz);
		return this;
	}
	
	@Override
	public EzyIndexedDataClassesFetcher addIndexedDataClasses(Class... classes) {
		return addIndexedDataClasses(Arrays.asList(classes));
	}
	
	@Override
	public EzyIndexedDataClassesFetcher addIndexedDataClasses(Iterable<Class> classes) {
		classes.forEach(this::addIndexedDataClass);
		return this;
	}
	
	@Override
	public Set<Class> getIndexedDataClasses() {
		Set<Class<?>> annotatedClasses = getAnnotatedClasses();
		classes.addAll(annotatedClasses);
		return classes;
	}
	
	private Set<Class<?>> getAnnotatedClasses() {
		if(packagesToScan.isEmpty())
			return new HashSet<>();
		EzyReflection reflection = new EzyReflectionProxy(packagesToScan);
		Set<Class<?>> answer = reflection.getAnnotatedClasses(EzyIndexedData.class);
		return answer;
	}
	
}
