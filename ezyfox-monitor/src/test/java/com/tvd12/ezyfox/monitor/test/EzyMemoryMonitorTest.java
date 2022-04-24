package com.tvd12.ezyfox.monitor.test;

import com.tvd12.ezyfox.monitor.EzyMemoryMonitor;
import com.tvd12.test.base.BaseTest;
import org.testng.annotations.Test;

public class EzyMemoryMonitorTest extends BaseTest {

    @Test
    public void test() {
        EzyMemoryMonitor monitor = new EzyMemoryMonitor();
        System.out.println(monitor.getFreeMemory());
        System.out.println(monitor.getMaxMemory());
        System.out.println(monitor.getTotalMemory());
    }
}
