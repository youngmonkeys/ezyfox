package com.tvd12.ezyfox.testing.concurrent;

import com.tvd12.ezyfox.concurrent.EzyExecutors;
import com.tvd12.ezyfox.function.EzyExceptionApply;
import org.testng.annotations.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class EzyErrorScheduledExecutorServiceTest {

    protected ScheduledExecutorService service;

    @SuppressWarnings("ALL")
    @Test
    public void test() {
        service = EzyExecutors.newErrorScheduledExecutor("hello");
        callFunc(ExecutorService::shutdown);
        callFunc(ExecutorService::shutdownNow);
        callFunc(ExecutorService::isShutdown);
        callFunc(ExecutorService::isTerminated);
        callFunc(s -> s.awaitTermination(1, TimeUnit.SECONDS));
        callFunc(s -> s.submit(new Callable<Object>() {

            @Override
            public Object call() throws Exception {
                return null;
            }

        }));
        callFunc(s -> s.submit(null, null));
        callFunc(s -> s.submit(new Runnable() {
            @Override
            public void run() {

            }
        }));
        callFunc(s -> s.invokeAll(null));
        callFunc(s -> s.invokeAll(null, 1, null));
        callFunc(s -> s.invokeAny(null));
        callFunc(s -> s.invokeAny(null, 1, null));
        callFunc(s -> s.execute(null));
        callFunc(s -> s.schedule((Runnable) null, 1, null));
        callFunc(s -> s.schedule((Callable<?>) null, 1, null));
        callFunc(s -> s.scheduleAtFixedRate(null, 1, 1, null));
        callFunc(s -> s.scheduleWithFixedDelay(null, 1, 1, null));
    }

    private void callFunc(EzyExceptionApply<ScheduledExecutorService> func) {
        try {
            func.apply(service);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
