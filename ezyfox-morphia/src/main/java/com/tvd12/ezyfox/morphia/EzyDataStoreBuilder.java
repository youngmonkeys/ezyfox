package com.tvd12.ezyfox.morphia;

import java.util.HashSet;
import java.util.Set;

import com.mongodb.MongoClient;
import com.tvd12.ezyfox.builder.EzyBuilder;
import com.tvd12.ezyfox.collect.Sets;
import com.tvd12.ezyfox.reflect.EzyReflection;
import com.tvd12.ezyfox.reflect.EzyReflectionProxy;
import com.tvd12.ezyfox.util.EzyLoggable;

import dev.morphia.Datastore;
import dev.morphia.Morphia;
import dev.morphia.annotations.Entity;

@SuppressWarnings("rawtypes")
public class EzyDataStoreBuilder
		extends EzyLoggable 
		implements EzyBuilder<Datastore> {

	protected String databaseName;
	protected MongoClient mongoClient;
	protected Set<Class> entityClasses;
	protected Set<String> packagesToScan;
	
	public EzyDataStoreBuilder() {
		this.entityClasses = new HashSet<>();
		this.packagesToScan = new HashSet<>();
	}
	
	public static EzyDataStoreBuilder dataStoreBuilder() {
		return new EzyDataStoreBuilder();
	}
	
	public EzyDataStoreBuilder databaseName(String databaseName) {
		this.databaseName = databaseName;
		return this;
	}
	
	public EzyDataStoreBuilder mongoClient(MongoClient mongoClient) {
		this.mongoClient = mongoClient;
		return this;
	}
	
	public EzyDataStoreBuilder scan(String packageName) {
		this.packagesToScan.add(packageName);
		return this;
	}
	
	public EzyDataStoreBuilder scan(String... packageNames) {
		for(String packageName : packageNames)
			scan(packageName);
		return this;
	}
	
	public EzyDataStoreBuilder scan(Iterable<String> packageNames) {
		for(String packageName : packageNames)
			scan(packageName);
		return this;
	}
	
	public EzyDataStoreBuilder addEntityClass(Class entityClass) {
		this.entityClasses.add(entityClass);
		return this;
	}
	
	public EzyDataStoreBuilder addEntityClasses(Class... classes) {
		return addEntityClasses(Sets.newHashSet(classes));
	}
	
	public EzyDataStoreBuilder addEntityClasses(Iterable<Class> classes) {
		classes.forEach(this::addEntityClass);
		return this;
	}
	
	@Override
	public Datastore build() {
		Datastore datastore = newDataStore();
		return datastore;
	}
	
	private Datastore newDataStore() {
		Set<Class<?>> annotatedClasses = getAnnotatedClasses();
		entityClasses.addAll(annotatedClasses);
		Morphia morphia = new Morphia(entityClasses);
		Datastore datastore = morphia.createDatastore(mongoClient, databaseName);
		return datastore;
	}
	
	private Set<Class<?>> getAnnotatedClasses() {
		if(packagesToScan.isEmpty())
			return new HashSet<>();
		EzyReflection reflection = new EzyReflectionProxy(packagesToScan);
		Set<Class<?>> classes = reflection.getAnnotatedClasses(Entity.class);
		return classes;
	}
	
}
