package com.tvd12.ezyfox.testing;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.collect.Lists;
import com.tvd12.test.base.BaseTest;
import com.tvd12.test.performance.Performance;

public class GuavaArrayListTest extends BaseTest {

    @Test
    public void test() {
        long time = Performance.create()
                .loop(1000000)
                .test(()-> {
                    Lists.newArrayList(new Integer[] {1, 2, 3});
                })
                .getTime();
                System.out.println("test elapsed time = " + time);
    }
}
