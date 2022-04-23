package com.tvd12.ezyfox.testing.concurrent;

import com.tvd12.ezyfox.concurrent.EzyThreadList;
import com.tvd12.ezyfox.util.EzyThreads;
import com.tvd12.test.base.BaseTest;
import org.testng.annotations.Test;

import java.util.concurrent.atomic.AtomicInteger;

public class EzyThreadListTest extends BaseTest {

    @Test
    public void test() {
        int maxCount = 5;
        AtomicInteger count = new AtomicInteger();
        EzyThreadList tl = new EzyThreadList(maxCount, () -> {
            try {
                while (count.get() < 10) {
                    info("count = " + count.incrementAndGet());
                    EzyThreads.sleep(1);
                }
            } catch (Exception e) {
                e.printStackTrace();
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
                    while (count.get() < 10) {
                        info("count = " + count.incrementAndGet());
                        EzyThreads.sleep(1);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }, "test-thread-list");
            tl.execute();
            tl.interrupt();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
