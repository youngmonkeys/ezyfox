package com.tvd12.ezyfox.concurrent;

import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.Lock;

public abstract class EzyConcurrentMapLockProxyProvider implements EzyMapLockProvider {

    protected final ConcurrentMap<Object, EzyLockProxy> locks;

    public EzyConcurrentMapLockProxyProvider() {
        this.locks = newLockMap();
    }

    protected abstract ConcurrentMap<Object, EzyLockProxy> newLockMap();


    @Override
    public Lock provideLock(Object key) {
        return locks.computeIfAbsent(key, k -> {
            EzyLockProxy lock = new EzyLockProxy();
            lock.retain();
            return lock;
        });
    }

    @Override
    public Lock getLock(Object key) {
        return locks.get(key);
    }

    @Override
    public void removeLock(Object key) {
        final EzyLockProxy lock = locks.get(key);
        if (Objects.nonNull(lock)) {
            synchronized (lock) {
                lock.release();
                if (lock.isReleasable()) {
                    locks.remove(key);
                }
            }
        }
    }

    @Override
    public void removeLocks(Set<?> keys) {
        keys.forEach(this::removeLock);
    }

    @Override
    public int size() {
        return locks.size();
    }
}
