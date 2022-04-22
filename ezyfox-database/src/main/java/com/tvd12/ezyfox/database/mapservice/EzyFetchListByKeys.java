package com.tvd12.ezyfox.database.mapservice;

import java.util.List;

public interface EzyFetchListByKeys<K,V> {

    List<V> getListByIds(Iterable<K> keys);
    }