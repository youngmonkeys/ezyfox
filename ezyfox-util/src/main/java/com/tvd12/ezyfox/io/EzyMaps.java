package com.tvd12.ezyfox.io;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.tvd12.ezyfox.collect.Lists;
import com.tvd12.ezyfox.util.EzyObjects;

public final class EzyMaps {

	private EzyMaps() {
	}
	
	@SuppressWarnings("rawtypes")
	public static boolean isEmpty(Map map) {
		if(map == null || map.isEmpty())
			return true;
		return false;
	}
	
	// =============================================
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> T getValue(Map map, Class<?> type) {
		if(type == Object.class)
			return null;
		Object answer = map.get(type);
		if(answer == null)
			answer = getValueOfInterfaces(map, type);
		if(answer == null && type.getSuperclass() != null)
			answer = getValueOfSuper(map, type);
		return (T)answer;
	}
	
	@SuppressWarnings("rawtypes")
	private static Object getValueOfSuper(Map map, Class<?> type) {
		return getValue(map, type.getSuperclass());
	}
	
	@SuppressWarnings("rawtypes")
	private static Object getValueOfInterfaces(Map map, Class<?> type) {
		Object answer = null;
		for(Class<?> clazz : type.getInterfaces())
			if((answer = getValue(map, clazz)) != null)
				return answer;
		return answer;
	}
	
	// =============================================
	
	// =============================================
	public static <K,V> List<V> getValueList(Map<K, V> map) {
		return new ArrayList<>(map.values());
	}
	
	public static <K,V> Set<V> getValueSet(Map<K, V> map) {
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
	public static <K,V> Map<K,V> newHashMap(
			Collection<V> coll, Function<V, K> keyGentor) {
		Map<K, V> map = new HashMap<>();
		for(V v : coll)
			map.put(keyGentor.apply(v), v);
		return map;
	}
    
    public static <K,V,K1> Map<K1,V> newHashMapNewKeys(
    		Map<K,V> origin, Function<K,K1> keyGentor) {
        return newHashMap(origin, keyGentor, (v) -> v);
    }
    
    public static <K,V,V1> Map<K,V1> newHashMapNewValues(
            Map<K,V> origin, Function<V,V1> valueGentor) {
        return newHashMap(origin, (k) -> k, valueGentor);
    }
    
    public static <K,V,K1,V1> Map<K1,V1> newHashMap(Map<K,V> origin, 
            Function<K,K1> keyGentor, Function<V,V1> valueGentor) {
        Map<K1, V1> map = new HashMap<>();
        for(K k : origin.keySet())
            map.put(keyGentor.apply(k), valueGentor.apply(origin.get(k)));
        return map;
    }
    
    public static <K,V> Map<K, V> newHashMap(K key, V value) {
		return newMap(key, value, new HashMap<>());
    }
    
    public static <K,V,M extends Map<K, V>> M newMap(K key, V value, M map) {
		map.put(key, value);
		return map;
    }

    //=================================================================
    
    public static <K,V> Map<K,V> getValues(Map<K, V> map, Collection<K> keys) {
        Map<K, V> answer = new HashMap<>();
        for(K k : keys)
        	if(map.containsKey(k))
        		answer.put(k, map.get(k));
        return answer;
    }
    
    public static <K,V> List<V> getValues(Map<K, V> map, Predicate<V> predicate) {
        return map.values().stream()
        		.filter(predicate)
        		.collect(Collectors.toList());
    }
    
    public static <K,V> V putIfAbsent(Map<K, V> map, K key, V value) {
		if(map == null)
			throw new NullPointerException("map is null");
		synchronized (map) {
			V v = map.computeIfAbsent(key, (k) -> value);
			return v;
		}
    }
    
	@SuppressWarnings("unchecked")
	public static <K,E> void addItemsToSet(Map<K, Set<E>> map, K key, E... items) {
		addItemsToSet(map, key, Lists.newArrayList(items));
    }
    
    @SuppressWarnings("unchecked")
	public static <K,E> void addItemsToList(Map<K, List<E>> map, K key, E... items) {
		addItemsToList(map, key, Lists.newArrayList(items));
    }
    
    public static <K,E> void addItemsToSet(Map<K, Set<E>> map, K key, Collection<E> items) {
    	synchronized (map) {
			Set<E> set = map.get(key);
			if(set == null) {
				set = new HashSet<>();
				map.put(key, set);
			}
			set.addAll(items);
		}
    }
    
    public static <K,E> void addItemsToList(Map<K, List<E>> map, K key, Collection<E> items) {
    	synchronized (map) {
			List<E> set = map.get(key);
			if(set == null) {
				set = new ArrayList<>();
				map.put(key, set);
			}
			set.addAll(items);
		}
    }
    
    @SuppressWarnings("unchecked")
	public static <K,E> void removeItems(Map<K, ? extends Collection<E>> map, K key, E... items) {
		if(map.containsKey(key)) {
			Collection<E> collection = map.get(key);
			for(E item : items)
				collection.remove(item);
		}
    }
    
    public static <K,E> void removeItems(Map<K, ? extends Collection<E>> map, K key, Collection<E> items) {
		if(map.containsKey(key)) {
			Collection<E> collection = map.get(key);
			collection.removeAll(items);
		}
    }
    
	// =============================================
    @SuppressWarnings("rawtypes")
	public static boolean containsAll(Map map1, Map map2) {
		for(Object key : map2.keySet()) {
    		if(!map1.containsKey(key))
    			return false;
    		if(!EzyObjects.equals(map2.get(key), map1.get(key)))
    			return false;
		}
		return true;
    }
}
