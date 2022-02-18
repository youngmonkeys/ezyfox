package com.tvd12.ezyfox.testing.util;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.util.EzyThreads;
import com.tvd12.test.base.BaseTest;

public class EzyThreadsTest extends BaseTest {

    @Test
    public void sleepTest() {
        EzyThreads.sleep(1);
    }
    
    @Test
    public void sleepInterrupt() {
        Thread thread = new Thread(() -> {
            EzyThreads.sleep(1000);
        });
        thread.start();
        EzyThreads.sleep(50);
        thread.interrupt();
    }
    
    @Override
    public Class<?> getTestClass() {
        return EzyThreads.class;
    }
}
