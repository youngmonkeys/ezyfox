package com.tvd12.ezyfox.testing.concurrent;

import java.util.concurrent.atomic.AtomicInteger;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.concurrent.EzyThreadList;
import com.tvd12.test.base.BaseTest;

public class EzyThreadListTest extends BaseTest {

    @Test
    public void test() {
        int maxCount = 5;
        AtomicInteger count = new AtomicInteger();
        EzyThreadList tl = new EzyThreadList(maxCount, () -> {
            try {
                while(count.get() < 10) {
                    info("count = " + count.incrementAndGet());
                    Thread.sleep(1);
                }
            }
            catch (Exception e) {
            }
        }, "test-thread-list");
        tl.execute();
    }

    @Test
    public void interruptTest() {
        try {
            int maxCount = 5;
            AtomicInteger count = new AtomicInteger();
            EzyThreadList tl = new EzyThreadList(maxCount, () -> {
                try {
                    while(count.get() < 10) {
                        info("count = " + count.incrementAndGet());
                        Thread.sleep(1);
                    }
                }
                catch (Exception e) {
                }
            }, "test-thread-list");
            tl.execute();
            tl.interrupt();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}
