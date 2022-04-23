package com.tvd12.ezyfox.testing.concurrent;

import com.tvd12.ezyfox.concurrent.EzyFutureTask;
import com.tvd12.test.assertion.Asserts;
import com.tvd12.test.base.BaseTest;
import com.tvd12.test.util.RandomUtil;
import org.testng.annotations.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class EzyFutureTaskTest extends BaseTest {

    @SuppressWarnings("CatchMayIgnoreException")
    @Test
    public void test() throws Exception {
        EzyFutureTask task = new EzyFutureTask();
        try {
            task.setResult(null);
        } catch (Exception e) {
            assert e instanceof NullPointerException;
        }

        try {
            task.setException(null);
        } catch (Exception e) {
            assert e instanceof NullPointerException;
        }

        Thread thread = new Thread(() -> {
            try {
                Integer value = task.get(3000L);
                assert value == 123;
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        thread.start();
        Thread.sleep(100);
        task.setResult(123);
        Thread.sleep(100);
        task.setResult(456);
        Thread.sleep(100);
        assert task.get().equals(123);

        EzyFutureTask task2 = new EzyFutureTask();
        thread = new Thread(() -> {
            try {
                Integer value = task2.get(3000L);
                assert value == 123;
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        thread.start();
        Thread.sleep(100);
        task2.cancel("test cancel");
        Thread.sleep(100);
    }

    @Test
    public void timeoutCaseTest() throws Exception {
        EzyFutureTask task = new EzyFutureTask();
        Thread thread = new Thread(() -> {
            try {
                task.get(10);
            } catch (Exception e) {
                assert e instanceof TimeoutException;
            }
        });
        thread.start();
        Thread.sleep(100);
    }

    @Test
    public void timeUnitTest() {
        Asserts.assertEquals(1L, TimeUnit.MILLISECONDS.convert(1, TimeUnit.MILLISECONDS));
        Asserts.assertEquals(1000L, TimeUnit.MILLISECONDS.convert(1, TimeUnit.SECONDS));
        Asserts.assertEquals(60 * 1000L, TimeUnit.MILLISECONDS.convert(1, TimeUnit.MINUTES));
    }

    @Test
    public void toFutureTest() throws Exception {
        // given
        Long result = RandomUtil.randomLong();
        EzyFutureTask task = new EzyFutureTask();

        // when
        // then
        task.setResult(result);
        Future<Long> future = task.toFuture();
        Asserts.assertEquals(result, future.get());
        Asserts.assertEquals(result, future.get(1, TimeUnit.SECONDS));
        Asserts.assertTrue(future.isDone());
        Asserts.assertFalse(future.isCancelled());
        Asserts.assertFalse(future.cancel(false));
    }

    @Test
    public void toFutureCancelTest() {
        // given
        Long result = RandomUtil.randomLong();
        EzyFutureTask task = new EzyFutureTask();

        // when
        // then
        task.setResult(result);
        Future<Long> future = task.toFuture();
        Asserts.assertTrue(future.cancel(false));
        Asserts.assertTrue(future.isCancelled());
    }

    @Test
    public void toFutureGetInterruptedExceptionTest() {
        // given
        EzyFutureTask task = new EzyFutureTask();
        task.setException(new InterruptedException());

        // when
        Future<Long> future = task.toFuture();

        // then
        Asserts.assertThat(future::get)
            .willThrows(InterruptedException.class);
    }

    @Test
    public void toFutureExecutionExceptionGetTest() {
        // given
        EzyFutureTask task = new EzyFutureTask();
        task.setException(new ExecutionException("test", new Exception()));

        // when
        Future<Long> future = task.toFuture();
        // then
        Asserts.assertThat(future::get)
            .willThrows(ExecutionException.class);
    }

    @Test
    public void toFutureExceptionGetTest() {
        // given
        EzyFutureTask task = new EzyFutureTask();
        task.setException(new Exception("test"));

        // when
        Future<Long> future = task.toFuture();

        // then
        Asserts.assertThat(future::get)
            .willThrows(ExecutionException.class);
    }

    @Test
    public void toFutureGetTimeoutExceptionTest() {
        // given
        EzyFutureTask task = new EzyFutureTask();
        task.setException(new TimeoutException());

        // when
        Future<Long> future = task.toFuture();

        // then
        Asserts.assertThat(() -> future.get(1, TimeUnit.SECONDS))
            .willThrows(TimeoutException.class);
    }

    @Test
    public void toFutureGetTimeoutInterruptedExceptionTest() {
        // given
        EzyFutureTask task = new EzyFutureTask();
        task.setException(new InterruptedException());

        // when
        Future<Long> future = task.toFuture();

        // then
        Asserts.assertThat(() -> future.get(1, TimeUnit.SECONDS))
            .willThrows(InterruptedException.class);
    }

    @Test
    public void toFutureExecutionExceptionGetTimeoutTest() {
        // given
        EzyFutureTask task = new EzyFutureTask();
        task.setException(new ExecutionException("test", new Exception()));

        // when
        Future<Long> future = task.toFuture();
        // then
        Asserts.assertThat(() -> future.get(1, TimeUnit.SECONDS))
            .willThrows(ExecutionException.class);
    }

    @Test
    public void toFutureExceptionGetTimeoutTest() {
        // given
        EzyFutureTask task = new EzyFutureTask();
        task.setException(new Exception("test"));

        // when
        Future<Long> future = task.toFuture();

        // then
        Asserts.assertThat(() -> future.get(1, TimeUnit.SECONDS))
            .willThrows(ExecutionException.class);
    }
}
