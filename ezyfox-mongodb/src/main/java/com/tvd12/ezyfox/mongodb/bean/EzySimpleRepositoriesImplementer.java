package com.tvd12.ezyfox.mongodb.bean;

import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.tvd12.ezyfox.annotation.EzyAutoImpl;
import com.tvd12.ezyfox.collect.Sets;
import com.tvd12.ezyfox.io.EzySets;
import com.tvd12.ezyfox.mongodb.EzyMongoRepository;
import com.tvd12.ezyfox.reflect.EzyReflection;
import com.tvd12.ezyfox.reflect.EzyReflectionProxy;
import com.tvd12.ezyfox.util.EzyLoggable;

public abstract class EzySimpleRepositoriesImplementer
		extends EzyLoggable
		implements EzyRepositoriesImplementer {

	protected Set<String> packagesToScan;
	protected Set<Class<?>> autoImplInterfaces;
	
	public EzySimpleRepositoriesImplementer() {
		this.packagesToScan = new HashSet<>();
		this.autoImplInterfaces = new HashSet<>();
	}
	
	public EzyRepositoriesImplementer scan(String packageName) {
		packagesToScan.add(packageName);
		return this;
	}
	
	public EzyRepositoriesImplementer scan(String... packageNames) {
		return scan(Sets.newHashSet(packageNames));
	}
	
	public EzyRepositoriesImplementer scan(Iterable<String> packageNames) {
		for(String packageName : packageNames)
			this.scan(packageName);
		return this;	
	}
	
	@Override
	public EzyRepositoriesImplementer repositoryInterface(Class<?> itf) {
		if(!Modifier.isInterface(itf.getModifiers())) {
			logger.warn("class {} is not an interface, ignore its", itf.getSimpleName());
		}
		else if(!EzyMongoRepository.class.isAssignableFrom(itf)) {
			logger.warn("interface {} doestn't extends {}, ignore its", 
					itf.getSimpleName(), EzyMongoRepository.class.getSimpleName());
		}
		else {
			autoImplInterfaces.add(itf);
		}
		return this;
	}
	
	@Override
	public EzyRepositoriesImplementer repositoryInterfaces(Class<?>... itfs) {
		return repositoryInterfaces(Sets.newHashSet(itfs));
	}
	
	@Override
	public EzyRepositoriesImplementer repositoryInterfaces(Iterable<Class<?>> itfs) {
		for(Class<?> itf : itfs)
			this.repositoryInterface(itf);
		return this;
	}
	
	@Override
	public Map<Class<?>, Object> implement(Object template) {
		Collection<Class<?>> scannedInterfaces = getAutoImplRepoInterfaces();
		autoImplInterfaces.addAll(scannedInterfaces);
		Map<Class<?>, Object> repositories = new ConcurrentHashMap<>();
		for(Class<?> itf : autoImplInterfaces) {
			Object repo = implementRepoInterface(itf, template);
			repositories.put(itf, repo);
		}
		return repositories;
	}
	
	private Object implementRepoInterface(Class<?> itf, Object template) {
		EzySimpleRepositoryImplementer implementer = newRepoImplementer(itf);
		return implementer.implement(template);
	}
	
	protected abstract EzySimpleRepositoryImplementer newRepoImplementer(Class<?> itf);
	
	private Collection<Class<?>> getAutoImplRepoInterfaces() {
		if(packagesToScan.isEmpty())
			return new HashSet<>();
		EzyReflection reflection = new EzyReflectionProxy(packagesToScan);
		Set<Class<?>> classes = reflection.getExtendsClasses(EzyMongoRepository.class);
		return EzySets.filter(classes, clazz -> this.isAutoImplRepoInterface(clazz));
	}
	
	private boolean isAutoImplRepoInterface(Class<?> clazz) {
		return  clazz.isAnnotationPresent(EzyAutoImpl.class) &&
				Modifier.isPublic(clazz.getModifiers()) &&
				Modifier.isInterface(clazz.getModifiers());
				
	}
	
}
