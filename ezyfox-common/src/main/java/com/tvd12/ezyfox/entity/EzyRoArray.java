package com.tvd12.ezyfox.entity;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;

public interface EzyRoArray extends EzyData {

    /**
     * Get value by index.
     *
     * @param <T>   the value type
     * @param index the index
     * @return the value
     */
    <T> T get(int index);

    /**
     * Get and cast value by index.
     *
     * @param <T>   the value
     * @param index the index
     * @param type  the value type
     * @return the value
     */
    <T> T get(int index, Class<T> type);

    /**
     * Get and cast value by index.
     *
     * @param <T>   the value type
     * @param index the index
     * @param type  the value type
     * @param def   the default value
     * @return the value
     */
    default <T> T get(int index, Class<T> type, T def) {
        return size() > index ? get(index, type) : def;
    }

    /**
     * Get value by index but not cast.
     *
     * @param index the index
     * @param type  the value type
     * @return the object value
     */
    @SuppressWarnings("rawtypes")
    Object getValue(int index, Class type);

    /**
     * Get value by index but not cast.
     *
     * @param index the index
     * @param type  the value type
     * @param def   the default value
     * @return the object value
     */
    @SuppressWarnings("rawtypes")
    default Object getValue(int index, Class type, Object def) {
        return size() > index ? getValue(index, type) : def;
    }

    /**
     * Check if value in the index is not null.
     *
     * @param index the index
     * @return true or false
     */
    boolean isNotNullValue(int index);

    /**
     * Check if contains value or not.
     *
     * @param value the value
     * @return contains or not
     */
    boolean contains(Object value);

    /**
     * Check if contains all a collection of values not.
     *
     * @param values the collection of values
     * @return contains all or not
     */
    @SuppressWarnings("rawtypes")
    boolean containsAll(Collection values);

    /**
     * Get new array.
     *
     * @param fromIndex the from index
     * @param toIndex   the to index
     * @return the new array
     */
    EzyRoArray sub(int fromIndex, int toIndex);

    /**
     * Get size of an array.
     *
     * @return the size of array
     */
    int size();

    /**
     * Check an array is empty or not.
     *
     * @return is empty or not
     */
    default boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Convert an array to a list.
     *
     * @param <T> type of value
     * @return covert this array to list
     */
    <T> List<T> toList();

    /**
     * Convert an anrray to a list with specific item type.
     *
     * @param <T>  type of value
     * @param type the item type
     * @return covert this array to list
     */
    <T> List<T> toList(Class<T> type);

    /**
     * Convert a EzyArray to java array.
     *
     * @param <T>  the array type
     * @param <A>  the return type
     * @param type type array type
     * @return the array value
     */
    <T, A> A toArray(Class<T> type);

    /**
     * (non-Javadoc).
     *
     * @see com.tvd12.ezyfox.entity.EzyData#duplicate()
     */
    @Override
    EzyRoArray duplicate();

    /**
     * sort this array.
     *
     * @param comparator the comparator
     */
    @SuppressWarnings("rawtypes")
    default void sort(Comparator comparator) {
    }

    /**
     * Get first value.
     *
     * @param <T> the value type
     * @param def the default value
     * @return the first value
     */
    default <T> T first(T def) {
        return getWithDefault(0, def);
    }

    /**
     * Get first value.
     *
     * @param <T>  the value type
     * @param type the value class
     * @param def  the default value
     * @return the first value
     */
    default <T> T first(Class<T> type, T def) {
        return get(0, type, def);
    }

    /**
     * Get value by index.
     *
     * @param <T>   the value type
     * @param index the index
     * @param def   the default value
     * @return the value
     */
    default <T> T getWithDefault(int index, T def) {
        return size() > index ? get(index) : def;
    }
}
