package com.tvd12.ezyfox.testing.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.concurrent.EzyExecutors;
import com.tvd12.ezyfox.function.EzyExceptionApply;

public class EzyErrorScheduledExecutorServiceTest {

    protected ScheduledExecutorService service;

    @Test
    public void test() {
        service = EzyExecutors.newErrorScheduledExecutor("hello");
        callFunc(s -> s.shutdown());
        callFunc(s -> s.shutdownNow());
        callFunc(s -> s.isShutdown());
        callFunc(s -> s.isTerminated());
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
        callFunc(s -> s.schedule((Runnable)null, 1, null));
        callFunc(s -> s.schedule((Callable<?>)null, 1, null));
        callFunc(s -> s.scheduleAtFixedRate(null, 1, 1, null));
        callFunc(s -> s.scheduleWithFixedDelay(null, 1, 1, null));
    }

    private void callFunc(EzyExceptionApply<ScheduledExecutorService> func) {
        try {
            func.apply(service);
        }
        catch (UnsupportedOperationException e) {
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
}
