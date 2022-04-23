package com.tvd12.ezyfox.monitor.test;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.monitor.EzyMemoryMonitor;
import com.tvd12.test.base.BaseTest;

public class EzyMemoryMonitorTest extends BaseTest {

    @Test
    public void test() {
        EzyMemoryMonitor monitor = new EzyMemoryMonitor();
        System.out.println(monitor.getFreeMemory());
        System.out.println(monitor.getMaxMemory());
        System.out.println(monitor.getTotalMemory());
    }
}
