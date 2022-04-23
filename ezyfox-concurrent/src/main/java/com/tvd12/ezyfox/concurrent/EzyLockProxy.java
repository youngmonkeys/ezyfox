package com.tvd12.ezyfox.concurrent;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Supplier;

import com.tvd12.ezyfox.util.EzyRef;

public class EzyLockProxy extends EzyRef<Lock> implements Lock {

    public static final Supplier<EzyLockProxy> SUPPLIER = () -> new EzyLockProxy();

    public EzyLockProxy() {
        this(new ReentrantLock());
    }

    public EzyLockProxy(Lock value) {
        super(value);
    }

    @Override
    public void lock() {
        value.lock();
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        value.lockInterruptibly();
    }

    @Override
    public boolean tryLock() {
        return value.tryLock();
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return value.tryLock(time, unit);
    }

    @Override
    public void unlock() {
        value.unlock();
    }

    @Override
    public Condition newCondition() {
        return value.newCondition();
    }
}
