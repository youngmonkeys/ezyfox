package com.tvd12.ezyfox.testing.concurrent;

import java.util.concurrent.TimeUnit;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.concurrent.EzyLockProxy;

public class EzyLockProxyTest {

    @Test
    public void test() {
        EzyLockProxy lock = new EzyLockProxy();
        lock.lock();
        try {
            System.out.println("lock");
        }
        finally {
            lock.unlock();
        }
        lock.tryLock();
        lock.tryLock();
        lock.unlock();
        try {
            assert lock.tryLock(3, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            lock.lockInterruptibly();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        lock.newCondition();
    }
}
