package com.tvd12.ezyfox.database.mapservice;

public interface EzyRemoveByKeyService<K,V> {

    V remove(K key);
    }