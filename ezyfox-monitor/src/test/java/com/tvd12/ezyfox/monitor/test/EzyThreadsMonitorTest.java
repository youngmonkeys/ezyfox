package com.tvd12.ezyfox.monitor.test;

import com.tvd12.ezyfox.monitor.EzyThreadsMonitor;
import com.tvd12.ezyfox.monitor.data.EzyThreadDetail;
import com.tvd12.ezyfox.monitor.data.EzyThreadDetails;
import com.tvd12.ezyfox.monitor.data.EzyThreadsDetail;
import com.tvd12.test.base.BaseTest;
import org.testng.annotations.Test;

import java.lang.management.ThreadMXBean;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class EzyThreadsMonitorTest extends BaseTest {

    @Test
    public void test() {
        EzyThreadsMonitor monitor = new EzyThreadsMonitor();
        System.out.println(monitor.getThreadCount());
        System.out.println(monitor.getDaemonThreadCount());
        System.out.println(monitor.getThreadsDetails());
        System.out.println(monitor.getThreadDetails(Thread.currentThread().getId()));

        EzyThreadDetails threadDetails = monitor.getThreadDetails(Thread.currentThread().getId());
        System.out.println(threadDetails.getBlockedTime());
        System.out.println(threadDetails.getBlockedCount());
        System.out.println(threadDetails.getWaitedTime());
        System.out.println(threadDetails.getWaitedCount());
        System.out.println(threadDetails.getLockName());
        System.out.println(threadDetails.getLockOwnerId());
        System.out.println(threadDetails.getLockOwnerName());
        System.out.println(threadDetails.isInNative());
        System.out.println(threadDetails.isSuspended());
        System.out.println(threadDetails.getState());
        System.out.println(threadDetails.getOverview());

        EzyThreadsDetail threadsDetails = monitor.getThreadsDetails();
        System.out.println(threadsDetails.getTotalThreadsCpuTime());
        List<EzyThreadDetail> threads = threadsDetails.getThreads();
        for (EzyThreadDetail thread : threads) {
            System.out.println(thread.getId());
            System.out.println(thread.getName());
            System.out.println(thread.getCpuTime());
        }

        monitor = new EzyThreadsMonitor() {
            protected ThreadMXBean getThreadMXBean() {
                ThreadMXBean bean = mock(ThreadMXBean.class);
                when(bean.getAllThreadIds()).thenReturn(new long[]{Thread.currentThread().getId()});
                return bean;
            }
        };
        System.out.println(monitor.getThreadsDetails());
        System.out.println(monitor.getThreadDetails(Thread.currentThread().getId()));

        monitor = new EzyThreadsMonitor() {
            @Override
            protected boolean isThreadCpuTimeEnabled() {
                return false;
            }
        };
        System.out.println(monitor.getThreadsDetails());
        System.out.println(monitor.getThreadDetails(Thread.currentThread().getId()));

        monitor = new EzyThreadsMonitor() {
            @Override
            protected boolean isThreadCpuTimeSupported() {
                return false;
            }
        };
        System.out.println(monitor.getThreadsDetails());
        System.out.println(monitor.getThreadDetails(Thread.currentThread().getId()));
    }
}
