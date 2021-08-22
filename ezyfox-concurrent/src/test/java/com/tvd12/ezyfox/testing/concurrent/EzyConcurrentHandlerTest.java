package com.tvd12.ezyfox.testing.concurrent;

import com.tvd12.ezyfox.concurrent.EzyConcurrentHandler;
import com.tvd12.ezyfox.concurrent.EzyConcurrentHashMapLockProxyProvider;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;
import java.util.stream.IntStream;

public class EzyConcurrentHandlerTest {

    @Test
    public void test_cacheMissStorm1() throws InterruptedException {
        final Map<String, Object> foolCache = new HashMap<>();
        final AtomicInteger cachedHitCounter = new AtomicInteger(0);

        final EzyConcurrentHashMapLockProxyProvider providerBean = new EzyConcurrentHashMapLockProxyProvider();
        final EzyConcurrentHandler concurrentHandler = new EzyConcurrentHandler(providerBean);
        final String userID = "1";
        final int threadNum = 1000;
        final int coreSize = 20;
        final ExecutorService executorService = Executors.newFixedThreadPool(coreSize);
        final CountDownLatch waiter = new CountDownLatch(threadNum);

        Supplier<Runnable> task = () -> {
            return () -> {
                try {
                    concurrentHandler.doInLock(userID, () -> {
                        if (!foolCache.containsKey(userID)) {
                            foolCache.put(userID, new Object());
                            cachedHitCounter.incrementAndGet();
                        }
                        waiter.countDown();
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            };
        };
        IntStream.range(0, threadNum).forEach(item -> {
            executorService.execute(task.get());
        });
        waiter.await();
        assert cachedHitCounter.get() == 1;
    }

    @Test
    public void test_cacheMissStorm2() throws InterruptedException {
        final Map<String, Object> foolCache = new ConcurrentHashMap<>();
        final AtomicInteger cachedHitCounter = new AtomicInteger(0);

        final EzyConcurrentHashMapLockProxyProvider providerBean = new EzyConcurrentHashMapLockProxyProvider();
        final EzyConcurrentHandler concurrentHandler = new EzyConcurrentHandler(providerBean);
        final String userID = "1";
        final int threadNum = 1000;
        final int coreSize = 20;
        final ExecutorService executorService = Executors.newFixedThreadPool(coreSize);
        final CountDownLatch waiter = new CountDownLatch(threadNum);

        Supplier<Runnable> task = () -> {
            return () -> {
                try {
                    concurrentHandler.getInLock(userID, () -> {
                        if (!foolCache.containsKey(userID)) {
                            foolCache.put(userID, new Object());
                            cachedHitCounter.incrementAndGet();
                        }
                        waiter.countDown();
                        return 1;
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            };
        };
        IntStream.range(0, threadNum).forEach(item -> {
            executorService.execute(task.get());
        });
        waiter.await();
        assert cachedHitCounter.get() == 1;
    }

}
