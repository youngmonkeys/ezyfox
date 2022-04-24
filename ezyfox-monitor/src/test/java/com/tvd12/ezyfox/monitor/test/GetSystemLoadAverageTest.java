package com.tvd12.ezyfox.monitor.test;

import com.sun.management.OperatingSystemMXBean;

import java.lang.management.ManagementFactory;

@SuppressWarnings("restriction")
public class GetSystemLoadAverageTest {

    public static void main(String[] args) {
        OperatingSystemMXBean osMxBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        double systemLoadAverage = osMxBean.getSystemLoadAverage();
        System.out.println("systemLoadAverage: " + systemLoadAverage);
    }
}
