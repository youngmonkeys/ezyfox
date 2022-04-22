package com.tvd12.ezyfox.util;

import java.io.Serializable;
import java.util.Map.Entry;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EzyEntry<K,V> implements Entry<K, V>, Serializable {
    private static final long serialVersionUID = -6562095834319774526L;

    protected K key;
    protected V value;

    public static <K,V> EzyEntry<K, V> of(K key, V value) {
        return new EzyEntry<>(key, value);
    }

    public void setKey(K key) {
        this.key = key;
    }

    @Override
    public V setValue(V value) {
        V old = this.value;
        this.value = value;
        return old;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public boolean equals(Object obj) {
        if(obj == null)
            return false;
        if(obj == this)
            return true;
        if(obj instanceof EzyEntry)
            return key.equals(((EzyEntry)obj).key);
        return false;
    }

    @Override
    public int hashCode() {
        return key.hashCode();
    }

}
