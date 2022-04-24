package com.tvd12.ezyfox.monitor;

import com.sun.management.OperatingSystemMXBean;

import java.lang.management.ManagementFactory;

@SuppressWarnings("restriction")
public class EzyCpuMonitor {

    protected volatile long lastProcessCpuTime;
    protected volatile long lastSystemTime = System.nanoTime();
    protected final OperatingSystemMXBean osMxBean
        = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();

    public EzyCpuMonitor() {
        this.lastProcessCpuTime = osMxBean.getProcessCpuTime();
    }

    public double getProcessCpuLoad() {
        long systemTime = System.nanoTime();
        long processCpuTime = osMxBean.getProcessCpuTime();
        long offsetSystemTime = systemTime - lastSystemTime;
        long offsetProcessCpuTime = processCpuTime - lastProcessCpuTime;
        double cpuLoad = (1.0D * offsetProcessCpuTime) / offsetSystemTime;
        this.lastSystemTime = systemTime;
        this.lastProcessCpuTime = processCpuTime;
        int availableProcessors = osMxBean.getAvailableProcessors();
        return cpuLoad / availableProcessors;
    }

    public double getSystemCpuLoad() {
        return this.osMxBean.getSystemCpuLoad();
    }
}
