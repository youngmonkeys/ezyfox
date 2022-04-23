package com.tvd12.ezyfox.entity;

import java.util.Collection;
import java.util.Map;
import java.util.function.BiFunction;

/**
 * Support to transport data between objects.
 *
 * @author tavandung12
 */

public interface EzyObject extends EzyRoObject, Comparable<EzyObject> {

    /**
     * (non-Javadoc).
     *
     * @see java.util.Map#put(java.lang.Object, java.lang.Object)
     */
    <V> V put(Object key, Object value);

    /**
     * (non-Javadoc).
     *
     * @see java.util.Map#putAll(java.util.Map)
     */
    @SuppressWarnings("rawtypes")
    void putAll(Map m);

    /**
     * Remove a key.
     *
     * @param <V> the value type
     * @param key the key
     * @return the removed value
     * @see java.util.Map#remove(java.lang.Object)
     */
    <V> V remove(Object key);

    /**
     * remove a set of keys.
     *
     * @param keys the key set to remove
     */
    @SuppressWarnings("rawtypes")
    void removeAll(Collection keys);

    /**
     * Compute the mapping key.
     *
     * @param <V>  the value type
     * @param key  the key
     * @param func the function
     * @return the value
     * @see java.util.Map#compute(java.lang.Object, java.util.function.BiFunction)
     */
    @SuppressWarnings("rawtypes")
    <V> V compute(Object key, BiFunction func);

    /**
     * (non-Javadoc).
     *
     * @see java.util.Map#clear()
     */
    void clear();

    /**
     * (non-Javadoc).
     *
     * @see com.tvd12.ezyfox.entity.EzyData#duplicate()
     */
    @Override
    EzyObject duplicate();

    /**
     * (non-Javadoc).
     *
     * @see java.lang.Comparable#compareTo(Object)
     */
    @SuppressWarnings("NullableProblems")
    @Override
    default int compareTo(EzyObject o) {
        return 0;
    }
}
