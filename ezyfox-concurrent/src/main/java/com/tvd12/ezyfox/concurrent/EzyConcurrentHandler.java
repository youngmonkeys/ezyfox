package com.tvd12.ezyfox.concurrent;

import java.util.concurrent.locks.Lock;
import java.util.function.Supplier;

public class EzyConcurrentHandler {

    private EzyConcurrentMapLockProxyProvider lockProvider;

    public EzyConcurrentHandler(EzyConcurrentMapLockProxyProvider lockProvider) {
        this.lockProvider = lockProvider;
    }

    public <K> void doInLock(K key, Runnable task) throws InterruptedException {
        Lock lock = lockProvider.provideLock(key);
        lock.lockInterruptibly();
        try {
            task.run();
        } finally {
            lock.unlock();
        }
    }

    public <K, R> R getInLock(K key, Supplier<R> task) throws InterruptedException {
        Lock lock = lockProvider.provideLock(key);
        lock.lockInterruptibly();
        try {
            return task.get();
        } finally {
            lock.unlock();
        }
    }

}
