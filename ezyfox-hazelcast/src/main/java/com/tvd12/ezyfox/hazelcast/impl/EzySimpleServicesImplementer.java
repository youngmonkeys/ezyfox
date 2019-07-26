package com.tvd12.ezyfox.hazelcast.impl;

import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.hazelcast.core.HazelcastInstance;
import com.tvd12.ezyfox.annotation.EzyAutoImpl;
import com.tvd12.ezyfox.collect.Sets;
import com.tvd12.ezyfox.hazelcast.annotation.EzyMapServiceAutoImpl;
import com.tvd12.ezyfox.hazelcast.bean.EzyServicesImplementer;
import com.tvd12.ezyfox.hazelcast.service.EzyHazelcastMapService;
import com.tvd12.ezyfox.io.EzySets;
import com.tvd12.ezyfox.reflect.EzyClass;
import com.tvd12.ezyfox.reflect.EzyReflection;
import com.tvd12.ezyfox.reflect.EzyReflectionProxy;
import com.tvd12.ezyfox.util.EzyLoggable;

public class EzySimpleServicesImplementer
		extends EzyLoggable
		implements EzyServicesImplementer {

	protected Set<String> packagesToScan = new HashSet<>();
	protected Set<Class<?>> autoImplInterfaces = new HashSet<>();
	protected Map<String, Class<?>> autoImplInterfaceMap = new HashMap<>();
	
	public EzyServicesImplementer scan(String packageName) {
		packagesToScan.add(packageName);
		return this;
	}
	
	public EzyServicesImplementer scan(String... packageNames) {
		return scan(Sets.newHashSet(packageNames));
	}
	
	public EzyServicesImplementer scan(Iterable<String> packageNames) {
		for(String packageName : packageNames)
			this.scan(packageName);
		return this;	
	}
	
	@Override
	public EzyServicesImplementer serviceInterface(String mapName, Class<?> itf) {
		autoImplInterfaceMap.put(mapName, itf);
		return this;
	}
	
	@Override
	public Map<Class<?>, Object> implement(HazelcastInstance hzInstance) {
		Collection<Class<?>> scannedInterfaces = getAutoImplServiceInterfaces();
		autoImplInterfaces.addAll(scannedInterfaces);
		Map<Class<?>, Object> repositories = new ConcurrentHashMap<>();
		for(Class<?> itf : autoImplInterfaces) {
			Object repo = newServiceImplementer(itf).implement(hzInstance);
			repositories.put(itf, repo);
		}
		for(String mapName : autoImplInterfaceMap.keySet()) {
			Class<?> itf = autoImplInterfaceMap.get(mapName);
			Object repo = newServiceImplementer(itf).implement(hzInstance, mapName);
			repositories.put(itf, repo);
		}
		return repositories;
	}
	
	private EzySimpleServiceImplementer newServiceImplementer(Class<?> itf) {
		return new EzySimpleServiceImplementer(new EzyClass(itf));
	}
	
	private Collection<Class<?>> getAutoImplServiceInterfaces() {
		if(packagesToScan.isEmpty())
			return new HashSet<>();
		EzyReflection reflection = new EzyReflectionProxy(packagesToScan);
		Set<Class<?>> classes = reflection.getExtendsClasses(EzyHazelcastMapService.class);
		return EzySets.filter(classes, clazz -> this.isAutoImplServiceInterface(clazz));
	}
	
	private boolean isAutoImplServiceInterface(Class<?> clazz) {
		return  (clazz.isAnnotationPresent(EzyAutoImpl.class) || 
				 clazz.isAnnotationPresent(EzyMapServiceAutoImpl.class)) &&
				Modifier.isPublic(clazz.getModifiers()) &&
				Modifier.isInterface(clazz.getModifiers());
				
	}
	
}
