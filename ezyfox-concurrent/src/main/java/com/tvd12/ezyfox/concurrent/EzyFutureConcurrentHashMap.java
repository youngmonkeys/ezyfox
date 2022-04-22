package com.tvd12.ezyfox.concurrent;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class EzyFutureConcurrentHashMap<K> extends EzyFutureAbstractMap<K> {

    @Override
    protected Map<K, EzyFuture> newFutureMap() {
        return new ConcurrentHashMap<>();
    }
}