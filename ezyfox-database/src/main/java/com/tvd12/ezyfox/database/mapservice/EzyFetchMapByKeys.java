package com.tvd12.ezyfox.database.mapservice;

import java.util.Map;

public interface EzyFetchMapByKeys<K,V> {

    Map<K, V> getMapByIds(Iterable<K> keys);
}
