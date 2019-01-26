package com.tvd12.ezyfox.binding.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

import com.tvd12.ezyfox.binding.EzyMarshaller;
import com.tvd12.ezyfox.binding.EzyWriter;
import com.tvd12.ezyfox.binding.writer.EzyDefaultWriter;
import com.tvd12.ezyfox.binding.writer.EzyIterableWriter;
import com.tvd12.ezyfox.binding.writer.EzyMapWriter;
import com.tvd12.ezyfox.builder.EzyArrayBuilder;
import com.tvd12.ezyfox.entity.EzyArray;
import com.tvd12.ezyfox.entity.EzyObject;
import com.tvd12.ezyfox.io.EzyMaps;
import com.tvd12.ezyfox.reflect.EzyTypes;
import com.tvd12.ezyfox.util.EzyEntityBuilders;

public class EzySimpleMarshaller
		extends EzyEntityBuilders
		implements EzyMarshaller {

	@SuppressWarnings("rawtypes")
	protected final Map<Class, EzyWriter> writersByType;
	@SuppressWarnings("rawtypes")
	protected final Map<Class, EzyWriter> writersByObjectType;
	
	public EzySimpleMarshaller() {
		this.writersByObjectType = defaultWriters();
		this.writersByType = defaultWritersByType();
	}
	
	@SuppressWarnings("rawtypes")
	public void addWriter(EzyWriter writer) {
		writersByType.put(writer.getClass(), writer);
	}
	
	@SuppressWarnings("rawtypes")
	public void addWriters(Iterable<EzyWriter> writers) {
		writers.forEach(this::addWriter);
	}
	
	@SuppressWarnings("rawtypes")
	public void addWriter(Class type, EzyWriter writer) {
		writersByObjectType.put(type, writer);
		writersByType.put(writer.getClass(), writer);
	}
	
	@SuppressWarnings("rawtypes")
	public void addWriters(Map<Class, EzyWriter> writers) {
		writers.keySet().forEach(key -> addWriter(key, writers.get(key)));
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public <T> T marshal(Object object) {
		if(object == null)
			return null;
		Class objectType = object.getClass();
		EzyWriter writer = EzyMaps.getValue(writersByObjectType, objectType);
		if(writer != null)
			return (T) writer.write(this, object);
		if(objectType.isEnum())
			return (T) object.toString();
		if(objectType.isArray())
			return (T) writeArray((Object[])object);
		throw new IllegalArgumentException("has no writer for " + object.getClass());
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public <T> T marshal(Class<? extends EzyWriter> writerClass, Object object) {
		if(writersByType.containsKey(writerClass))
			return (T) writersByType.get(writerClass).write(this, object);
		throw new IllegalArgumentException("can't marshal object " + 
			object + ", " + writerClass.getName() + " is not writer class");
	}
	
	@SuppressWarnings("rawtypes")
	private Map<Class, EzyWriter> defaultWritersByType() {
		Map<Class, EzyWriter> map = new ConcurrentHashMap<>();
		writersByObjectType.values().forEach(w -> map.put(w.getClass(), w));
		return map;
	}
	
	@SuppressWarnings("rawtypes")
	private Map<Class, EzyWriter> defaultWriters() {
		Map<Class, EzyWriter> map = new ConcurrentHashMap<>();
		Set<Class> normalTypes = EzyTypes.ALL_TYPES;
		for(Class normalType : normalTypes)
			map.put(normalType, EzyDefaultWriter.getInstance());
		map.put(Date.class, EzyDefaultWriter.getInstance());
		map.put(Class.class, EzyDefaultWriter.getInstance());
		map.put(LocalDate.class, EzyDefaultWriter.getInstance());
		map.put(LocalDateTime.class, EzyDefaultWriter.getInstance());
		map.put(EzyArray.class, EzyDefaultWriter.getInstance());
		map.put(EzyObject.class, EzyDefaultWriter.getInstance());
		map.put(Map.class, EzyMapWriter.getInstance());
		map.put(HashMap.class, EzyMapWriter.getInstance());
		map.put(TreeMap.class, EzyMapWriter.getInstance());
		map.put(ConcurrentHashMap.class, EzyMapWriter.getInstance());
		map.put(List.class, EzyIterableWriter.getInstance());
		map.put(Set.class, EzyIterableWriter.getInstance());
		map.put(ArrayList.class, EzyIterableWriter.getInstance());
		map.put(HashSet.class, EzyIterableWriter.getInstance());
		map.put(Collection.class, EzyIterableWriter.getInstance());
		map.put(Iterable.class, EzyIterableWriter.getInstance());
		return map;
	}
	
	private EzyArray writeArray(Object[] array) {
		EzyArrayBuilder builder = newArrayBuilder();
		Arrays.stream(array).forEach(o -> builder.append((Object)marshal(o)));
		return builder.build();
	}

}
