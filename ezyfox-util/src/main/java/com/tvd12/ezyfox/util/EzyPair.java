package com.tvd12.ezyfox.util;

import lombok.Getter;

@Getter
public class EzyPair<K, V> {

    protected final K key;
    protected final V value;

    public EzyPair(K key) {
        this(key, null);
    }

    public EzyPair(K key, V value) {
        if (key == null) {
            throw new NullPointerException("key can't be null");
        }
        this.key = key;
        this.value = value;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj instanceof EzyPair) {
            return key.equals(((EzyPair) obj).key);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return key.hashCode();
    }

    @Override
    public String toString() {
        return "(" + key + ":" + value + ")";
    }
}
