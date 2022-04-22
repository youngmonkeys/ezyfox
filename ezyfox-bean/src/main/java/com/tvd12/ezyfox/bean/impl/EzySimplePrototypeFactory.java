package com.tvd12.ezyfox.bean.impl;

import static com.tvd12.ezyfox.bean.impl.EzyBeanKey.of;
import static com.tvd12.ezyfox.reflect.EzyClasses.flatSuperAndInterfaceClasses;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;

import com.tvd12.ezyfox.annotation.EzyKeyValue;
import com.tvd12.ezyfox.bean.EzyPrototypeFactory;
import com.tvd12.ezyfox.bean.EzyPrototypeSupplier;
import com.tvd12.ezyfox.bean.annotation.EzyPrototype;
import com.tvd12.ezyfox.io.EzyMaps;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class EzySimplePrototypeFactory
        extends EzySimpleBeanFactory
        implements EzyPrototypeFactory {

    protected final Set<EzyPrototypeSupplier> supplierSet
            = new HashSet<>();
    protected final Map<EzyBeanKey, EzyPrototypeSupplier> supplierByKey
            = new ConcurrentHashMap<>();
    protected final Map<EzyPrototypeSupplier, Map> suppliersByProperties
            = new ConcurrentHashMap<>();

    @Override
    public EzyPrototypeSupplier getSupplier(Class objectType) {
        return getSupplier(getBeanName(objectType), objectType);
    }

    @Override
    public EzyPrototypeSupplier getSupplier(String objectName, Class objectType) {
        String realname = translateBeanName(objectName, objectType);
        EzyPrototypeSupplier supplier = supplierByKey.get(of(realname, objectType));
        if(supplier == null) {
            for(EzyBeanKey key : supplierByKey.keySet()) {
                if(objectType.isAssignableFrom(key.getType())) {
                    supplier = supplierByKey.get(key);
                    break;
                }
            }
        }
        return supplier;
    }

    @Override
    public EzyPrototypeSupplier getAnnotatedSupplier(Class annotationClass) {
        List<EzyPrototypeSupplier> list = getSuppliers(annotationClass);
        if(list.size() > 0)
            return list.get(0);
        return null;
    }

    @Override
    public EzyPrototypeSupplier getSupplier(Map properties) {
        for(Entry<EzyPrototypeSupplier, Map> entry : suppliersByProperties.entrySet()) {
            if(EzyMaps.containsAll(entry.getValue(), properties))
                return entry.getKey();
        }
        return null;
    }

    @Override
    public List<EzyPrototypeSupplier> getSuppliers() {
        return new ArrayList<>(supplierSet);
    }

    @Override
    public List<EzyPrototypeSupplier> getSuppliers(Map properties) {
        Set<EzyPrototypeSupplier> set = new HashSet<>();
        for(Entry<EzyPrototypeSupplier, Map> entry : suppliersByProperties.entrySet()) {
            if(EzyMaps.containsAll(entry.getValue(), properties))
                set.add(entry.getKey());
        }
        return new ArrayList<>(set);
    }

    @Override
    public List<EzyPrototypeSupplier> getSuppliers(Class... annotationClasses) {
        return getSuppliers(s -> {
            for(Class annotationClass : annotationClasses) {
                if(s.getObjectType().isAnnotationPresent(annotationClass))
                    return true;
            }
            return false;
        });
    }

    @Override
    public List<EzyPrototypeSupplier> getSuppliers(Predicate<EzyPrototypeSupplier> filter) {
        List<EzyPrototypeSupplier> list = new ArrayList<>();
        for(EzyPrototypeSupplier supplier : supplierSet) {
            if(filter.test(supplier))
                list.add(supplier);
        }
        return list;
    }

    @Override
    public List<EzyPrototypeSupplier> getSuppliersOf(Class parentClass) {
        return getSuppliers(s ->
            parentClass.isAssignableFrom(s.getObjectType())
        );
    }

    @Override
    public Map getProperties(EzyPrototypeSupplier supplier) {
        return suppliersByProperties.get(supplier);
    }

    @Override
    public void addSupplier(EzyPrototypeSupplier supplier) {
        Class type = supplier.getObjectType();
        addSupplier(getBeanName(type), supplier);
    }

    @Override
    public void addSupplier(String objectName, EzyPrototypeSupplier supplier) {
        Class<?> type = supplier.getObjectType();
        addSupplier(objectName, supplier, getProperties(type));
    }

    @Override
    public void addSupplier(
            String objectName, EzyPrototypeSupplier supplier, Map properties) {
        Class<?> type = supplier.getObjectType();
        EzyBeanKey key = of(objectName, type);

        if(supplierByKey.containsKey(key))
            return;

        supplierSet.add(supplier);
        supplierByKey.put(key, supplier);
        suppliersByProperties.put(supplier, properties);

        String defname = getDefaultBeanName(type);
        mapBeanName(defname, type, objectName);

        Set<Class> subTypes = flatSuperAndInterfaceClasses(type, true);
        for(Class<?> subType : subTypes)
            checkAndAddSupplier(objectName, subType, supplier);
    }

    private void checkAndAddSupplier(
            String objectName, Class<?> type, EzyPrototypeSupplier supplier) {
        EzyBeanKey key = of(objectName, type);
        if(supplierByKey.containsKey(key))
            return;
        supplierByKey.put(key, supplier);
    }

    private String getBeanName(Class<?> type) {
        return EzyBeanNameParser.getPrototypeName(type);
    }

    private Map getProperties(Class<?> type) {
        EzyPrototype ann = type.getAnnotation(EzyPrototype.class);
        Map properties = new HashMap<>();
        EzyKeyValue[] keyValues = ann != null ? ann.properties() : new EzyKeyValue[0];
        Arrays.stream(keyValues).forEach(kv -> properties.put(kv.key(), kv.value()));
        return properties;
    }

}
