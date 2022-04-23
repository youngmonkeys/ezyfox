package com.tvd12.ezyfox.concurrent;

import com.tvd12.ezyfox.function.EzyFunctions;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Lock;

public abstract class EzyAbstractMapLockProvider implements EzyMapLockProvider {

    protected final Map<Object, Lock> locks;

    public EzyAbstractMapLockProvider() {
        this.locks = newLockMap();
    }

    protected abstract Map<Object, Lock> newLockMap();

    @Override
    public Lock provideLock(Object key) {
        return locks.computeIfAbsent(key, EzyFunctions.NEW_REENTRANT_LOCK_FUNC);
    }

    @Override
    public Lock getLock(Object key) {
        return locks.get(key);
    }

    @Override
    public void removeLock(Object key) {
        this.locks.remove(key);
    }

    @Override
    public void removeLocks(Set<?> keys) {
        for (Object key : keys) {
            this.locks.remove(key);
        }
    }

    @Override
    public int size() {
        return locks.size();
    }
}
