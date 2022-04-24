package com.tvd12.ezyfox.bean.impl;

import com.tvd12.ezyfox.bean.EzySingletonFactory;
import com.tvd12.ezyfox.io.EzyMaps;
import lombok.Getter;

import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;

import static com.tvd12.ezyfox.bean.impl.EzyBeanKey.of;
import static com.tvd12.ezyfox.reflect.EzyClasses.flatSuperAndInterfaceClasses;

@SuppressWarnings({"unchecked", "rawtypes"})
public class EzySimpleSingletonFactory
    extends EzySimpleBeanFactory
    implements EzySingletonFactory {

    @Getter
    protected final Set<Class> singletonClasses
        = new HashSet<>();
    protected final Set<Object> singletonSet
        = new HashSet<>();
    protected final Map<EzyBeanKey, Object> singletonByKey
        = new ConcurrentHashMap<>();
    protected final Map<Object, Map> propertiesBySingleton
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

        Object existed = singletonByKey.get(key);
        if (existed != null) {
            return existed;
        }

        singletonSet.add(singleton);
        singletonByKey.put(key, singleton);
        propertiesBySingleton.put(singleton, properties);

        String defaultBeanName = getDefaultBeanName(type);
        mapBeanName(defaultBeanName, type, name);

        Set<Class> subTypes = flatSuperAndInterfaceClasses(type, true);
        for (Class<?> subType : subTypes) {
            checkAndAddSingleton(name, subType, singleton);
        }
        return singleton;
    }

    private void checkAndAddSingleton(String name, Class<?> type, Object singleton) {
        EzyBeanKey key = of(name, type);
        if (singletonByKey.containsKey(key)) {
            return;
        }
        singletonByKey.put(key, singleton);
    }

    @Override
    public void addSingletons(Map<String, Object> singletons) {
        for (String name : singletons.keySet()) {
            addSingleton(name, singletons.get(name));
        }
    }

    @Override
    public void addSingletonsByBeanKey(Map<EzyBeanKey, Object> singletons) {
        for (EzyBeanKey key : singletons.keySet()) {
            Object singleton = singletons.get(key);
            Class<?> type = singleton.getClass();
            Map properties = getProperties(type);
            singletonClasses.add(type);
            singletonSet.add(singleton);
            singletonByKey.put(key, singleton);
            propertiesBySingleton.put(singleton, properties);
        }
    }

    @Override
    public Object getSingleton(Class type) {
        String name = getBeanName(type);
        return getSingleton(name, type);
    }

    @Override
    public Object getSingleton(String name, Class type) {
        String realName = translateBeanName(name, type);
        Object singleton = singletonByKey.get(of(realName, type));
        if (singleton == null) {
            for (EzyBeanKey key : singletonByKey.keySet()) {
                if (type.isAssignableFrom(key.getType())) {
                    singleton = singletonByKey.get(key);
                    break;
                }
            }
        }
        return singleton;
    }

    @Override
    public Object getAnnotatedSingleton(Class annotationClass) {
        List list = getSingletons(annotationClass);
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public Object getSingleton(Map properties) {
        for (Entry<Object, Map> entry : propertiesBySingleton.entrySet()) {
            if (EzyMaps.containsAll(entry.getValue(), properties)) {
                return entry.getKey();
            }
        }
        return null;
    }

    @Override
    public List getSingletons() {
        return new ArrayList<>(singletonSet);
    }

    @Override
    public List getSingletons(Map properties) {
        Set set = new HashSet<>();
        for (Entry<Object, Map> entry : propertiesBySingleton.entrySet()) {
            if (EzyMaps.containsAll(entry.getValue(), properties)) {
                set.add(entry.getKey());
            }
        }
        return new ArrayList<>(set);
    }

    @Override
    public List getSingletons(Class... annotationClasses) {
        return getSingletons(o -> {
            for (Class annotationClass : annotationClasses) {
                if (o.getClass().isAnnotationPresent(annotationClass)) {
                    return true;
                }
            }
            return false;
        });
    }

    @Override
    public List getSingletons(Predicate filter) {
        List list = new ArrayList<>();
        for (Object object : singletonSet) {
            if (filter.test(object)) {
                list.add(object);
            }
        }
        return list;
    }

    @Override
    public List getSingletonsOf(Class parentClass) {
        return getSingletons(o ->
            parentClass.isAssignableFrom(o.getClass())
        );
    }

    @Override
    public Map<EzyBeanKey, Object> getSingletonMapByKey() {
        return new HashMap<>(singletonByKey);
    }

    @Override
    public Map getProperties(Object singleton) {
        return propertiesBySingleton.get(singleton);
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
