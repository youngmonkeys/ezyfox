package com.tvd12.ezyfox.monitor;

import com.tvd12.ezyfox.monitor.data.EzyThreadDetail;
import com.tvd12.ezyfox.monitor.data.EzyThreadDetails;
import com.tvd12.ezyfox.monitor.data.EzyThreadsDetail;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.ArrayList;
import java.util.List;

public class EzyThreadsMonitor {

    public int getThreadCount() {
        ThreadMXBean tmxBean = getThreadMXBean();
        return tmxBean.getThreadCount();
    }

    public int getDaemonThreadCount() {
        ThreadMXBean tmxBean = getThreadMXBean();
        return tmxBean.getDaemonThreadCount();
    }

    public EzyThreadDetails getThreadDetails(long threadId) {
        ThreadMXBean tmxBean = getThreadMXBean();
        ThreadInfo info = tmxBean.getThreadInfo(threadId);
        if (info == null) {
            return null;
        }
        return new EzyThreadDetails(info);
    }

    public EzyThreadsDetail getThreadsDetails() {
        long totalThreadsCpuTime = 0L;
        ThreadMXBean tmxBean = getThreadMXBean();
        long[] threadIds = tmxBean.getAllThreadIds();
        List<EzyThreadDetail> threads = new ArrayList<>();
        for (long threadId : threadIds) {
            ThreadInfo threadInfo = tmxBean.getThreadInfo(threadId);
            if (threadInfo == null) {
                continue;
            }
            String threadName = threadInfo.getThreadName();
            long cpuTime = 0L;
            if (canGetThreadCpuTime()) {
                cpuTime = tmxBean.getThreadCpuTime(threadId);
                if (cpuTime > 0) {
                    totalThreadsCpuTime += cpuTime;
                }
            }
            EzyThreadDetail detail = EzyThreadDetail.builder()
                .id(threadId)
                .name(threadName)
                .cpuTime(cpuTime)
                .build();
            threads.add(detail);
        }
        return EzyThreadsDetail.builder()
            .threads(threads)
            .totalThreadsCpuTime(totalThreadsCpuTime)
            .build();
    }

    protected boolean canGetThreadCpuTime() {
        return isThreadCpuTimeEnabled() && isThreadCpuTimeSupported();
    }

    protected boolean isThreadCpuTimeEnabled() {
        return getThreadMXBean().isThreadCpuTimeEnabled();
    }

    protected boolean isThreadCpuTimeSupported() {
        return getThreadMXBean().isThreadCpuTimeSupported();
    }

    @SuppressWarnings("AbbreviationAsWordInName")
    protected ThreadMXBean getThreadMXBean() {
        return ManagementFactory.getThreadMXBean();
    }
}
