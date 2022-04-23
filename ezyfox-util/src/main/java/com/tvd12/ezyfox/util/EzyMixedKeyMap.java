package com.tvd12.ezyfox.util;

import com.tvd12.ezyfox.util.EzyMixedMap.EzyMixedKey;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

public class EzyMixedKeyMap<V> {

    protected final Map<Object, Map<Object, V>> maps;

    public EzyMixedKeyMap() {
        this.maps = new HashMap<>();
    }

    public V get(EzyMixedKey key) {
        Map<Object, Object> keys = key.getKeys();
        Set<Object> keyTypeSet = keys.keySet();
        V value = null;
        for (Object keyType : keyTypeSet) {
            Map<Object, V> map = maps.get(keyType);
            if (map != null) {
                Object keyValue = keys.get(keyType);
                V v = map.get(keyValue);
                if (value == null) {
                    value = v;
                }
                if (value != v) {
                    throw new IllegalArgumentException("too many value mapping to key: " + key);
                }
            }
        }
        return value;
    }

    public V remove(EzyMixedKey key) {
        Map<Object, Object> keys = key.getKeys();
        Set<Object> keyTypeSet = keys.keySet();
        V value = null;
        for (Object keyType : keyTypeSet) {
            Map<Object, V> map = maps.get(keyType);
            if (map != null) {
                Object keyValue = keys.get(keyType);
                V v = map.get(keyValue);
                if (value == null) {
                    value = v;
                }
                if (value != v) {
                    throw new IllegalArgumentException("too many value mapping to key: " + key);
                }
            }
        }
        for (Object keyType : keyTypeSet) {
            Map<Object, V> map = maps.get(keyType);
            if (map != null) {
                Object keyValue = keys.get(keyType);
                map.remove(keyValue);
            }
        }
        return value;
    }

    public V computeIfAbsent(EzyMixedKey key, Supplier<V> valueSupplier) {
        Map<Object, Object> keys = key.getKeys();
        Set<Object> keyTypeSet = keys.keySet();
        V value = null;
        for (Object keyType : keyTypeSet) {
            Map<Object, V> map = maps.get(keyType);
            if (map == null) {
                map = new HashMap<>();
                maps.put(keyType, map);
            } else {
                Object keyValue = keys.get(keyType);
                value = map.get(keyValue);
                if (value != null) {
                    break;
                }
            }
        }
        if (value == null) {
            value = valueSupplier.get();
        }
        for (Object keyType : keyTypeSet) {
            Object keyValue = keys.get(keyType);
            Map<Object, V> map = maps.get(keyType);
            map.put(keyValue, value);
        }
        return value;
    }
}
