package com.tvd12.ezyfox.util;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

public class EzyMapFactory {

    @SuppressWarnings("rawtypes")
    protected final Map<Class, Supplier<Map>> suppliers = defaultSuppliers();

    @SuppressWarnings({"rawtypes", "unchecked"})
    public <T extends Map> T newMap(Class mapType) {
        if (suppliers.containsKey(mapType)) {
            return (T) suppliers.get(mapType).get();
        }
        throw new IllegalArgumentException("unknown implementation of " + mapType);
    }

    @SuppressWarnings("rawtypes")
    private Map<Class, Supplier<Map>> defaultSuppliers() {
        Map<Class, Supplier<Map>> map = new ConcurrentHashMap<>();
        map.put(Map.class, HashMap::new);
        map.put(HashMap.class, HashMap::new);
        map.put(TreeMap.class, TreeMap::new);
        map.put(ConcurrentHashMap.class, ConcurrentHashMap::new);
        return map;
    }
}
