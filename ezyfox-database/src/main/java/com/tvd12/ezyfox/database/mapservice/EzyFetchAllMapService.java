package com.tvd12.ezyfox.database.mapservice;

import java.util.Map;

public interface EzyFetchAllMapService<K, V> {

    Map<K, V> getAllMap();
}
