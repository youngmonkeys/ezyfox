package com.tvd12.ezyfox.hazelcast.mapstore;

import static com.tvd12.ezyfox.database.util.EzyMapstoreAnnotations.getMapName;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import java.util.function.Supplier;
import com.tvd12.ezyfox.collect.Sets;
import com.hazelcast.core.MapStore;
import com.tvd12.ezyfox.builder.EzyBuilder;
import com.tvd12.ezyfox.database.annotation.EzyMapstore;
import com.tvd12.ezyfox.reflect.EzyClasses;
import com.tvd12.ezyfox.reflect.EzyReflection;
import com.tvd12.ezyfox.reflect.EzyReflectionProxy;
import com.tvd12.ezyfox.util.EzyLoggable;

public class EzySimpleMapstoresFetcher
		extends EzyLoggable
		implements EzyMapstoresFetcher {
	
	protected Map<String, Object> mapstores = new ConcurrentHashMap<>();
	
	protected EzySimpleMapstoresFetcher(Builder builder) {
		this.mapstores.putAll(builder.mapstores);
	}

	@Override
	public Set<String> getMapNames() {
		return new HashSet<>(mapstores.keySet());
	}
	
	@Override
	public Object getMapstore(String mapName) {
		return mapstores.get(mapName);
	}
	
	@Override
	public Map<String, Object> getMapstores() {
		return new HashMap<>(mapstores);
	}
	
	@Override
	public boolean containsMapstore(String mapName) {
		return mapstores.containsKey(mapName);
	}
	
	public static Builder builder() {
		return new Builder();
	}
	
	@SuppressWarnings({"rawtypes"})
	public static class Builder
			extends EzyLoggable
			implements EzyBuilder<EzyMapstoresFetcher> {

		protected Set<String> packagesToScan = new HashSet<>();
		protected Map<String, Object> mapstores = new HashMap<>();
		protected Map<String, Class<?>> mapstoreClassMap = new HashMap<>();
		
		public Builder scan(String packageName) {
			this.packagesToScan.add(packageName);
			return this;
		}
		
		public Builder scan(String... packageNames) {
			return scan(Sets.newHashSet(packageNames));
		}
		
		public Builder scan(Iterable<String> packageNames) {
			packageNames.forEach(this::scan);
			return this;	
		}
		
		public Builder addMapstoreClass(Class clazz) {
			return addMapstoreClass(() -> getMapName(clazz), clazz);
		}
		
		public Builder addMapstoreClass(String mapName, Class clazz) {
			return addMapstoreClass(() -> mapName, clazz);
		}
		
		public Builder addMapstore(MapStore mapstore) {
			return addMapstore(getMapName(mapstore.getClass()), mapstore);
		}
		
		public Builder addMapstore(String mapName, MapStore mapstore) {
			this.mapstores.put(mapName, mapstore);
			logger.debug("add mapstore {} of class {}", mapName, mapstore.getClass().getName());
			return this;
		}
		
		@Override
		public EzySimpleMapstoresFetcher build() {
			Set<Class<?>> annotatedClasses = scanAnnotatedClasses();
			for(Class<?> clazz : annotatedClasses)
				addMapstoreClass(clazz);
			for(String mapName : mapstoreClassMap.keySet()) {
				Class clazz = mapstoreClassMap.get(mapName);
				Object mapstore = newMapstore(clazz);
				addMapstore(mapName, (MapStore) mapstore);
			}
			return new EzySimpleMapstoresFetcher(this);
		}
		
		private Set<Class<?>> scanAnnotatedClasses() {
			if(packagesToScan.isEmpty())
				return new HashSet<>();
			EzyReflection reflection = new EzyReflectionProxy(packagesToScan);
			Set<Class<?>> annotatedClasses = reflection.getAnnotatedClasses(EzyMapstore.class);
			return annotatedClasses;
		}
		
		protected Object newMapstore(Class<?> mapstoreClass) {
			return EzyClasses.newInstance(mapstoreClass);
		}
		
		private Builder addMapstoreClass(Supplier<String> mapNameSupplier, Class clazz) {
			if(MapStore.class.isAssignableFrom(clazz))
				this.mapstoreClassMap.put(mapNameSupplier.get(), clazz);
			else
				logger.warn("{} doesn't implements {}, ignore it", clazz, MapStore.class);
			return this;
		}
		
	}
	
}
