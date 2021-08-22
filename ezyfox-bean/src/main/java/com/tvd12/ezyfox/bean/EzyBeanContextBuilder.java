package com.tvd12.ezyfox.bean;

import java.io.File;
import java.io.InputStream;
import java.util.Collection;
import java.util.Map;
import java.util.Properties;

import com.tvd12.ezyfox.builder.EzyBuilder;
import com.tvd12.ezyfox.properties.EzyPropertiesReader;

@SuppressWarnings("rawtypes")
public interface EzyBeanContextBuilder extends EzyBuilder<EzyBeanContext> {
	
	Properties getProperties();
	
	EzyBeanContextBuilder disableAutoConfiguration();

	EzyBeanContextBuilder scan(String packageName);

	EzyBeanContextBuilder scan(String... packageNames);

	EzyBeanContextBuilder scan(Iterable<String> packageNames);
	
	EzyBeanContextBuilder scan(Collection<String> packageNames);
	
	EzyBeanContextBuilder addConfigurationClass(Class clazz);
	
	EzyBeanContextBuilder addConfigurationClasses(Class... classes);
	
	EzyBeanContextBuilder addConfigurationClasses(Iterable<Class> classes);
	
	EzyBeanContextBuilder addConfigurationBeforeClass(Class clazz);
	
	EzyBeanContextBuilder addConfigurationBeforeClasses(Class... classes);
	
	EzyBeanContextBuilder addConfigurationBeforeClasses(Iterable<Class> classes);
	
	EzyBeanContextBuilder addConfigurationAfterClass(Class clazz);
	
	EzyBeanContextBuilder addConfigurationAfterClasses(Class... classes);
	
	EzyBeanContextBuilder addConfigurationAfterClasses(Iterable<Class> classes);

	EzyBeanContextBuilder addSingleton(String name, Object singleton);
	
	EzyBeanContextBuilder addSingletons(Map<String, Object> singletons);

	EzyBeanContextBuilder addSingletonClass(Class clazz);

	EzyBeanContextBuilder addSingletonClasses(Class... classes);

	EzyBeanContextBuilder addSingletonClasses(Iterable<Class> classes);
	
	EzyBeanContextBuilder addSingletonClass(String name, Class clazz);
	
	EzyBeanContextBuilder addSingletonClasses(Map<String, Class> classes);

	EzyBeanContextBuilder addPrototypeClass(Class clazz);

	EzyBeanContextBuilder addPrototypeClasses(Class... classes);

	EzyBeanContextBuilder addPrototypeClasses(Iterable<Class> classes);
	
	EzyBeanContextBuilder addPrototypeClass(String name, Class clazz);
	
	EzyBeanContextBuilder addPrototypeClasses(Map<String, Class> classes);
	
	EzyBeanContextBuilder addPrototypeSupplier(String objectName, EzyPrototypeSupplier supplier);
	
	EzyBeanContextBuilder addPrototypeSuppliers(Map<String, EzyPrototypeSupplier> suppliers);
	
	EzyBeanContextBuilder addAllClasses(Object reflection);
	
	EzyBeanContextBuilder errorHandler(EzyErrorHandler handler);
	
	EzyBeanContextBuilder addProperties(Map properties);
	
	EzyBeanContextBuilder addProperties(String file);
	
	EzyBeanContextBuilder addProperties(String file, String activeProfiles);
	
	EzyBeanContextBuilder addProperties(File file);
	
	EzyBeanContextBuilder addProperties(File file, String activeProfiles);
	
	EzyBeanContextBuilder addProperties(InputStream inputStream);
	
	EzyBeanContextBuilder addProperty(String key, Object value);
	
	EzyBeanContextBuilder propertiesMap(EzyPropertiesMap propertiesMap);
	
	EzyBeanContextBuilder propertiesReader(EzyPropertiesReader propertiesReader);
	
	EzyBeanContextBuilder propertiesBeanClass(String prefix, Class propertiesBeanClass);
	
}