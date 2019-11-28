package com.tvd12.ezyfox.identifier;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

import com.tvd12.ezyfox.builder.EzyBuilder;
import com.tvd12.ezyfox.collect.Sets;
import com.tvd12.ezyfox.reflect.EzyReflection;
import com.tvd12.ezyfox.reflect.EzyReflectionProxy;
import com.tvd12.ezyfox.util.EzyHasIdEntity;

@SuppressWarnings({"rawtypes", "unchecked"})
public abstract class EzyIdEncapsulationBuilder<T, B extends EzyIdEncapsulationBuilder<T, B>> implements EzyBuilder<T> {

	protected final Set<Class> entityClasses;
	protected final Set<String> packagesToScan;
	protected final Set<Class<? extends Annotation>> annotationClasses;
	
	public EzyIdEncapsulationBuilder() {
		this.packagesToScan = new HashSet<>();
		this.entityClasses = new HashSet<>();
		this.annotationClasses = getAnnotationClasses();
	}
	
	public B scan(String packageName) {
		packagesToScan.add(packageName);
		return (B)this;
	}

	public B scan(String... packageNames) {
		return scan(Sets.newHashSet(packageNames));
	}

	public B scan(Iterable<String> packageNames) {
		for(String packageName : packageNames)
			this.scan(packageName);
		return (B)this;
	}

	public B addClass(Class clazz) {
		if (isHasIdClass(clazz) || isAnnotatedClass(clazz))
			this.entityClasses.add(clazz);
		return (B)this;
	}

	public B addClasses(Class... classes) {
		return addClasses(Sets.newHashSet(classes));
	}

	public B addClasses(Iterable<Class> classes) {
		for(Class clazz : classes)
			this.addClass(clazz);
		return (B)this;
	}
	
	public B addClasses(Object reflection) {
		if(reflection instanceof EzyReflection) {
			EzyReflection ref = (EzyReflection)reflection;
			Set<Class<?>> hasIdClasses = ref.getExtendsClasses(EzyHasIdEntity.class);
			Set<Class<?>> annotatedClasses = new HashSet<>();
			annotatedClasses.addAll(ref.getAnnotatedClasses(annotationClasses));
			this.entityClasses.addAll(hasIdClasses);
			this.entityClasses.addAll(annotatedClasses);
		}
		return (B)this;
	}
	
	@Override
	public T build() {
		preBuild();
		return newProduct();
	}
	
	protected abstract T newProduct();
	
	protected void preBuild() {
		scanAllPackages();
		parseEntityClasses();
	}
	
	protected abstract void parseEntityClasses();
	
	protected void scanAllPackages() {
		if(packagesToScan.isEmpty()) 
			return;
		EzyReflection reflection = new EzyReflectionProxy(packagesToScan);
		addClasses(reflection);
	}
	
	protected boolean isHasIdClass(Class<?> clazz) {
		return EzyHasIdEntity.class.isAssignableFrom(clazz);
	}
	
	protected boolean isAnnotatedClass(Class<?> clazz) {
		for (Class<? extends Annotation> annClass : annotationClasses)
			if (clazz.isAnnotationPresent(annClass))
				return true;
		return false;
	}
	
	protected Set<Class<? extends Annotation>> getAnnotationClasses() {
		return new HashSet<>();
	}
	
}
