package com.tvd12.ezyfox.database.mapservice;

public interface EzyPutService<K,V> extends 
        EzyPutKeyValueService<K, V>,
        EzyPutValueService<V>,
        EzyPutValuesService<V>,
        EzyPutMapService<K, V> {}
