package com.tvd12.ezyfox.database.mapservice;

import java.util.Map;

public interface EzySetMapService<K,V> {

    void set(Map<K, V> map);
}
