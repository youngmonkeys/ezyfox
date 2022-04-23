package com.tvd12.ezyfox.entity;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Read only interface of EzyObject.
 *
 * @author tavandung12
 *
 */
public interface EzyRoObject extends EzyData {

    /**
     * (non-Javadoc).
     *
     * @see java.util.Map#size()
     */
    int size();

    /**
     * (non-Javadoc).
     *
     * @see java.util.Map#isEmpty()
     */
    default boolean isEmpty() {
        return size() == 0;
    }

    /**
     * (non-Javadoc).
     *
     * @see java.util.Map#containsKey(java.lang.Object)
     */
    boolean containsKey(Object key);

    /**
     * check contains a collection of keys.
     *
     * @param keys the collection of keys
     * @return contains all or not
     */
    @SuppressWarnings("rawtypes")
    boolean containsKeys(Collection keys);

    /**
     * check contains key and not null value mapped to the key.
     *
     * @param key the key
     * @return true or false
     */
    boolean isNotNullValue(Object key);

    /**
     * Get value by a key.
     *
     * @param <V> type of value
     * @param key key 
     * @return a value 
     */
    <V> V get(Object key);

    /**
     * returns the value to which the specified key is mapped,
     * or null if contains no mapping for the key and cast 
     * the value to specific type.
     *
     * @param <V> the type
     * @param key key
     * @param type type of value
     * @return a value
     */
    <V> V get(Object key, Class<V> type);

    /**
     * returns the value to which the specified key is mapped,
     * or null if contains no mapping for the key and cast
     * the value to specific type.
     *
     * @param <V> the type
     * @param key key
     * @param type type of value
     * @param def the default value
     * @return a value
     */
    default <V> V get(Object key, Class<V> type, V def) {
        return containsKey(key) ? get(key, type) : def;
    }

    /**
     * get but not cast.
     *
     * @param key the key
     * @param type the value type
     * @return object value
     */
    @SuppressWarnings("rawtypes")
    Object getValue(Object key, Class type);

    /**
     * get but not cast.
     *
     * @param key the key
     * @param type the value type
     * @param def the default value
     * @return object value
     */
    @SuppressWarnings("rawtypes")
    default Object getValue(Object key, Class type, Object def) {
        return containsKey(key) ? getValue(key, type) : def;
    }

    /**
     * (non-Javadoc).
     *
     * @see java.util.Map#keySet()
     */
    Set<Object> keySet();

    /**
     * (non-Javadoc).
     *
     * @see java.util.Map#entrySet()
     */
    Set<Entry<Object, Object>> entrySet();

    /**
     * Convert this object to map.
     *
     * @return a map
     */
    @SuppressWarnings("rawtypes")
    Map toMap();

    /**
     * (non-Javadoc).
     *
     * @see com.tvd12.ezyfox.entity.EzyData#duplicate()
     */
    @Override
    EzyRoObject duplicate();

    /**
     * Get first entry.
     *
     * @return the first entry
     */
    default Entry<Object, Object> firstEntry() {
        for (Entry<Object, Object> entry : entrySet()) {
            return entry;
        }
        return null;
    }

    /**
     * Get object's value, return the default value if null.
     *
     * @see java.util.Map#get(java.lang.Object)
     *
     * @param <V> type of value
     * @param key the key 
     * @param def the default value
     * @return the value mapped to key 
     */
    default <V> V getWithDefault(Object key, V def) {
        return containsKey(key) ? get(key) : def;
    }
}
