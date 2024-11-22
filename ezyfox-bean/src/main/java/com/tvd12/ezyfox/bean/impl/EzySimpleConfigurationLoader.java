package com.tvd12.ezyfox.bean.impl;

import com.tvd12.ezyfox.bean.*;
import com.tvd12.ezyfox.bean.annotation.EzyPrototype;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyfox.io.EzyMaps;
import com.tvd12.ezyfox.reflect.EzyClass;
import com.tvd12.ezyfox.reflect.EzyField;
import com.tvd12.ezyfox.reflect.EzyMethod;
import com.tvd12.ezyfox.util.EzyLoggable;
import com.tvd12.ezyfox.util.EzyPropertiesAware;

import java.lang.annotation.Annotation;
import java.util.*;

import static com.tvd12.ezyfox.bean.impl.EzyBeanNameParser.getPrototypeName;
import static com.tvd12.ezyfox.bean.impl.EzyBeanNameParser.getSingletonName;
import static com.tvd12.ezyfox.io.EzyMaps.newHashMapNewKeys;

public class EzySimpleConfigurationLoader
    extends EzyLoggable
    implements EzyConfigurationLoader {

    protected EzyClass clazz;
    protected Properties properties;
    protected EzyBeanContext context;
    protected EzyPrototypeFactory prototypeFactory;
    protected EzySingletonFactory singletonFactory;
    protected EzyBeanNameTranslator beanNameTranslator;
    protected Map<Class<?>, EzyMethod> singletonMethods;
    protected Map<EzyBeanKey, EzyMethod> singletonMethodByKey;

    @Override
    public EzyConfigurationLoader clazz(Class<?> configClass) {
        this.clazz = new EzyClass(configClass);
        this.singletonMethodByKey = mapSingletonKeyMethods();
        this.singletonMethods = newHashMapNewKeys(
            singletonMethodByKey,
            EzyBeanKey::getType
        );
        return this;
    }

    @Override
    public EzyConfigurationLoader context(EzyBeanContext context) {
        this.context = context;
        this.properties = context.getProperties();
        this.singletonFactory = context.getSingletonFactory();
        this.prototypeFactory = context.getPrototypeFactory();
        this.beanNameTranslator = context.getBeanNameTranslator();
        return this;
    }

    @Override
    public void load() {
        Object configurator = newConfigurator();
        addSingletonByFields(configurator);
        addSingletonByMethods(configurator);
        addPrototypeByFields(configurator);
        addPrototypeByMethods(configurator);
    }

    private Object newConfigurator() {
        String beanName = getSingletonName(clazz.getClazz());
        Object object = new EzyByConstructorSingletonLoader(beanName, clazz)
            .load(context);
        if (object instanceof EzyBeanContextAware) {
            ((EzyBeanContextAware) object).setContext(context);
        }
        if (object instanceof EzyPropertiesAware) {
            ((EzyPropertiesAware) object).setProperties(properties);
        }
        if (object instanceof EzyPackagesToScanAware) {
            ((EzyPackagesToScanAware) object).setPackagesToScan(context.getPackagesToScan());
        }
        if (object instanceof EzySingletonFactoryAware) {
            ((EzySingletonFactoryAware) object).setSingletonFactory(singletonFactory);
        }
        if (object instanceof EzyPrototypeFactoryAware) {
            ((EzyPrototypeFactoryAware) object).setPrototypeFactory(prototypeFactory);
        }
        if (object instanceof EzyBeanNameTranslatorAware) {
            ((EzyBeanNameTranslatorAware) object).setBeanNameTranslator(beanNameTranslator);
        }
        if (object instanceof EzyBeanConfig) {
            ((EzyBeanConfig) object).config();
        }
        return object;
    }

    private void addSingletonByFields(Object configurator) {
        getSingletonFields().forEach(f -> addSingletonByField(f, configurator));
    }

    private void addSingletonByField(EzyField field, Object configurator) {
        String beanName = getSingletonName(field);
        Object current = singletonFactory.getSingleton(
            EzyBeanKey.of(beanName, field.getType())
        );
        if (current == null) {
            EzySingletonLoader loader = new EzyConfigurationFieldSingletonLoader(
                beanName,
                field,
                configurator,
                singletonMethods
            );
            loader.load(context);
        }
    }

    private void addSingletonByMethods(Object configurator) {
        Set<EzyBeanKey> keys = new HashSet<>(singletonMethodByKey.keySet());
        for (EzyBeanKey key : keys) {
            EzyMethod method = singletonMethodByKey.remove(key);
            if (method != null) {
                logger.debug("add singleton of {} with method {}", key, method);
                addSingletonByMethod(method, configurator);
            }
        }
    }

    private void addSingletonByMethod(EzyMethod method, Object configurator) {
        String beanName = getSingletonName(method);
        Object current = singletonFactory.getSingleton(
            EzyBeanKey.of(beanName, method.getReturnType())
        );
        if (current == null) {
            EzySingletonLoader loader = new EzyConfigurationMethodSingletonLoader(
                beanName,
                method,
                configurator,
                singletonMethods
            );
            loader.load(context);
        }
    }

    //============ prototype =================
    private void addPrototypeByFields(Object configurator) {
        getPrototypeFields().forEach(f -> addPrototypeByField(f, configurator));
    }

    private void addPrototypeByField(EzyField field, Object configurator) {
        String beanName = getPrototypeName(field);
        Object current = prototypeFactory.getSupplier(beanName, field.getType());
        if (current == null) {
            EzyPrototypeSupplierLoader loader = new EzyByFieldPrototypeSupplierLoader(beanName, field, configurator);
            loader.load(prototypeFactory);
        }
    }

    private void addPrototypeByMethods(Object configurator) {
        Map<EzyBeanKey, EzyMethod> methods = mapPrototypeKeyMethods();
        Set<EzyBeanKey> keys = new HashSet<>(methods.keySet());
        for (EzyBeanKey key : keys) {
            EzyMethod method = methods.remove(key);
            logger.debug("add prototype of {} with method {}", key, method);
            addPrototypeByMethod(method, configurator);
        }
    }

    private void addPrototypeByMethod(
        EzyMethod method,
        Object configurator
    ) {
        String beanName = getPrototypeName(method);
        Object current = prototypeFactory.getSupplier(beanName, method.getReturnType());
        if (current == null) {
            EzyPrototypeSupplierLoader loader = new EzyByMethodPrototypeSupplierLoader(beanName, method, configurator);
            loader.load(prototypeFactory);
        }
    }

    //============ get components ==========

    private List<EzyField> getSingletonFields() {
        return getBeanFields(EzySingleton.class);
    }

    private Map<EzyBeanKey, EzyMethod> mapSingletonKeyMethods() {
        return mapBeanKeyMethods(EzySingleton.class);
    }

    private List<EzyField> getPrototypeFields() {
        return getBeanFields(EzyPrototype.class);
    }

    private Map<EzyBeanKey, EzyMethod> mapPrototypeKeyMethods() {
        return mapBeanKeyMethods(EzyPrototype.class);
    }

    private List<EzyField> getBeanFields(Class<? extends Annotation> annClass) {
        return clazz.getPublicFields(f -> f.isAnnotated(annClass));
    }

    private Map<EzyBeanKey, EzyMethod> mapBeanKeyMethods(Class<? extends Annotation> annClass) {
        List<EzyMethod> methods = clazz.getPublicMethods(m ->
            m.isAnnotated(annClass) && m.getReturnType() != void.class
        );
        return EzyMaps.newHashMap(
            methods,
            it -> EzyBeanKey.of(
                getSingletonName(it),
                it.getReturnType()
            )
        );
    }
}
