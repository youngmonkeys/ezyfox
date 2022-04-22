package com.tvd12.ezyfox.database.mapservice;

public interface EzyPutKeyValueService<K,V> {

    V put(K key, V value);
}