package com.tvd12.ezyfox.bean.impl;

import static com.tvd12.ezyfox.bean.impl.EzyBeanNameParser.getPrototypeName;
import static com.tvd12.ezyfox.bean.impl.EzyBeanNameParser.getSingletonName;
import static com.tvd12.properties.file.util.PropertiesUtil.setVariableValues;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.function.Predicate;

import com.tvd12.ezyfox.annotation.EzyImport;
import com.tvd12.ezyfox.annotation.EzyPackagesToScan;
import com.tvd12.ezyfox.annotation.EzyProperty;
import com.tvd12.ezyfox.bean.EzyBeanAutoConfig;
import com.tvd12.ezyfox.bean.EzyBeanContext;
import com.tvd12.ezyfox.bean.EzyBeanContextBuilder;
import com.tvd12.ezyfox.bean.EzyBeanNameTranslator;
import com.tvd12.ezyfox.bean.EzyErrorHandler;
import com.tvd12.ezyfox.bean.EzyPropertiesMap;
import com.tvd12.ezyfox.bean.EzyPrototypeFactory;
import com.tvd12.ezyfox.bean.EzyPrototypeSupplier;
import com.tvd12.ezyfox.bean.EzySingletonFactory;
import com.tvd12.ezyfox.bean.annotation.EzyBeanPackagesToScan;
import com.tvd12.ezyfox.bean.annotation.EzyConfiguration;
import com.tvd12.ezyfox.bean.annotation.EzyConfigurationAfter;
import com.tvd12.ezyfox.bean.annotation.EzyConfigurationBefore;
import com.tvd12.ezyfox.bean.annotation.EzyDisableAutoConfiguration;
import com.tvd12.ezyfox.bean.annotation.EzyExclusiveClassesConfiguration;
import com.tvd12.ezyfox.bean.annotation.EzyPropertiesBean;
import com.tvd12.ezyfox.bean.annotation.EzyPropertiesBeans;
import com.tvd12.ezyfox.bean.annotation.EzyPropertiesSources;
import com.tvd12.ezyfox.bean.annotation.EzyPrototype;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyfox.bean.exception.EzyNewSingletonException;
import com.tvd12.ezyfox.bean.exception.EzySingletonException;
import com.tvd12.ezyfox.bean.supplier.EzyArrayListSupplier;
import com.tvd12.ezyfox.bean.supplier.EzyCollectionSupplier;
import com.tvd12.ezyfox.bean.supplier.EzyConcurrentHashMapSupplier;
import com.tvd12.ezyfox.bean.supplier.EzyCopyOnWriteArrayListSupplier;
import com.tvd12.ezyfox.bean.supplier.EzyCopyOnWriteArraySetSupplier;
import com.tvd12.ezyfox.bean.supplier.EzyHashMapSupplier;
import com.tvd12.ezyfox.bean.supplier.EzyHashSetSupplier;
import com.tvd12.ezyfox.bean.supplier.EzyLinkedListSupplier;
import com.tvd12.ezyfox.bean.supplier.EzyListSupplier;
import com.tvd12.ezyfox.bean.supplier.EzyMapSupplier;
import com.tvd12.ezyfox.bean.supplier.EzyQueueSupplier;
import com.tvd12.ezyfox.bean.supplier.EzySetSupplier;
import com.tvd12.ezyfox.bean.supplier.EzyStackSupplier;
import com.tvd12.ezyfox.bean.supplier.EzyTreeMapSupplier;
import com.tvd12.ezyfox.collect.Sets;
import com.tvd12.ezyfox.io.EzySimpleValueConverter;
import com.tvd12.ezyfox.io.EzyStrings;
import com.tvd12.ezyfox.properties.EzyPropertiesReader;
import com.tvd12.ezyfox.properties.EzySimplePropertiesReader;
import com.tvd12.ezyfox.reflect.EzyClass;
import com.tvd12.ezyfox.reflect.EzyImportReflection;
import com.tvd12.ezyfox.reflect.EzyPackages;
import com.tvd12.ezyfox.reflect.EzyReflection;
import com.tvd12.ezyfox.util.EzyHashMapSet;
import com.tvd12.ezyfox.util.EzyLoggable;
import com.tvd12.ezyfox.util.EzyMapSet;
import com.tvd12.properties.file.annotation.PropertyAnnotation;
import com.tvd12.properties.file.io.ValueConverter;
import com.tvd12.properties.file.mapping.PropertiesMapper;
import com.tvd12.properties.file.reader.BaseFileReader;
import com.tvd12.properties.file.reader.FileReader;
import com.tvd12.properties.file.reader.MultiFileReader;

