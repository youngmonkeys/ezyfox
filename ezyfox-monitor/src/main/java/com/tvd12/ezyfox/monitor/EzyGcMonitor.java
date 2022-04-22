package com.tvd12.ezyfox.monitor;

import java.lang.management.ManagementFactory;
import java.util.List;

import com.sun.management.GarbageCollectorMXBean;
import com.sun.management.GcInfo;

@SuppressWarnings("restriction")
public class EzyGcMonitor {

    protected volatile long lastGcActivityTime = System.nanoTime();
    protected static final double MILI_TO_NANO = 1000000.0D;

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
        if(totalGCTime <= 0)
                return 0.0D;
        long currentTime = System.nanoTime();
        long offsetTime = currentTime - lastGcActivityTime;
        lastGcActivityTime = currentTime;
        double percent = (totalGCTime * MILI_TO_NANO) / (offsetTime * totalGC);
        return percent;
    }

    protected List<?> getGarbageCollectorMXBeans() {
        List<?> beans = ManagementFactory.getGarbageCollectorMXBeans();
        return beans;
    }
}