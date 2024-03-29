package com.tvd12.ezyfox.testing.util;

import com.tvd12.ezyfox.util.EzyLoggable;
import com.tvd12.test.performance.Performance;
import org.testng.annotations.Test;

public class EzyLoggableTest extends EzyLoggable {

    @Test
    public void test() {
        logger.debug("abc");
    }

    @Test
    public void performanceTest() {
        long time1 = Performance.create()
            .test(A::new)
            .getTime();
        long time2 = Performance.create()
            .test(B::new)
            .getTime();
        System.out.println("EzyLoggableTest:performanceTest: time1: " + time1 + ", time2: " + time2);
    }

    public static class A extends EzyLoggable {

    }

    public static class B {

    }
}
