package com.tvd12.ezyfox.monitor.test;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.MemoryUsage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import com.sun.management.GarbageCollectorMXBean;
import com.sun.management.GcInfo;

@SuppressWarnings({"restriction", "rawtypes", "unchecked"})
public class LastGCInfo2 {
    public static void main(String[] argv) throws Exception {
        while(true) {
                byte[] bytes = new byte[1000000];
                new String(bytes);
                test();
                Thread.sleep(1000);
        }
    }
    
    private static void test() throws Exception {
            boolean hasGcInfo = false;
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
                else {
                        System.out.println("gc: " + gc.getName() + " has no last info");
                }
            }
        }
        if (! hasGcInfo) {
//            throw new RuntimeException("No GcInfo returned");
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
        for (Object e : usage.entrySet()) {
            Map.Entry entry = (Map.Entry) e;
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
    }
}
