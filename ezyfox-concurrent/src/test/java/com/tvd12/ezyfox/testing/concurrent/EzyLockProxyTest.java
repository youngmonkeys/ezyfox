package com.tvd12.ezyfox.testing.concurrent;

import com.tvd12.ezyfox.concurrent.EzyLockProxy;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class EzyLockProxyTest {

    @Test
    public void test() {
        EzyLockProxy lock = new EzyLockProxy();
        lock.lock();
        try {
            System.out.println("lock");
        } finally {
            lock.unlock();
        }
        System.out.println(lock.tryLock());
        System.out.println(lock.tryLock());
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
        System.out.println(lock.newCondition());
    }
}
