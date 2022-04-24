package com.tvd12.ezyfox.monitor;

import com.tvd12.ezyfox.monitor.frame.EzyActionFrame;
import com.tvd12.ezyfox.monitor.frame.EzyActionFrameSecond;

import java.util.concurrent.atomic.AtomicLong;

public class EzyMonitorIncludeRequestResponse extends EzyMonitor {

    private final AtomicLong requestCount;
    private EzyActionFrame requestPerSecond;
    private EzyActionFrame responsePerSecond;

    public EzyMonitorIncludeRequestResponse() {
        this.requestCount = new AtomicLong();
        this.requestPerSecond = new EzyActionFrameSecond();
        this.responsePerSecond = new EzyActionFrameSecond();
    }

    public void increaseRequestCount() {
        requestCount.incrementAndGet();
        if (requestPerSecond.isExpired()) {
            requestPerSecond = new EzyActionFrameSecond();
        }
        requestPerSecond.addActions(1);
    }

    public long getRequestCount() {
        return requestCount.get();
    }

    public long getRequestPerSecond() {
        return requestPerSecond.isExpired() ? 0L : requestPerSecond.getActions();
    }

    public void increaseResponseCount() {
        if (responsePerSecond.isExpired()) {
            responsePerSecond = new EzyActionFrameSecond();
        }
        responsePerSecond.addActions(1);
    }

    public long getResponsePerSecond() {
        return responsePerSecond.isExpired() ? 0L : responsePerSecond.getActions();
    }
}
