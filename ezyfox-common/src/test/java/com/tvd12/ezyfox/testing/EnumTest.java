package com.tvd12.ezyfox.testing;

import com.tvd12.test.base.BaseTest;
import com.tvd12.test.performance.Performance;
import org.testng.annotations.Test;

public class EnumTest extends BaseTest {

    @SuppressWarnings("unused")
    @Test
    public void test() {
        long time = Performance.create()
            .loop(1000000)
            .test(() -> {
                One.ABC.getClass().isEnum();
                One one = Enum.valueOf(One.class, "ABC");
            })
            .getTime();
        System.err.println("loop time is: " + time);
    }

    @SuppressWarnings("unused")
    public enum One {
        ABC, DEF
    }
}
