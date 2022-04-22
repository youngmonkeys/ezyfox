package com.tvd12.ezyfox.concurrent;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;

public class EzyConcurrentHashMapLockProvider extends EzyAbstractMapLockProvider {

    @Override
    protected Map<Object, Lock> newLockMap() {
        return new ConcurrentHashMap<>();
    }

}
