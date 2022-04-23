package com.tvd12.ezyfox.testing.util;

import com.tvd12.ezyfox.util.EzyTimes;
import com.tvd12.test.assertion.Asserts;
import com.tvd12.test.base.BaseTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class EzyTimesTest extends BaseTest {

    @Test
    public void test() {
        assertEquals(EzyTimes.getRemainTime(0, 0), 0);
        assertTrue(EzyTimes.getRemainTime(100, System.currentTimeMillis()) <= 100);

        assertEquals(EzyTimes.getPositiveRemainTime(0, 0), 0);
        assertTrue(EzyTimes.getPositiveRemainTime(100, System.currentTimeMillis()) <= 100);

        Asserts.assertEquals(
            EzyTimes.MILLIS_OF_DAY,
            24 * 60 * 60 * 1000L
        );
    }

    @Override
    public Class<?> getTestClass() {
        return EzyTimes.class;
    }
}
