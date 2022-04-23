package com.tvd12.ezyfox.testing;

import com.tvd12.ezyfox.collect.Lists;
import com.tvd12.test.base.BaseTest;
import com.tvd12.test.performance.Performance;
import org.testng.annotations.Test;

public class GuavaArrayListTest extends BaseTest {

    @Test
    public void test() {
        long time = Performance.create()
            .loop(1000000)
            .test(() -> Lists.newArrayList(1, 2, 3))
            .getTime();
        System.out.println("test elapsed time = " + time);
    }
}
