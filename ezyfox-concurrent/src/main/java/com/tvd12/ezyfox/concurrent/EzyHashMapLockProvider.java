package com.tvd12.ezyfox.concurrent;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;

public class EzyHashMapLockProvider extends EzyAbstractMapLockProvider {

    @Override
    protected Map<Object, Lock> newLockMap() {
        return new HashMap<>();
    }
}