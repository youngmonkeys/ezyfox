package com.tvd12.ezyfox.testing.concurrent;

import com.tvd12.ezyfox.concurrent.EzyExecutors;
import com.tvd12.test.base.BaseTest;
import org.testng.annotations.Test;

public class EzyExecutorsTest extends BaseTest {

    @Test
    public void test() {
        System.out.println(
            EzyExecutors.newFixedThreadPool(5, "test")
        );
        System.out.println(
            EzyExecutors.newScheduledThreadPool(5, "test")
        );
        System.out.println(
            EzyExecutors.newSingleThreadExecutor("test")
        );
        System.out.println(
            EzyExecutors.newSingleThreadScheduledExecutor("test")
        );
        System.out.println(
            EzyExecutors.newThreadFactory("test", false)
        );
        System.out.println(
            EzyExecutors.newThreadFactory("test", Thread.NORM_PRIORITY)
        );
    }

    @Override
    public Class<?> getTestClass() {
        return EzyExecutors.class;
    }
}
