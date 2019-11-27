package com.tvd12.ezyfox.entity;

import java.util.Collection;
import java.util.Map;
import java.util.function.BiFunction;

import com.tvd12.ezyfox.entity.EzyObject;
import com.tvd12.ezyfox.entity.EzyRoObject;

/**
 * Support to transport data between objects
 * 
 * @author tavandung12
 *
 */

public interface EzyObject extends EzyRoObject, Comparable<EzyObject> {

	/**
	 * @see java.util.Map#put(java.lang.Object, java.lang.Object)
	 * 
	 * @param <V> the value type
	 * @param key the key
	 * @param value the value
	 * @return the old value
	 */
	<V> V put(Object key, Object value);
	
    /**
     * @see java.util.Map#putAll(java.util.Map)
     * 
     * @param m the map value
     */
    @SuppressWarnings("rawtypes")
	void putAll(Map m);
    
    /**
     * @see java.util.Map#remove(java.lang.Object)
     * 
     * @param <V> the value type
     * @param key the key
     * @return the removed value
     */
    <V> V remove(Object key);
    
    /**
     * remove a set of keys
     * 
     * @param keys the key set to remove
     */
    @SuppressWarnings("rawtypes")
	void removeAll(Collection keys);
    
    /**
     * @see java.util.Map#compute(java.lang.Object, java.util.function.BiFunction)
     * 
     * @param <V> the value type
     * @param key the key
     * @param func the function
     * @return the value
     */
    @SuppressWarnings("rawtypes")
	<V> V compute(Object key, BiFunction func);
    
    /**
     * @see java.util.Map#clear()
     */
    void clear();
    
    /**
     * @see com.tvd12.ezyfox.entity.EzyData#duplicate()
     */
    @Override
    EzyObject duplicate();
    
    /**
     * @see java.lang.Comparable#compareTo(Object)
     */
    @Override
    default int compareTo(EzyObject o) {
    	return 0;
    }

}
