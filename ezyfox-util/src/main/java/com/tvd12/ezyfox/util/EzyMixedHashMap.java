package com.tvd12.ezyfox.util;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class EzyMixedHashMap<V> implements EzyMixedMap<V> {

    protected final Map<Object, EzyMixedKeyMap<V>> multiKeyMaps;

    public EzyMixedHashMap() {
        this.multiKeyMaps = new HashMap<>();
    }

    public V get(EzyMixedKey key) {
        Object multiKeyType = key.getType();
        EzyMixedKeyMap<V> multiKeyMap = multiKeyMaps.get(multiKeyType);
        if(multiKeyMap == null)
            return null;
        V value = multiKeyMap.get(key);
        return value;
    }

    @Override
    public V remove(EzyMixedKey key) {
        Object multiKeyType = key.getType();
        EzyMixedKeyMap<V> multiKeyMap = multiKeyMaps.get(multiKeyType);
        if(multiKeyMap == null)
            return null;
        V value = multiKeyMap.remove(key);
        return value;
    }

    public V computeIfAbsent(EzyMixedKey key, Supplier<V> valueSupplier) {
        Object multiKeyType = key.getType();
        EzyMixedKeyMap<V> multiKeyMap = multiKeyMaps.get(multiKeyType);
        if(multiKeyMap == null) {
            multiKeyMap = new EzyMixedKeyMap<>();
            multiKeyMaps.put(multiKeyType, multiKeyMap);
        }
        V value = multiKeyMap.computeIfAbsent(key, valueSupplier);
        return value;
    }

    @Override
    public int size() {
        int size = multiKeyMaps.size();
        return size;
    }}