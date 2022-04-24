package com.tvd12.ezyfox.monitor.test;

import com.sun.management.GarbageCollectorMXBean;
import com.sun.management.GcInfo;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.MemoryUsage;
import java.util.*;

@SuppressWarnings({"restriction", "rawtypes", "unchecked"})
public class LastGCInfo2 {
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
        boolean hasGcInfo = false;
        List mgrs = ManagementFactory.getGarbageCollectorMXBeans();
        for (Object mgr : mgrs) {
            if (mgr instanceof GarbageCollectorMXBean) {
                GarbageCollectorMXBean gc = (GarbageCollectorMXBean) mgr;
                GcInfo info = gc.getLastGcInfo();
                if (info != null) {
                    checkGcInfo(gc.getName(), info);
                    hasGcInfo = true;
                } else {
                    System.out.println("gc: " + gc.getName() + " has no last info");
                }
            }
        }
        if (!hasGcInfo) {
            System.out.println("No GcInfo returned");
        }
        System.out.println("Test passed.");
    }

    private static void checkGcInfo(String name, GcInfo info) {
        System.out.println("GC statistic for : " + name);
        System.out.print("GC #" + info.getId());
        System.out.print(" start:" + info.getStartTime());
        System.out.print(" end:" + info.getEndTime());
        System.out.println(" (" + info.getDuration() + "ms)");
        Map usage = info.getMemoryUsageBeforeGc();

        List pnames = new ArrayList();
        for (Object e : usage.entrySet()) {
            Map.Entry entry = (Map.Entry) e;
            String poolname = (String) entry.getKey();
            pnames.add(poolname);
            MemoryUsage busage = (MemoryUsage) entry.getValue();
            MemoryUsage ausage = info.getMemoryUsageAfterGc().get(poolname);
            if (ausage == null) {
                throw new RuntimeException(
                    "After Gc Memory does not exist" + " for " + poolname
                );
            }
            System.out.println("Usage for pool " + poolname);
            System.out.println("   Before GC: " + busage);
            System.out.println("   After GC: " + ausage);
        }

        // check if memory usage for all memory pools are returned
        List pools = ManagementFactory.getMemoryPoolMXBeans();
        for (Object pool : pools) {
            MemoryPoolMXBean p = (MemoryPoolMXBean) pool;
            if (!pnames.contains(p.getName())) {
                throw new RuntimeException("GcInfo does not contain " +
                    "memory usage for pool " + p.getName());
            }
        }
    }
}
