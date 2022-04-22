package com.tvd12.ezyfox.concurrent;

import java.util.HashMap;
import java.util.Map;

public class EzyFutureHashMap<K> extends EzyFutureAbstractMap<K> {

    @Override
    protected Map<K, EzyFuture> newFutureMap() {
        return new HashMap<>();
    }
}