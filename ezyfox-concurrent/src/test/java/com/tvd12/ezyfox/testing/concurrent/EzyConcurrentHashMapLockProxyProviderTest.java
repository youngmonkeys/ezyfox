package com.tvd12.ezyfox.testing.concurrent;

import com.tvd12.ezyfox.concurrent.EzyConcurrentHashMapLockProxyProvider;
import com.tvd12.ezyfox.concurrent.EzyConcurrentMapLockProxyProvider;
import org.testng.annotations.Test;

import java.util.Objects;
import java.util.concurrent.locks.Lock;
import java.util.function.Supplier;

public class EzyConcurrentHashMapLockProxyProviderTest {

    @Test
    public void test() throws InterruptedException {
        final EzyConcurrentMapLockProxyProvider lockProvider = new EzyConcurrentHashMapLockProxyProvider();
        final String key = "accountID";
        final Supplier<Runnable> task = () -> {
          return () -> {
              Lock lock = lockProvider.provideLock(key);
              assert lockProvider.getLock(key).equals(lock);
              lockProvider.removeLock(key);
          };
        };

        Thread t1 = new Thread(task.get());
        Thread t2 = new Thread(task.get());
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        assert Objects.isNull(lockProvider.getLock(key));
    }

}
