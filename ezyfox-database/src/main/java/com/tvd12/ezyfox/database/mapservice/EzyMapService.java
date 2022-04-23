package com.tvd12.ezyfox.database.mapservice;

public interface EzyMapService<K,V> extends
        EzySetService<K, V>,
        EzyPutService<K, V>,
        EzyFetchService<K, V>,
        EzyRemoveService<K, V>,
        EzyCheckService<K, V>,
        EzyFetchSizeService,
        EzyClearService {
}
