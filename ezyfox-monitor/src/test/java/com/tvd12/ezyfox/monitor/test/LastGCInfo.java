package com.tvd12.ezyfox.monitor.test;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryUsage;
import java.lang.management.MemoryPoolMXBean;
import java.util.*;
import com.sun.management.GcInfo;
import com.sun.management.GarbageCollectorMXBean;

@SuppressWarnings({"restriction", "rawtypes", "unchecked"})
public class LastGCInfo {
    public static void main(String[] argv) throws Exception {
        boolean hasGcInfo = false;

        System.gc();
        List mgrs = ManagementFactory.getGarbageCollectorMXBeans();
        for (ListIterator iter = mgrs.listIterator(); iter.hasNext(); ) {
            Object mgr = iter.next();
            if (mgr instanceof GarbageCollectorMXBean) {
                GarbageCollectorMXBean gc = (GarbageCollectorMXBean) mgr;
                GcInfo info = gc.getLastGcInfo();
                if (info != null) {
                    checkGcInfo(gc.getName(), info);
                    hasGcInfo = true;
                }
            }
        }

        if (! hasGcInfo) {
            throw new RuntimeException("No GcInfo returned");
        }
        System.out.println("Test passed.");
    }

    private static void checkGcInfo(String name, GcInfo info) throws Exception {
        System.out.println("GC statistic for : " + name);
        System.out.print("GC #" + info.getId());
        System.out.print(" start:" + info.getStartTime());
        System.out.print(" end:" + info.getEndTime());
        System.out.println(" (" + info.getDuration() + "ms)");
        Map usage = info.getMemoryUsageBeforeGc();

        List pnames = new ArrayList();
        for (Iterator iter = usage.entrySet().iterator(); iter.hasNext(); ) {
            Map.Entry entry = (Map.Entry) iter.next();
            String poolname = (String) entry.getKey();
            pnames.add(poolname);
            MemoryUsage busage = (MemoryUsage) entry.getValue();
            MemoryUsage ausage = (MemoryUsage) info.getMemoryUsageAfterGc().get(poolname);
            if (ausage == null) {
                throw new RuntimeException("After Gc Memory does not exist" +
                    " for " + poolname);
            }
            System.out.println("Usage for pool " + poolname);
            System.out.println("   Before GC: " + busage);
            System.out.println("   After GC: " + ausage);
        }

        // check if memory usage for all memory pools are returned
        List pools = ManagementFactory.getMemoryPoolMXBeans();
        for (Iterator iter = pools.iterator(); iter.hasNext(); ) {
            MemoryPoolMXBean p = (MemoryPoolMXBean) iter.next();
            if (!pnames.contains(p.getName())) {
                throw new RuntimeException("GcInfo does not contain " +
                    "memory usage for pool " + p.getName());
            }
        }
    }}