import lombok.Getter;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class EzySimpleBeanContext
        extends EzyLoggable
        implements EzyBeanContext {

    @Getter
    protected Properties properties;
    @Getter
    protected Set<String> packagesToScan;
    @Getter
    protected EzySingletonFactory singletonFactory;
    @Getter
    protected EzyPrototypeFactory prototypeFactory;
    @Getter
    protected EzyBeanNameTranslator beanNameTranslator;

    protected EzyPropertiesReader propertiesReader;

    @Override
    public Object getBean(String name, Class type) {
        Object object = getSingleton(name, type);
        if(object != null)
            return object;
        return getPrototype(name, type);
    }

    @Override
    public Object getBean(Class<?> type) {
        Object object = getSingleton(type);
        if(object != null)
            return object;
        return getPrototype(type);
    }

    @Override
    public Object getAnnotatedBean(Class<?> annotationClass) {
        Object object = getAnnotatedSingleton(annotationClass);
        if(object != null)
            return object;
        return getAnnotatedPrototype(annotationClass);
    }

    @Override
    public <T> T getSingleton(Class<T> type) {
        return (T) singletonFactory.getSingleton(type);
    }

    @Override
    public <T> T getSingleton(String name, Class<T> type) {
        return (T) singletonFactory.getSingleton(name, type);
    }

    @Override
    public <T> T getAnnotatedSingleton(Class annotationClass) {
        return (T) singletonFactory.getAnnotatedSingleton(annotationClass);
    }

    @Override
    public <T> T getSingleton(Map properties) {
        return (T) singletonFactory.getSingleton(properties);
    }

    @Override
    public List getSingletons() {
        return singletonFactory.getSingletons();
    }

    @Override
    public List getSingletons(Map properties) {
        return singletonFactory.getSingletons(properties);
    }

    @Override
    public List getSingletons(Class annotationClass) {
        return singletonFactory.getSingletons(annotationClass);
    }

    @Override
    public List getSingletons(Predicate filter) {
        return singletonFactory.getSingletons(filter);
    }

    @Override
    public List getSingletonsOf(Class parentClass) {
        return singletonFactory.getSingletonsOf(parentClass);
    }

    @Override
    public Map<EzyBeanKey, Object> getSingletonMapByKey() {
        return singletonFactory.getSingletonMapByKey();
    }

    @Override
    public <T> T getPrototype(Class<T> type) {
        EzyPrototypeSupplier supplier = prototypeFactory.getSupplier(type);
        if(supplier == null)
            throw new IllegalArgumentException("has no bean with type " + type.getName());
        return (T) supplier.supply(this);
    }

    @Override
    public <T> T getPrototype(String name, Class<T> type) {
        EzyPrototypeSupplier supplier = prototypeFactory.getSupplier(name, type);
        if(supplier == null)
            throw new IllegalArgumentException("has no bean with name = " + name + ", and type " + type.getName());
        return (T) supplier.supply(this);
    }

    @Override
    public <T> T getAnnotatedPrototype(Class annotationClass) {
        EzyPrototypeSupplier supplier = prototypeFactory.getAnnotatedSupplier(annotationClass);
        if(supplier == null)
            throw new IllegalArgumentException("can't create a bean, has no class annotated with: " + annotationClass.getName());
        return (T) supplier.supply(this);
    }

    @Override
    public <T> T getPrototype(Map properties) {
        EzyPrototypeSupplier supplier = getPrototypeSupplier(properties);
        return supplier != null ? (T)supplier.supply(this) : null;
    }

    @Override
    public List getPrototypes(Map properties) {
        List list = new ArrayList<>();
        List<EzyPrototypeSupplier> suppliers = getPrototypeSuppliers(properties);
        for(EzyPrototypeSupplier supplier : suppliers)
            list.add(supplier.supply(this));
        return list;
    }

    @Override
    public EzyPrototypeSupplier getPrototypeSupplier(Map properties) {
        return prototypeFactory.getSupplier(properties);
    }

    @Override
    public List<EzyPrototypeSupplier> getPrototypeSuppliers(Map properties) {
        return prototypeFactory.getSuppliers(properties);
    }

    @Override
    public List<EzyPrototypeSupplier> getPrototypeSuppliers(Class annotationClass) {
        return prototypeFactory.getSuppliers(annotationClass);
    }

    @Override
    public boolean containsProperty(Object key) {
        return properties.containsKey(key);
    }

    @Override
    public <T> T getProperty(Object key, Class<T> outType) {
        return propertiesReader.get(properties, key, outType);
    }

    public static EzyBeanContextBuilder builder() {
        return new Builder();
    }

    public static class Builder extends EzyLoggable implements EzyBeanContextBuilder {
        @Getter
        protected Properties properties;
        protected Set<String> packagesToScan;
        protected Set<String> packagesToExclude;
        protected Set<String> activeProfileSet;
        protected Set<Class> excludeConfigurationClasses;
        protected Set<Class> importClasses;
        protected Set<Class> singletonClasses;
        protected Set<Class> prototypeClasses;
        protected Set<Class> packagesScanClasses;
        protected Set<Class> propertiesSourceClasses;
        protected Set<Class> configurationClasses;
        protected Set<Class> configurationBeforeClasses;
        protected Set<Class> configurationAfterClasses;
        protected Set<Class> propertiesBeanAnnotatedClasses;
        protected Set<Class> propertiesBeansAnnotatedClasses;
        protected Map<Class, String> namedSingletonClasses;
        protected Map<Class, String> namedPrototypeClasses;
        protected Map<String, Set<Class<?>>> propertiesBeanClasses;
        protected boolean enableAutoConfiguration;
        protected EzyPropertiesMap propertiesMap;
        protected EzyPropertiesReader propertiesReader;
        protected EzySimpleSingletonFactory singletonFactory;
        protected EzySimplePrototypeFactory prototypeFactory;
        protected EzyBeanNameTranslator beanNameTranslator;

        protected EzyErrorHandler errorHandler;
        protected EzyMapSet<EzyBeanKey, Class<?>> unloadedSingletons;

        protected static final String AUTO_CONFIGURATION_PACKAGE = "com.tvd12.ezyfox.boot";

        public Builder() {
            this.enableAutoConfiguration = true;
            this.properties = new Properties();
            this.properties.putAll(System.getProperties());
            this.packagesToScan = new HashSet<>();
            this.packagesToExclude = new HashSet<>();
            this.activeProfileSet = new HashSet<>();
            this.importClasses = new HashSet<>();
            this.singletonClasses = new HashSet<>();
            this.prototypeClasses = new HashSet<>();
            this.packagesScanClasses = new HashSet<>();
            this.propertiesSourceClasses = new HashSet<>();
            this.configurationClasses = new HashSet<>();
            this.configurationBeforeClasses = new HashSet<>();
            this.configurationAfterClasses = new HashSet<>();
            this.propertiesBeanAnnotatedClasses = new HashSet<>();
            this.propertiesBeansAnnotatedClasses = new HashSet<>();
            this.propertiesBeanClasses = new HashMap<>();
            this.namedSingletonClasses = new HashMap<>();
            this.namedPrototypeClasses = new HashMap<>();
            this.excludeConfigurationClasses = new HashSet<>();
            this.errorHandler = new EzySimpleErrorHandler();
            this.unloadedSingletons = new EzyHashMapSet<>();
            this.beanNameTranslator = new EzySimpleBeanNameTranslator();
            this.singletonFactory = newBeanFactory(new EzySimpleSingletonFactory());
            this.prototypeFactory = newBeanFactory(new EzySimplePrototypeFactory());
        }

        protected <T extends EzySimpleBeanFactory> T newBeanFactory(T factory) {
            factory.setBeanNameTranslator(beanNameTranslator);
            return factory;
        }

        /* (non-Javadoc)
         * @see com.tvd12.ezyfox.bean.impl.EzyBeanContextBuilder#disableAutoConfiguration()
         */
        @Override
        public EzyBeanContextBuilder disableAutoConfiguration() {
            this.enableAutoConfiguration = false;
            return this;
        }

        /* (non-Javadoc)
         * @see com.tvd12.ezyfox.bean.impl.EzyBeanContextBuilder#scan(java.lang.String)
         */
        @Override
        public EzyBeanContextBuilder scan(String packageName) {
            if(packageName != null)
                packagesToScan.add(packageName);
            return this;
        }

        /* (non-Javadoc)
         * @see com.tvd12.ezyfox.bean.impl.EzyBeanContextBuilder#scan(java.lang.String)
         */
        @Override
        public EzyBeanContextBuilder scan(String... packageNames) {
            return scan(Sets.newHashSet(packageNames));
        }

        /* (non-Javadoc)
         * @see com.tvd12.ezyfox.bean.impl.EzyBeanContextBuilder#scan(java.lang.Iterable)
         */
        @Override
        public EzyBeanContextBuilder scan(Iterable<String> packageNames) {
            return scan(Sets.newHashSet(packageNames));
        }

        /* (non-Javadoc)
         * @see com.tvd12.ezyfox.bean.impl.EzyBeanContextBuilder#scan(java.util.Collection)
         */
        @Override
        public EzyBeanContextBuilder scan(Collection<String> packageNames) {
            packagesToScan.addAll(packageNames);
            return this;
        }

        /* (non-Javadoc)
         * @see com.tvd12.ezyfox.bean.impl.EzyBeanContextBuilder#activeProfiles(java.lang.String)
         */
        @Override
        public EzyBeanContextBuilder activeProfiles(String activeProfiles) {
            if(activeProfiles != null) {
                String[] strs = activeProfiles.split(",");
                for(String str : strs) {
                    this.activeProfileSet.add(str.trim());
                }
            }
            return this;
        }

        /* (non-Javadoc)
         * @see com.tvd12.ezyfox.bean.impl.EzyBeanContextBuilder#excludePackage(java.lang.String)
         */
        @Override
        public EzyBeanContextBuilder excludePackage(String packageName) {
            packagesToExclude.add(packageName);
            return this;
        }

        /* (non-Javadoc)
         * @see com.tvd12.ezyfox.bean.impl.EzyBeanContextBuilder#excludePackages(java.lang.String)
         */
        @Override
        public EzyBeanContextBuilder excludePackages(String... packageNames) {
            return excludePackages(Sets.newHashSet(packageNames));
        }

        /* (non-Javadoc)
         * @see com.tvd12.ezyfox.bean.impl.EzyBeanContextBuilder#excludePackages(java.lang.Iterable)
         */
        @Override
        public EzyBeanContextBuilder excludePackages(Iterable<String> packageNames) {
            for(String packageName : packageNames)
                excludePackage(packageName);
            return this;
        }


        /* (non-Javadoc)
         * @see com.tvd12.ezyfox.bean.impl.EzyBeanContextBuilder#addConfigurationClass(java.lang.Class)
         */
        @Override
        public EzyBeanContextBuilder addConfigurationClass(Class clazz) {
            if(clazz.isAnnotationPresent(EzyConfigurationBefore.class)) {
                configurationBeforeClasses.add(clazz);
            }
            else if(clazz.isAnnotationPresent(EzyConfigurationAfter.class)) {
                configurationAfterClasses.add(clazz);
            }
            else {
                configurationClasses.add(clazz);
            }
            return this;
        }

        /* (non-Javadoc)
         * @see com.tvd12.ezyfox.bean.impl.EzyBeanContextBuilder#addConfigurationClasses(java.lang.Class)
         */
        @Override
        public EzyBeanContextBuilder addConfigurationClasses(Class... classes) {
            for(Class clazz : classes)
                addConfigurationClass(clazz);
            return this;
        }

        /* (non-Javadoc)
         * @see com.tvd12.ezyfox.bean.impl.EzyBeanContextBuilder#addConfigurationClasses(java.lang.Iterable)
         */
        @Override
        public EzyBeanContextBuilder addConfigurationClasses(Iterable<Class> classes) {
            for(Class clazz : classes)
                addConfigurationClass(clazz);
            return this;
        }

        /* (non-Javadoc)
         * @see com.tvd12.ezyfox.bean.impl.EzyBeanContextBuilder#addConfigurationBeforeClass(java.lang.Class)
         */
        @Override
        public EzyBeanContextBuilder addConfigurationBeforeClass(Class clazz) {
            configurationBeforeClasses.add(clazz);
            return this;
        }

        /* (non-Javadoc)
         * @see com.tvd12.ezyfox.bean.impl.EzyBeanContextBuilder#addConfigurationBeforeClasses(java.lang.Class)
         */
        @Override
        public EzyBeanContextBuilder addConfigurationBeforeClasses(Class... classes) {
            for(Class clazz : classes)
                addConfigurationBeforeClass(clazz);
            return this;
        }

        /* (non-Javadoc)
         * @see com.tvd12.ezyfox.bean.impl.EzyBeanContextBuilder#addConfigurationBeforeClasses(java.lang.Iterable)
         */
        @Override
        public EzyBeanContextBuilder addConfigurationBeforeClasses(Iterable<Class> classes) {
            for(Class clazz : classes)
                addConfigurationBeforeClass(clazz);
            return this;
        }

        /* (non-Javadoc)
         * @see com.tvd12.ezyfox.bean.impl.EzyBeanContextBuilder#addConfigurationAfterClass(java.lang.Class)
         */
        @Override
        public EzyBeanContextBuilder addConfigurationAfterClass(Class clazz) {
            configurationAfterClasses.add(clazz);
            return this;
        }

        /* (non-Javadoc)
         * @see com.tvd12.ezyfox.bean.impl.EzyBeanContextBuilder#addConfigurationAfterClasses(java.lang.Class)
         */
        @Override
        public EzyBeanContextBuilder addConfigurationAfterClasses(Class... classes) {
            for(Class clazz : classes)
                addConfigurationAfterClass(clazz);
            return this;
        }

        /* (non-Javadoc)
         * @see com.tvd12.ezyfox.bean.impl.EzyBeanContextBuilder#addConfigurationAfterClasses(java.lang.Iterable)
         */
        @Override
        public EzyBeanContextBuilder addConfigurationAfterClasses(Iterable<Class> classes) {
            for(Class clazz : classes)
                addConfigurationAfterClass(clazz);
            return this;
        }

        /* (non-Javadoc)
         * @see com.tvd12.ezyfox.bean.impl.EzyBeanContextBuilder#addSingleton(java.lang.Object)
         */
        @Override
        public EzyBeanContextBuilder addSingleton(Object singleton) {
            singletonFactory.addSingleton(singleton);
            return this;
        }

        /* (non-Javadoc)
         * @see com.tvd12.ezyfox.bean.impl.EzyBeanContextBuilder#addSingleton(java.lang.String, java.lang.Object)
         */
        @Override
        public EzyBeanContextBuilder addSingleton(String name, Object singleton) {
            singletonFactory.addSingleton(name, singleton);
            return this;
        }

        /* (non-Javadoc)
         * @see com.tvd12.ezyfox.bean.impl.EzyBeanContextBuilder#addSingletons(java.util.Map)
         */
        @Override
        public EzyBeanContextBuilder addSingletons(Map<String, Object> singletons) {
            for(Entry<String, Object> e : singletons.entrySet())
                singletonFactory.addSingleton(e.getKey(), e.getValue());
            return this;
        }


        /* (non-Javadoc)
         * @see com.tvd12.ezyfox.bean.impl.EzyBeanContextBuilder#addSingletonsByKey(java.util.Map)
         */
        @Override
        public EzyBeanContextBuilder addSingletonsByKey(Map<EzyBeanKey, Object> singletons) {
            singletonFactory.addSingletonsByBeanKey(singletons);
            return this;
        }

        /* (non-Javadoc)
         * @see com.tvd12.ezyfox.bean.impl.EzyBeanContextBuilder#addPrototypeSupplier(java.lang.String, com.tvd12.ezyfox.bean.EzyPrototypeSupplier)
         */
        @Override
        public EzyBeanContextBuilder addPrototypeSupplier(String objectName, EzyPrototypeSupplier supplier) {
            prototypeFactory.addSupplier(objectName, supplier);
            return this;
        }

        /* (non-Javadoc)
         * @see com.tvd12.ezyfox.bean.impl.EzyBeanContextBuilder#addPrototypeSuppliers(java.lang.Map)
         */
        @Override
        public EzyBeanContextBuilder addPrototypeSuppliers(Map<String, EzyPrototypeSupplier> suppliers) {
            for(Entry<String, EzyPrototypeSupplier> e : suppliers.entrySet())
                prototypeFactory.addSupplier(e.getKey(), e.getValue());
            return this;
        }

        /* (non-Javadoc)
         * @see com.tvd12.ezyfox.bean.impl.EzyBeanContextBuilder#addSingletonClass(java.lang.Class)
         */
        @Override
        public EzyBeanContextBuilder addSingletonClass(Class clazz) {
            this.singletonClasses.add(clazz);
            return this;
        }

        /* (non-Javadoc)
         * @see com.tvd12.ezyfox.bean.impl.EzyBeanContextBuilder#addSingletonClasses(java.lang.Class)
         */
        @Override
        public EzyBeanContextBuilder addSingletonClasses(Class... classes) {
            return addSingletonClasses(Sets.newHashSet(classes));
        }

        /* (non-Javadoc)
         * @see com.tvd12.ezyfox.bean.impl.EzyBeanContextBuilder#addSingletonClasses(java.lang.Iterable)
         */
        @Override
        public EzyBeanContextBuilder addSingletonClasses(Iterable<Class> classes) {
            for(Class clazz : classes)
                this.addSingletonClass(clazz);
            return this;
        }

        /* (non-Javadoc)
         * @see com.tvd12.ezyfox.bean.impl.EzyBeanContextBuilder#addSingletonClass(java.lang.Class)
         */
        @Override
        public EzyBeanContextBuilder addSingletonClass(String name, Class clazz) {
            this.singletonClasses.add(clazz);
            this.namedSingletonClasses.put(clazz, name);
            return this;
        }

        /* (non-Javadoc)
         * @see com.tvd12.ezyfox.bean.impl.EzyBeanContextBuilder#addSingletonClasses(java.util.Map)
         */
        @Override
        public EzyBeanContextBuilder addSingletonClasses(Map<String, Class> classes) {
            for(Entry<String, Class> e : classes.entrySet()) {
                this.singletonClasses.add(e.getValue());
                this.namedSingletonClasses.put(e.getValue(), e.getKey());
            }
            return this;
        }

        /* (non-Javadoc)
         * @see com.tvd12.ezyfox.bean.impl.EzyBeanContextBuilder#addPrototypeClass(java.lang.Class)
         */
        @Override
        public EzyBeanContextBuilder addPrototypeClass(Class clazz) {
            this.prototypeClasses.add(clazz);
            return this;
        }

        /* (non-Javadoc)
         * @see com.tvd12.ezyfox.bean.impl.EzyBeanContextBuilder#addPrototypeClasses(java.lang.Class)
         */
        @Override
        public EzyBeanContextBuilder addPrototypeClasses(Class... classes) {
            return addPrototypeClasses(Sets.newHashSet(classes));
        }

        /* (non-Javadoc)
         * @see com.tvd12.ezyfox.bean.impl.EzyBeanContextBuilder#addPrototypeClasses(java.lang.Iterable)
         */
        @Override
        public EzyBeanContextBuilder addPrototypeClasses(Iterable<Class> classes) {
            for(Class clazz : classes)
                this.addPrototypeClass(clazz);
            return this;
        }

        /* (non-Javadoc)
         * @see com.tvd12.ezyfox.bean.impl.EzyBeanContextBuilder#addPrototypeClass(java.lang.String, java.lang.Class)
         */
        @Override
        public EzyBeanContextBuilder addPrototypeClass(String name, Class clazz) {
            this.prototypeClasses.add(clazz);
            this.namedPrototypeClasses.put(clazz, name);
            return this;
        }

        /* (non-Javadoc)
         * @see com.tvd12.ezyfox.bean.impl.EzyBeanContextBuilder#addPrototypeClasses(java.util.Map)
         */
        @Override
        public EzyBeanContextBuilder addPrototypeClasses(Map<String, Class> classes) {
            for(Entry<String, Class> e : classes.entrySet()) {
                this.prototypeClasses.add(e.getValue());
                this.namedPrototypeClasses.put(e.getValue(), e.getKey());
            }
            return this;
        }

        /*
         * (non-Javadoc)
         * @see com.tvd12.ezyfox.bean.EzyBeanContextBuilder#addAllClasses(java.lang.Object)
         */
        @Override
        public EzyBeanContextBuilder addAllClasses(Object reflection) {
            if(reflection instanceof EzyReflection)
                addAllClasses((EzyReflection)reflection);
            return this;
        }

        /* (non-Javadoc)
         * @see com.tvd12.ezyfox.bean.impl.EzyBeanContextBuilder#excludeConfigurationClass(java.lang.Class)
         */
        @Override
        public EzyBeanContextBuilder excludeConfigurationClass(Class clazz) {
            excludeConfigurationClasses.add(clazz);
            return this;
        }

        /* (non-Javadoc)
         * @see com.tvd12.ezyfox.bean.impl.EzyBeanContextBuilder#excludeConfigurationClasses(java.lang.Class)
         */
        @Override
        public EzyBeanContextBuilder excludeConfigurationClasses(Class... classes) {
            for(Class clazz : classes)
                excludeConfigurationClass(clazz);
            return this;
        }

        /* (non-Javadoc)
         * @see com.tvd12.ezyfox.bean.impl.EzyBeanContextBuilder#excludeConfigurationClasses(java.lang.Iterable)
         */
        @Override
        public EzyBeanContextBuilder excludeConfigurationClasses(Iterable<Class> classes) {
            for(Class clazz : classes)
                excludeConfigurationClass(clazz);
            return this;
        }

        /*
         * (non-Javadoc)
         * @see com.tvd12.ezyfox.bean.EzyBeanContextBuilder#errorHandler(com.tvd12.ezyfox.bean.EzyErrorHandler)
         */
        @Override
        public EzyBeanContextBuilder errorHandler(EzyErrorHandler handler) {
            this.errorHandler = handler;
            return this;
        }

        /*
         * (non-Javadoc)
         * @see com.tvd12.ezyfox.bean.EzyBeanContextBuilder#addProperties(java.util.Map)
         */
        @Override
        public EzyBeanContextBuilder addProperties(Map properties) {
            this.properties.putAll(properties);
            return this;
        }

        /*
         * (non-Javadoc)
         * @see com.tvd12.ezyfox.bean.EzyBeanContextBuilder#addProperties(java.lang.String)
         */
        @Override
        public EzyBeanContextBuilder addProperties(String file) {
            return addProperties(file, getActiveProfiles());
        }

        /*
         * (non-Javadoc)
         * @see com.tvd12.ezyfox.bean.EzyBeanContextBuilder#addProperties(java.lang.String, java.lang.String)
         */
        @Override
        public EzyBeanContextBuilder addProperties(String file, String activeProfiles) {
            Properties props = new MultiFileReader(activeProfiles).read(file);
            return addProperties(props);
        }

        /*
         * (non-Javadoc)
         * @see com.tvd12.ezyfox.bean.EzyBeanContextBuilder#addProperties(java.lang.Iterable)
         */
        @Override
        public EzyBeanContextBuilder addProperties(Iterable<String> files) {
            for(String file : files)
                addProperties(file);
            return this;
        }

        /*
         * (non-Javadoc)
         * @see com.tvd12.ezyfox.bean.EzyBeanContextBuilder#addProperties(java.lang.Iterable, java.lang.String)
         */
        @Override
        public EzyBeanContextBuilder addProperties(Iterable<String> files, String activeProfiles) {
            for(String file : files)
                addProperties(file, activeProfiles);
            return this;
        }

        /*
         * (non-Javadoc)
         * @see com.tvd12.ezyfox.bean.EzyBeanContextBuilder#addProperties(java.io.File)
         */
        @Override
        public EzyBeanContextBuilder addProperties(File file) {
            return addProperties(file, getActiveProfiles());
        }

        /*
         * (non-Javadoc)
         * @see com.tvd12.ezyfox.bean.EzyBeanContextBuilder#addProperties(java.io.File, java.lang.String)
         */
        @Override
        public EzyBeanContextBuilder addProperties(File file, String activeProfiles) {
            Properties props = new MultiFileReader(activeProfiles).read(file);
            return addProperties(props);
        }

        /*
         * (non-Javadoc)
         * @see com.tvd12.ezyfox.bean.EzyBeanContextBuilder#addProperties(java.util.Collection)
         */
        @Override
        public EzyBeanContextBuilder addProperties(Collection<File> files) {
            for(File file : files)
                addProperties(file);
            return this;
        }

        /*
         * (non-Javadoc)
         * @see com.tvd12.ezyfox.bean.EzyBeanContextBuilder#addProperties(java.util.Collection, java.lang.String)
         */
        @Override
        public EzyBeanContextBuilder addProperties(Collection<File> files, String activeProfiles) {
            for(File file : files)
                addProperties(file, activeProfiles);
            return this;
        }

        /*
         * (non-Javadoc)
         * @see com.tvd12.ezyfox.bean.EzyBeanContextBuilder#addProperties(java.io.InputStream)
         */
        @Override
        public EzyBeanContextBuilder addProperties(InputStream inputStream) {
            Properties props = new BaseFileReader().loadInputStream(inputStream);
            return addProperties(props);
        }

        /*
         * (non-Javadoc)
         * @see com.tvd12.ezyfox.bean.EzyBeanContextBuilder#addProperty(java.lang.String, java.lang.Object)
         */
        public EzyBeanContextBuilder addProperty(String key, Object value) {
            this.properties.put(key, value);
            return this;
        }

        /*
         * (non-Javadoc)
         * @see com.tvd12.ezyfox.bean.EzyBeanContextBuilder#propertiesMap(com.tvd12.ezyfox.bean.EzyPropertiesMap)
         */
        @Override
        public EzyBeanContextBuilder propertiesMap(EzyPropertiesMap propertiesMap) {
            this.propertiesMap = propertiesMap;
            return this;
        }

        /*
         * (non-Javadoc)
         * @see com.tvd12.ezyfox.bean.EzyBeanContextBuilder#propertiesReader(com.tvd12.ezyfox.properties.EzyPropertiesReader)
         */
        @Override
        public EzyBeanContextBuilder propertiesReader(EzyPropertiesReader propertiesReader) {
            this.propertiesReader = propertiesReader;
            return this;
        }

        /*
         * (non-Javadoc)
         * @see com.tvd12.ezyfox.bean.EzyBeanContextBuilder#propertiesBeanClass(java.lang.String, java.lang.Class)
         */
        @Override
        public EzyBeanContextBuilder propertiesBeanClass(String prefix, Class propertiesBeanClass) {
            this.propertiesBeanClasses
                .computeIfAbsent(prefix, k -> new HashSet<>())
                .add(propertiesBeanClass);
            return this;
        }

        /* (non-Javadoc)
         * @see com.tvd12.ezyfox.bean.impl.EzyBeanContextBuilder#build()
         */
        @Override
        public EzySimpleBeanContext build() {
            EzySimpleBeanContext context = new EzySimpleBeanContext();
            readDefaultPropertiesFiles();
            setVariableValues(properties);
            packagesToScan.removeAll(packagesToExclude);
            removeExcludeConfigurationClasses();
            context.properties = properties;
            context.packagesToScan = packagesToScan;
            context.prototypeFactory = prototypeFactory;
            context.singletonFactory = singletonFactory;
            context.beanNameTranslator = beanNameTranslator;
            context.propertiesReader = getPropertiesReader();
            doScanPackages();
            scanAutoConfigurationPackage();
            readImportClasses();
            addSingleton("beanContext", context);
            addSingleton("singletonFactory", singletonFactory);
            addSingleton("prototypeFactory", prototypeFactory);
            addSingleton("beanNameTranslator", beanNameTranslator);
            readConfigurationClasses();
            scanPackagesScanClasses();
            packagesToScan.removeAll(packagesToExclude);
            removeExcludeConfigurationClasses();
            loadPropertiesBeanClasses();
            loadPropertiesSources();
            setVariableValues(properties);
            mapProperties();
            addPropertiesBeans();
            singletonFactory.addSingletonClasses(singletonClasses);
            prototypeClasses.removeAll(singletonClasses);
            loadConfigurationBeforeClasses(context);
            addScannedSingletonsToFactory(context);
            addDefaultPrototypeSuppliers();
            addScannedPrototypeSuppliersToFactory();
            tryLoadUncompletedSingletonsAgain(context, false);
            loadConfigurationClasses(context);
            tryLoadUncompletedSingletonsAgain(context, true);
            loadConfigurationAfterClasses(context);
            return context;
        }

        private void readDefaultPropertiesFiles() {
            String activeProfiles = getActiveProfiles();
            logger.info("active profiles: {}", activeProfiles);
            Properties props = new Properties();
            FileReader fileReader = new MultiFileReader(activeProfiles);
            props.putAll(fileReader.read("application.properties"));
            props.putAll(fileReader.read("application.yaml"));
            props.putAll(fileReader.read("application.yml"));
            for(Object key : props.keySet())
                properties.put(key, props.get(key));
        }

        private String getActiveProfiles() {
            String activeProfiles = properties.getProperty(ACTIVE_PROFILES_KEY);
            if(EzyStrings.isNoContent(activeProfiles))
                activeProfiles = properties.getProperty(EZYFOX_ACTIVE_PROFILES_KEY);
            if(activeProfiles == null) {
                if(activeProfileSet.size() > 0) {
                    return String.join(",", activeProfileSet);
                }
                return null;
            }
            else {
                if(activeProfileSet.size() > 0) {
                    return activeProfiles + "," + String.join(",", activeProfileSet);
                }
                return activeProfiles;
            }
        }

        private void removeExcludeConfigurationClasses() {
            configurationClasses.removeAll(excludeConfigurationClasses);
            configurationBeforeClasses.removeAll(excludeConfigurationClasses);
            configurationAfterClasses.removeAll(excludeConfigurationClasses);
        }

        private void doScanPackages() {
            doScanPackages(packagesToScan);
        }

        private void doScanPackages(Set<String> packages) {
            if(packages.size() > 0) {
                EzyReflection reflection = EzyPackages.scanPackages(packages);
                addAllClasses(reflection);
            }
        }

        private void scanAutoConfigurationPackage() {
            if(enableAutoConfiguration) {
                addAllClasses(EzyPackages.scanPackage(AUTO_CONFIGURATION_PACKAGE));
            }
        }

        private void readImportClasses() {
            addAllClasses(new EzyImportReflection((Set)importClasses));
        }

        private void mapProperties() {
            if(propertiesMap == null)
                return;
            Map<String, String> keyMap = propertiesMap.keyMap();
            for(String originKey : keyMap.keySet()) {
                String mapKey = keyMap.get(originKey);
                Object value = properties.get(originKey);
                properties.put(mapKey, value);
            }
        }

        private void addScannedSingletonsToFactory(EzyBeanContext context) {
            for(Class type : singletonClasses)
                createAndLoadSingleton(context, type);
        }

        private void addScannedPrototypeSuppliersToFactory() {
            for(Class type : prototypeClasses)
                createAndLoadPrototypeSupplier(type);
        }

        private Object createAndLoadSingleton(EzyBeanContext context, Class type) {
            return createAndLoadSingleton(context, type, false);
        }

        private Object createAndLoadSingleton(EzyBeanContext context, Class type, boolean reload) {
            String beanName = getSingletonBeanName(type);
            Object current = singletonFactory.getSingleton(beanName, type);
            if(current != null && !reload) return current;
            List<Class<?>> stackCallClasses = new ArrayList<>();
            try {
                EzySingletonLoader loader =
                        new EzyByConstructorSingletonLoader(beanName, new EzyClass(type), stackCallClasses);
                return loader.load(context);
            }
            catch(EzyNewSingletonException e) {
                for(Class<?> clazz : stackCallClasses)
                    unloadedSingletons.addItems(e.getErrorKey(), clazz);
                return null;
            }
        }

        private String getSingletonBeanName(Class type) {
            String beanName = namedSingletonClasses.get(type);
            if(beanName == null)
                beanName = getSingletonName(type);
            return beanName;
        }

        private void createAndLoadPrototypeSupplier(Class type) {
            String beanName = getPrototypeBeanName(type);
            Object current = prototypeFactory.getSupplier(beanName, type);
            if(current == null)
                new EzyByConstructorPrototypeSupplierLoader(beanName, new EzyClass(type)).load(prototypeFactory);
        }

        private String getPrototypeBeanName(Class type) {
            String beanName = namedPrototypeClasses.get(type);
            if(beanName == null)
                beanName = getPrototypeName(type);
            return beanName;
        }

        private void readConfigurationClasses() {
            Set<Class> classes = new HashSet<>();
            classes.addAll(configurationBeforeClasses);
            classes.addAll(configurationAfterClasses);
            classes.addAll(configurationClasses);
            for(Class<?> clazz : classes) {
                EzyPackagesToScan packagesScanAnn = clazz.getAnnotation(EzyPackagesToScan.class);
                if(packagesScanAnn != null)
                    packagesScanClasses.add(clazz);
                EzyBeanPackagesToScan beanPackagesScanAnn = clazz.getAnnotation(EzyBeanPackagesToScan.class);
                if(beanPackagesScanAnn != null)
                    packagesScanClasses.add(clazz);
                EzyPropertiesBean propertiesBeanAnn = clazz.getAnnotation(EzyPropertiesBean.class);
                if(propertiesBeanAnn != null)
                    propertiesBeanAnnotatedClasses.add(clazz);
                EzyPropertiesBeans propertiesBeansAnn = clazz.getAnnotation(EzyPropertiesBeans.class);
                if(propertiesBeansAnn != null)
                    propertiesBeansAnnotatedClasses.add(clazz);
            }
        }

        private void scanPackagesScanClasses() {
            for(Class clazz : packagesScanClasses)
                this.scanPackagesScanClass(clazz);
        }

        private void scanPackagesScanClass(Class<?> clazz) {
            Set<String> packets = new HashSet<>();
            EzyPackagesToScan packagesScanAnn = clazz.getAnnotation(EzyPackagesToScan.class);
            if(packagesScanAnn != null) {
                String[] value = packagesScanAnn.value();
                if(value.length == 0) {
                    packets.add(clazz.getPackage().getName());
                }
                else {
                    packets.addAll(Arrays.asList(value));
                }
            }
            EzyBeanPackagesToScan beanPackagesScanAnn = clazz.getAnnotation(EzyBeanPackagesToScan.class);
            if(beanPackagesScanAnn != null) {
                String[] value = beanPackagesScanAnn.value();
                if(value.length == 0) {
                    packets.add(clazz.getPackage().getName());
                }
                else {
                    packets.addAll(Arrays.asList(value));
                }
            }
            packagesToScan.addAll(packets);
            doScanPackages(packets);
        }

        private void loadPropertiesSources() {
            for(Class<?> clazz : propertiesSourceClasses) {
                EzyPropertiesSources ann = clazz.getAnnotation(EzyPropertiesSources.class);
                for(String file : ann.value())
                    addProperties(file);
            }
        }

        private void loadPropertiesBeanClasses() {
            for(Class<?> clazz : propertiesBeanAnnotatedClasses) {
                EzyPropertiesBean ann = clazz.getAnnotation(EzyPropertiesBean.class);
                Class<?> beanClass = ann.value();
                if(beanClass == Object.class)
                    beanClass = clazz;
                propertiesBeanClass(ann.prefix(), beanClass);
            }

            for(Class<?> clazz : propertiesBeansAnnotatedClasses) {
                EzyPropertiesBeans anns = clazz.getAnnotation(EzyPropertiesBeans.class);
                for(EzyPropertiesBean ann : anns.value())
                    propertiesBeanClass(ann.prefix(), ann.value());
            }
        }

        private void addPropertiesBeans() {
            for(String prefix : propertiesBeanClasses.keySet()) {
                Set<Class<?>> propertiesBeanClassSet = propertiesBeanClasses.get(prefix);
                for(Class<?> propertiesBeanClass : propertiesBeanClassSet) {
                    Object propertiesBean = newPropertiesMapper()
                            .propertyPrefix(prefix)
                            .clazz(propertiesBeanClass)
                            .map();
                    addSingleton(EzyBeanNameParser.getBeanName(propertiesBeanClass), propertiesBean);
                }
            }
        }

        private void loadConfigurationBeforeClasses(EzyBeanContext context) {
            List<Class> classes = EzyConfigurationBeforeClassSorter.sort(configurationBeforeClasses);
            for(Class clazz : classes)
                loadConfigurationClass(clazz, context);
        }

        private void loadConfigurationClasses(EzyBeanContext context) {
            List<Class> classes = EzyConfigurationClassSorter.sort(configurationClasses);
            for(Class clazz : classes)
                loadConfigurationClass(clazz, context);
        }

        private void loadConfigurationAfterClasses(EzyBeanContext context) {
            List<Class> classes = EzyConfigurationAfterClassSorter.sort(configurationAfterClasses);
            for(Class clazz : classes)
                loadConfigurationClass(clazz, context);
        }

        private void loadConfigurationClass(Class<?> clazz, EzyBeanContext context) {
            try {
                new EzySimpleConfigurationLoader().context(context).clazz(clazz).load();
            }
            catch (Throwable e) {
                if(EzyBeanAutoConfig.class.isAssignableFrom(clazz)) {
                    EzyBeanAutoConfig.LOGGER.debug("{} auto config failed due to: {} ({})", clazz.getName(), e.getClass().getName(), e.getMessage());
                }
                else {
                    throw e;
                }
            }
        }

        private void tryLoadUncompletedSingletonsAgain(EzyBeanContext context, boolean finish) {
            Set<EzyBeanKey> keySet = new HashSet<>(unloadedSingletons.keySet());
            for(EzyBeanKey key : keySet) {
                Set<Class<?>> uncompleted = unloadedSingletons.get(key);
                logger.debug("unload bean: {}, uncompleted: {}", key, uncompleted);
                logger.debug("try load bean {} again", key);
                loadUncompletedSingletons(context, key, uncompleted, finish);
            }
            if(finish) {
                while(!unloadedSingletons.isEmpty())
                    tryLoadUncompletedSingletonsAgain(context, finish);
            }
        }

        private void loadUncompletedSingletons(
                EzyBeanContext context, EzyBeanKey key, Set<Class<?>> uncompleted, boolean finish) {
            Object singleton = getSingletonOfErrorBeanKey0(context, key, finish);
            if(singleton == null) return;
            logger.debug("found singleton {} with key {}", singleton, key);
            for(Class<?> clazz : uncompleted) {
                createAndLoadSingleton(context, clazz, true);
            }
        }

        private Object getSingletonOfErrorBeanKey0(EzyBeanContext context, EzyBeanKey key, boolean finish) {
            Object singleton = null;
            try {
                singleton = getSingletonOfErrorBeanKey(context, key);
            }
            catch(EzySingletonException e) {
                if(finish) {
                    errorHandler.handle(e);
                    unloadedSingletons.remove(key);
                }
            }
            if(singleton != null) {
                unloadedSingletons.remove(key);
            }
            return singleton;
        }

        private Object getSingletonOfErrorBeanKey(EzyBeanContext context, EzyBeanKey key) {
            Object singleton = singletonFactory.getSingleton(key.getName(), key.getType());
            if(singleton != null) return singleton;
            return loadSingletonOfBeanKey(context, key);
        }

        private Object loadSingletonOfBeanKey(EzyBeanContext context, EzyBeanKey key) {
            for(EzyBeanKey i : unloadedSingletons.keySet()) {
                Set<Class<?>> classes = unloadedSingletons.get(i);
                for(Class<?> implClass : classes) {
                    if(key.getType().isAssignableFrom(implClass)) {
                        return createAndLoadSingleton(context, implClass, true);
                    }
                }
            }
            throw EzySingletonException.implementationNotFound(key, unloadedSingletons.get(key));
        }

        private void addAllClasses(EzyReflection reflection) {
            importClasses.addAll(reflection.getAnnotatedClasses(EzyImport.class));
            singletonClasses.addAll(reflection.getAnnotatedClasses(EzySingleton.class));
            prototypeClasses.addAll(reflection.getAnnotatedClasses(EzyPrototype.class));
            packagesScanClasses.addAll(reflection.getAnnotatedClasses(EzyPackagesToScan.class));
            packagesScanClasses.addAll(reflection.getAnnotatedClasses(EzyBeanPackagesToScan.class));
            propertiesSourceClasses.addAll(reflection.getAnnotatedClasses(EzyPropertiesSources.class));
            configurationClasses.addAll(reflection.getAnnotatedClasses(EzyConfiguration.class));
            configurationBeforeClasses.addAll(reflection.getAnnotatedClasses(EzyConfigurationBefore.class));
            configurationAfterClasses.addAll(reflection.getAnnotatedClasses(EzyConfigurationAfter.class));
            propertiesBeanAnnotatedClasses.addAll(reflection.getAnnotatedClasses(EzyPropertiesBean.class));
            propertiesBeansAnnotatedClasses.addAll(reflection.getAnnotatedClasses(EzyPropertiesBeans.class));
            excludeConfigurationClasses.addAll(getExcludeConfigurationClasses(reflection));
            enableAutoConfiguration = enableAutoConfiguration && reflection.getAnnotatedClass(EzyDisableAutoConfiguration.class) == null;
        }

        private Set<Class<?>> getExcludeConfigurationClasses(EzyReflection reflection) {
            Set<Class<?>> answer = new HashSet<>();
            Set<Class<?>> classes = reflection.getAnnotatedClasses(EzyExclusiveClassesConfiguration.class);
            for(Class<?> clazz : classes) {
                EzyExclusiveClassesConfiguration ann = clazz.getAnnotation(EzyExclusiveClassesConfiguration.class);
                answer.addAll(Arrays.asList(ann.value()));
            }
            return answer;
        }

        private EzyPropertiesReader getPropertiesReader() {
            return propertiesReader != null ? propertiesReader : new EzySimplePropertiesReader();
        }

        private void addDefaultPrototypeSuppliers() {
            prototypeFactory.addSupplier(EzyArrayListSupplier.getInstance());
            prototypeFactory.addSupplier(EzyCollectionSupplier.getInstance());
            prototypeFactory.addSupplier(EzyConcurrentHashMapSupplier.getInstance());
            prototypeFactory.addSupplier(EzyCopyOnWriteArrayListSupplier.getInstance());
            prototypeFactory.addSupplier(EzyCopyOnWriteArraySetSupplier.getInstance());
            prototypeFactory.addSupplier(EzyHashMapSupplier.getInstance());
            prototypeFactory.addSupplier(EzyHashSetSupplier.getInstance());
            prototypeFactory.addSupplier(EzyLinkedListSupplier.getInstance());
            prototypeFactory.addSupplier(EzyListSupplier.getInstance());
            prototypeFactory.addSupplier(EzyMapSupplier.getInstance());
            prototypeFactory.addSupplier(EzyQueueSupplier.getInstance());
            prototypeFactory.addSupplier(EzySetSupplier.getInstance());
            prototypeFactory.addSupplier(EzyStackSupplier.getInstance());
            prototypeFactory.addSupplier(EzyTreeMapSupplier.getInstance());
        }

        private PropertiesMapper newPropertiesMapper() {
            return new PropertiesMapper()
                    .addPropertyAnnotation(new PropertyAnnotation(
                            EzyProperty.class,
                            a -> ((EzyProperty)a).value(),
                            a -> ((EzyProperty)a).prefix()))
                    .data(properties)
                    .valueConverter(new ValueConverter() {
                        @Override
                        public <T> T convert(Object value, Class<T> outType) {
                            return EzySimpleValueConverter
                                    .getSingleton()
                                    .convert(value, outType);
                        }
                    });
        }

    }
}