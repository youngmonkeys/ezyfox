package com.tvd12.ezyfox.io;

import com.tvd12.ezyfox.util.EzyObjects;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public final class EzyMaps {

    private EzyMaps() {
    }

    @SuppressWarnings("rawtypes")
    public static boolean isEmpty(Map map) {
        return map == null || map.isEmpty();
    }

    // =============================================
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static <T> T getValue(Map map, Class<?> type) {
        if (type == Object.class) {
            return null;
        }
        Object answer = map.get(type);
        if (answer == null) {
            answer = getValueOfInterfaces(map, type);
        }
        if (answer == null && type.getSuperclass() != null) {
            answer = getValueOfSuper(map, type);
        }
        return (T) answer;
    }

    @SuppressWarnings("rawtypes")
    private static Object getValueOfSuper(Map map, Class<?> type) {
        return getValue(map, type.getSuperclass());
    }

    @SuppressWarnings("rawtypes")
    private static Object getValueOfInterfaces(Map map, Class<?> type) {
        Object answer;
        for (Class<?> clazz : type.getInterfaces()) {
            if ((answer = getValue(map, clazz)) != null) {
                return answer;
            }
        }
        return null;
    }

    // =============================================

    // =============================================
    public static <K, V> List<V> getValueList(Map<K, V> map) {
        return new ArrayList<>(map.values());
    }

    public static <K, V> Set<V> getValueSet(Map<K, V> map) {
        return new HashSet<>(map.values());
    }

    public static <K, V> List<V> flattenValues(Map<K, List<V>> map) {
        return map
            .values()
            .stream()
            .flatMap(Collection::stream)
            .collect(Collectors.toList());
    }
    // =============================================

    // =============================================
    public static <K, V> Map<K, V> newHashMap(
        Collection<V> coll, Function<V, K> keyGenerator) {
        Map<K, V> map = new HashMap<>();
        for (V v : coll) {
            map.put(keyGenerator.apply(v), v);
        }
        return map;
    }

    public static <K, V, K1, V1> Map<K1, V1> newHashMap(
        Map<K, V> origin,
        Function<K, K1> keyGenerator, Function<V, V1> valueGenerator
    ) {
        Map<K1, V1> map = new HashMap<>();
        for (K k : origin.keySet()) {
            map.put(keyGenerator.apply(k), valueGenerator.apply(origin.get(k)));
        }
        return map;
    }

    public static <K, V> Map<K, V> newHashMap(K key, V value) {
        return newMap(key, value, new HashMap<>());
    }

    public static <K, V, K1> Map<K1, V> newHashMapNewKeys(
        Map<K, V> origin, Function<K, K1> keyGenerator) {
        return newHashMap(origin, keyGenerator, (v) -> v);
    }

    public static <K, V, V1> Map<K, V1> newHashMapNewValues(
        Map<K, V> origin, Function<V, V1> valueGenerator) {
        return newHashMap(origin, (k) -> k, valueGenerator);
    }

    public static <K, V, M extends Map<K, V>> M newMap(K key, V value, M map) {
        map.put(key, value);
        return map;
    }

    //=================================================================

    public static <K, V> Map<K, V> getValues(Map<K, V> map, Collection<K> keys) {
        Map<K, V> answer = new HashMap<>();
        for (K k : keys) {
            if (map.containsKey(k)) {
                answer.put(k, map.get(k));
            }
        }
        return answer;
    }

    public static <K, V> List<V> getValues(Map<K, V> map, Predicate<V> predicate) {
        return map.values().stream()
            .filter(predicate)
            .collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    public static <K, E> void removeItems(
        Map<K, ? extends Collection<E>> map,
        K key,
        E... items
    ) {
        map.computeIfPresent(key, (k, v) -> {
            for (E item : items) {
                v.remove(item);
            }
            return v;
        });
    }

    public static <K, E> void removeItems(
        Map<K, ? extends Collection<E>> map,
        K key,
        Collection<E> items
    ) {
        map.computeIfPresent(key, (k, v) -> {
            v.removeAll(items);
            return v;
        });
    }

    // =============================================
    @SuppressWarnings("rawtypes")
    public static boolean containsAll(Map map1, Map map2) {
        for (Object key : map2.keySet()) {
            if (!map1.containsKey(key)) {
                return false;
            }
            if (!EzyObjects.equals(map2.get(key), map1.get(key))) {
                return false;
            }
        }
        return true;
    }
}
