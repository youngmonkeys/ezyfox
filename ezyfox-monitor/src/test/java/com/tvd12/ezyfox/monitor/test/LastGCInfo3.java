package com.tvd12.ezyfox.monitor.test;

import com.sun.management.GarbageCollectorMXBean;
import com.sun.management.GcInfo;

import java.lang.management.ManagementFactory;
import java.util.List;

@SuppressWarnings({"restriction"})
public class LastGCInfo3 {

    private static long lastGCTime = System.nanoTime();

    @SuppressWarnings({"BusyWait", "InfiniteLoopStatement"})
    public static void main(String[] argv) throws Exception {
        while (true) {
            byte[] bytes = new byte[1000000];
            new String(bytes);
            test();
            Thread.sleep(1000);
        }
    }

    private static void test() {
        List<?> mgrs = ManagementFactory.getGarbageCollectorMXBeans();
        long totalGCTime = 0;
        int totalGc = 0;
        for (Object mgr : mgrs) {
            if (mgr instanceof GarbageCollectorMXBean) {
                GarbageCollectorMXBean gc = (GarbageCollectorMXBean) mgr;
                GcInfo info = gc.getLastGcInfo();
                if (info != null) {
                    ++totalGc;
                    totalGCTime += info.getDuration();
                }
            }
        }
        if (totalGCTime <= 0) {
            System.out.println("gc is not active");
            return;
        }
        long currentTime = System.nanoTime();
        long offsetTime = currentTime - lastGCTime;
        lastGCTime = currentTime;
        double percent = (totalGCTime * 1000000.0D) / (offsetTime * totalGc);
        double percentPretty = Math.round(percent * 1000.0D) / 1000.0D;
        System.out.println("percent: " + percentPretty);

    }
}
