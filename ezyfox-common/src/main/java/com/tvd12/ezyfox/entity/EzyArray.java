package com.tvd12.ezyfox.entity;

import java.util.Collection;
import java.util.Iterator;
import java.util.function.Consumer;

import com.tvd12.ezyfox.entity.EzyArray;
import com.tvd12.ezyfox.entity.EzyRoArray;

public interface EzyArray extends EzyRoArray, Comparable<EzyArray> {
	
	/**
	 * Add item to array
	 * 
	 * @param item the item to add
	 */
	void add(Object item);
	
	/**
	 * Add items to array
	 * 
	 * @param items the items to add
	 */
	void add(Object... items);
	
	/**
	 * Add items to array
	 * 
	 * @param items the items to add
	 */
	@SuppressWarnings("rawtypes")
	void add(Collection items);
	
	/**
	 * Set value at the index
	 * 
	 * @param <T> the value type
	 * @param index the index
	 * @param item the item to set
	 * @return old value
	 */
	<T> T set(int index, Object item);
	
	/**
	 * Remove value at the index
	 * 
	 * @param <T> the value type
	 * @param index the index
	 * @return the removed value
	 */
	<T> T remove(int index);
	
	/*
	 * (non-Javadoc)
	 * @see com.tvd12.ezyfox.entity.EzyRoArray#sub(int, int)
	 */
	@Override
	EzyArray sub(int fromIndex, int toIndex);
	
	/**
	 * For each
	 * 
	 * @param action the action
	 */
	void forEach(Consumer<Object> action);
	
	/**
	 * @see java.util.List#iterator()
	 * 
	 * @return the iterator
	 */
	Iterator<Object> iterator();
	
	/**
     * @see com.tvd12.ezyfox.entity.EzyData#duplicate()
     */
    @Override
    EzyArray duplicate();
    
    /**
	 * @see java.util.List#clear()
	 * 
	 */
    default void clear() {}

    /**
     * @see java.lang.Comparable#compareTo(Object)
     */
    @Override
    default int compareTo(EzyArray o) {
    	return 0;
    }
	
}
