package com.tvd12.ezyfox.concurrent;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class EzyConcurrentHashMapLockProxyProvider extends EzyConcurrentMapLockProxyProvider {

    @Override
    protected ConcurrentMap<Object, EzyLockProxy> newLockMap() {
        return new ConcurrentHashMap<>();
    }

}
