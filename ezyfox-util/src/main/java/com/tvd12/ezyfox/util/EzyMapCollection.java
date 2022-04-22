package com.tvd12.ezyfox.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

public interface EzyMapCollection<K,E,V extends Collection<E>> extends Map<K,V> {

    void addItem(K key, E item);

    void addItems(K key, Collection<E> items);

    void deepClear();

    @SuppressWarnings("unchecked")
    default void addItems(K key, E... items) {
        addItems(key, Arrays.asList(items));
    }

    @SuppressWarnings("unchecked")
    default void removeItems(K key, E... items) {
        removeItems(key, Arrays.asList(items));
    }

    default void removeItems(K key, Collection<E> items) {
        Collection<E> coll = get(key);
        if(coll != null)
            coll.removeAll(items);
    }

}
