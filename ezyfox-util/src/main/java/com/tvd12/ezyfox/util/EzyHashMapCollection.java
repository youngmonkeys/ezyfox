package com.tvd12.ezyfox.util;

import java.util.Collection;
import java.util.HashMap;

public abstract class EzyHashMapCollection<K, E, V extends Collection<E>>
    extends HashMap<K, V>
    implements EzyMapCollection<K, E, V> {
    private static final long serialVersionUID = 3535251482476794711L;

    @Override
    public void addItem(K key, E item) {
        V current = get(key);
        if (current == null) {
            current = newCollection();
            put(key, current);
        }
        current.add(item);
    }

    @Override
    public void addItems(K key, Collection<E> items) {
        V current = get(key);
        if (current == null) {
            current = newCollection();
            put(key, current);
        }
        current.addAll(items);
    }

    public V getItems(K key) {
        return containsKey(key) ? get(key) : newCollection();
    }

    protected abstract V newCollection();

    @Override
    public void deepClear() {
        for (V value : values()) {
            value.clear();
        }
        this.clear();
    }
}
