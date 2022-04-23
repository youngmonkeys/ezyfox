package com.tvd12.ezyfox.util;

import java.util.Map;
import java.util.function.Supplier;

public interface EzyMixedMap<V> {

    V get(EzyMixedKey key);

    V remove(EzyMixedKey key);

    V computeIfAbsent(EzyMixedKey key, Supplier<V> valueSupplier);

    int size();

    interface EzyMixedKey {

        Map<Object, Object> getKeys();

        default Object getType() {
            return getClass();
        }

    }
}
