package com.tvd12.ezyfox.concurrent;

import java.util.Map;
import java.util.TreeMap;

public class EzyTreeMapLockProxyProvider extends EzyMapLockProxyProvider {

    @Override
    protected Map<Object, EzyLockProxy> newLockMap() {
        return new TreeMap<>();
    }
}