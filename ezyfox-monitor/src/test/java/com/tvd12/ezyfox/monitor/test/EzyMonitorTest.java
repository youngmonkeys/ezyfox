package com.tvd12.ezyfox.monitor.test;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.monitor.EzyMonitor;
import com.tvd12.test.base.BaseTest;

public class EzyMonitorTest extends BaseTest {

    @Test
    public void test() {
        EzyMonitor monitor = new EzyMonitor();
        System.out.println(monitor.getGcMonitor());
        System.out.println(monitor.getCpuMonitor());
        System.out.println(monitor.getMemoryMonitor());
        System.out.println(monitor.getThreadsMonitor());
    }

}
