package com.tvd12.ezyfox.monitor;

import com.sun.management.GarbageCollectorMXBean;
import com.sun.management.GcInfo;

import java.lang.management.ManagementFactory;
import java.util.List;

@SuppressWarnings("restriction")
public class EzyGcMonitor {

    protected volatile long lastGcActivityTime = System.nanoTime();
    protected static final double MILLIS_TO_NANO = 1000000.0D;

    @SuppressWarnings("AbbreviationAsWordInName")
    public double getProcessGcActivity() {
        int totalGC = 0;
        long totalGCTime = 0;
        List<?> beans = getGarbageCollectorMXBeans();
        for (Object bean : beans) {
            if (bean instanceof GarbageCollectorMXBean) {
                GarbageCollectorMXBean gc = (GarbageCollectorMXBean) bean;
                GcInfo info = gc.getLastGcInfo();
                if (info != null) {
                    totalGC += 1;
                    totalGCTime += info.getDuration();
                }
            }
        }
        if (totalGCTime <= 0) {
            return 0.0D;
        }
        long currentTime = System.nanoTime();
        long offsetTime = currentTime - lastGcActivityTime;
        lastGcActivityTime = currentTime;
        return (totalGCTime * MILLIS_TO_NANO) / (offsetTime * totalGC);
    }

    @SuppressWarnings("AbbreviationAsWordInName")
    protected List<?> getGarbageCollectorMXBeans() {
        return ManagementFactory.getGarbageCollectorMXBeans();
    }
}
