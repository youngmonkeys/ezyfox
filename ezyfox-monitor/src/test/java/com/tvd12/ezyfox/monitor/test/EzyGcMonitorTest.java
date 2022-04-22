package com.tvd12.ezyfox.monitor.test;

import java.util.Arrays;
import java.util.List;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.monitor.EzyGcMonitor;
import com.tvd12.test.base.BaseTest;

public class EzyGcMonitorTest extends BaseTest {

    @Test
    public void test() throws Exception {
        EzyGcMonitor monitor = new EzyGcMonitor();
        System.out.println(monitor.getProcessGcActivity());
        System.out.println(new byte[1000]);
        Thread.sleep(1000);
        System.out.println(monitor.getProcessGcActivity());
        Thread.sleep(1000);
        System.out.println(monitor.getProcessGcActivity());
        Thread.sleep(1000);
        System.out.println(monitor.getProcessGcActivity());

        monitor = new EzyGcMonitor() {
            @Override
            protected List<?> getGarbageCollectorMXBeans() {
                return Arrays.asList(new Object());
            }
        };
        assert monitor.getProcessGcActivity() == 0.0D;
    }
}