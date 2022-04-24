package com.tvd12.ezyfox.monitor.test;

import com.tvd12.ezyfox.monitor.EzyGcMonitor;
import com.tvd12.test.assertion.Asserts;
import com.tvd12.test.base.BaseTest;
import org.testng.annotations.Test;

import java.util.Collections;
import java.util.List;

public class EzyGcMonitorTest extends BaseTest {

    @Test
    public void test() throws Exception {
        EzyGcMonitor monitor = new EzyGcMonitor();
        System.out.println(monitor.getProcessGcActivity());
        Thread.sleep(1000);
        System.out.println(monitor.getProcessGcActivity());
        Thread.sleep(1000);
        System.out.println(monitor.getProcessGcActivity());
        Thread.sleep(1000);
        System.out.println(monitor.getProcessGcActivity());

        monitor = new EzyGcMonitor() {
            @Override
            protected List<?> getGarbageCollectorMXBeans() {
                return Collections.singletonList(new Object());
            }
        };
        Asserts.assertEquals(monitor.getProcessGcActivity(), 0.0D);
    }
}
