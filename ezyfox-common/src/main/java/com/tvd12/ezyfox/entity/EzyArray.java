package com.tvd12.ezyfox.entity;

import java.util.Collection;
import java.util.Iterator;
import java.util.function.Consumer;

public interface EzyArray extends EzyRoArray, Comparable<EzyArray> {

    /**
     * Add item to array.
     *
     * @param item the item to add
     */
    void add(Object item);

    /**
     * Add items to array.
     *
     * @param items the items to add
     */
    void add(Object... items);

    /**
     * Add items to array.
     *
     * @param items the items to add
     */
    @SuppressWarnings("rawtypes")
    void add(Collection items);

    /**
     * Set value at the index.
     *
     * @param <T>   the value type
     * @param index the index
     * @param item  the item to set
     * @return old value
     */
    <T> T set(int index, Object item);

    /**
     * Remove value at the index.
     *
     * @param <T>   the value type
     * @param index the index
     * @return the removed value
     */
    <T> T remove(int index);

    @Override
    EzyArray sub(int fromIndex, int toIndex);

    void forEach(Consumer<Object> action);

    Iterator<Object> iterator();

    @Override
    EzyArray duplicate();

    default void clear() {}

    @SuppressWarnings("NullableProblems")
    @Override
    default int compareTo(EzyArray o) {
        return 0;
    }
}
