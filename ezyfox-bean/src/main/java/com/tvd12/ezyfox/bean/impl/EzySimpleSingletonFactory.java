package com.tvd12.ezyfox.bean.impl;

import static com.tvd12.ezyfox.bean.impl.EzyBeanKey.of;
import static com.tvd12.ezyfox.reflect.EzyClasses.flatSuperAndInterfaceClasses;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.tvd12.ezyfox.bean.EzySingletonFactory;
import com.tvd12.ezyfox.io.EzyMaps;

import lombok.Getter;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class EzySimpleSingletonFactory
		extends EzySimpleBeanFactory
		implements EzySingletonFactory {

	@Getter
	protected final Set<Class> singletonClasses
			= new HashSet<>();
	protected final Map<EzyBeanKey, Object> objectsByKey
			= new ConcurrentHashMap<>();
	protected final Map<Object, Map> objectsByProperties
			= new ConcurrentHashMap<>();
	
	@Override
	public Object addSingleton(Object singleton) {
		Class type = singleton.getClass();
		return addSingleton(getBeanName(type), singleton);
	}
	
	@Override
	public Object addSingleton(String name, Object singleton) {
		Class<?> type = singleton.getClass();
		return addSingleton(name, singleton, getProperties(type));
	}
	
	@Override
	public Object addSingleton(String name, Object singleton, Map properties) {
		Class<?> type = singleton.getClass();
		EzyBeanKey key = of(name, type);
		
		if(objectsByKey.containsKey(key))
			return objectsByKey.get(key);
		
		objectsByKey.put(key, singleton);
		objectsByProperties.put(singleton, properties);
		
		String defname = getDefaultBeanName(type);
		mapBeanName(defname, type, name);
		
		Set<Class> subTypes = flatSuperAndInterfaceClasses(type, true);
		for(Class<?> subType : subTypes)
			checkAndAddSingleton(name, subType, singleton);
		return singleton;
	}
	
	private void checkAndAddSingleton(String name, Class<?> type, Object singleton) {
		EzyBeanKey key = of(name, type);
		if(objectsByKey.containsKey(key))
			return;
		objectsByKey.put(key, singleton);
	}
	
	@Override
	public Object getSingleton(String name, Class type) {
		String realname = translateBeanName(name, type);
		Object singleton = objectsByKey.get(of(realname, type));
		if(singleton == null) {
			for(EzyBeanKey key : objectsByKey.keySet()) {
				if(type.isAssignableFrom(key.getType())) {
					singleton = objectsByKey.get(key);
					break;
				}		
			}
		}
		return singleton;
	}
	
	@Override
	public Object getSingleton(Map properties) {
		for(Entry<Object, Map> entry : objectsByProperties.entrySet())
			if(EzyMaps.containsAll(entry.getValue(), properties))
				return entry.getKey();
		return null;
	}
	
	@Override
	public List getSingletons(Map properties) {
		List list = new ArrayList<>();
		for(Entry<Object, Map> entry : objectsByProperties.entrySet())
			if(EzyMaps.containsAll(entry.getValue(), properties))
				list.add(entry.getKey());
		return list;
	}
	
	@Override
	public List getSingletons(Class annoClass) {
		List list = new ArrayList<>();
		for(EzyBeanKey key : objectsByKey.keySet()) {
			Class type = key.getType();
			if(type.isAnnotationPresent(annoClass))
				list.add(objectsByKey.get(key));
		}
		return list;
	}
	
	@Override
	public Map getProperties(Object singleton) {
		return objectsByProperties.get(singleton);
	}
	
	public void addSingletonClasses(Set<Class> classes) {
		this.singletonClasses.addAll(classes);
	}
	
	private String getBeanName(Class<?> type) {
		return EzyBeanNameParser.getSingletonName(type);
	}
	
	private Map getProperties(Class<?> type) {
		return EzyKeyValueParser.getSingletonProperties(type);
	}
	
}
