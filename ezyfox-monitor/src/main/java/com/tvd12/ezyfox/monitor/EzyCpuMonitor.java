package com.tvd12.ezyfox.monitor;

import java.lang.management.ManagementFactory;
import com.sun.management.OperatingSystemMXBean;

@SuppressWarnings("restriction")
public class EzyCpuMonitor {

    protected volatile long lastProcessCpuTime = 0L;
    protected volatile long lastSystemTime = System.nanoTime();
    protected final OperatingSystemMXBean osMxBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();

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
        int nrocessors = osMxBean.getAvailableProcessors();
        return cpuLoad / nrocessors;
    }

    public double getSystemCpuLoad() {
        double cpuLoad = this.osMxBean.getSystemCpuLoad();
        return cpuLoad;
    }

}
