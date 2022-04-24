package com.tvd12.ezyfox.monitor.test;

import com.sun.management.GarbageCollectorMXBean;
import com.sun.management.GcInfo;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.MemoryUsage;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SuppressWarnings({"restriction", "rawtypes", "unchecked"})
public class LastGCInfo {
    public static void main(String[] argv) {
        boolean hasGcInfo = false;

        System.gc();
        List mgrs = ManagementFactory.getGarbageCollectorMXBeans();
        for (Object mgr : mgrs) {
            if (mgr instanceof GarbageCollectorMXBean) {
                GarbageCollectorMXBean gc = (GarbageCollectorMXBean) mgr;
                GcInfo info = gc.getLastGcInfo();
                if (info != null) {
                    checkGcInfo(gc.getName(), info);
                    hasGcInfo = true;
                }
            }
        }

        if (!hasGcInfo) {
            throw new RuntimeException("No GcInfo returned");
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

        List paramNames = new ArrayList();
        for (Object o : usage.entrySet()) {
            Map.Entry entry = (Map.Entry) o;
            String poolName = (String) entry.getKey();
            paramNames.add(poolName);
            MemoryUsage busage = (MemoryUsage) entry.getValue();
            MemoryUsage ausage = info.getMemoryUsageAfterGc().get(poolName);
            if (ausage == null) {
                throw new RuntimeException("After Gc Memory does not exist" +
                    " for " + poolName);
            }
            System.out.println("Usage for pool " + poolName);
            System.out.println("   Before GC: " + busage);
            System.out.println("   After GC: " + ausage);
        }

        // check if memory usage for all memory pools are returned
        List pools = ManagementFactory.getMemoryPoolMXBeans();
        for (Object pool : pools) {
            MemoryPoolMXBean p = (MemoryPoolMXBean) pool;
            if (!paramNames.contains(p.getName())) {
                throw new RuntimeException("GcInfo does not contain " +
                    "memory usage for pool " + p.getName());
            }
        }
    }
}
