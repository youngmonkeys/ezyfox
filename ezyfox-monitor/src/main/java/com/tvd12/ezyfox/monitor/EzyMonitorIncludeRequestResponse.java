package com.tvd12.ezyfox.monitor;

import java.util.concurrent.atomic.AtomicLong;

import com.tvd12.ezyfox.monitor.frame.EzyActionFrame;
import com.tvd12.ezyfox.monitor.frame.EzyActionFrameSecond;

public class EzyMonitorIncludeRequestResponse extends EzyMonitor {

    private final AtomicLong requestCount;
    private EzyActionFrame requestPerSecond;
    private EzyActionFrame reponsePerSecond;
    
    public EzyMonitorIncludeRequestResponse() {
        this.requestCount = new AtomicLong();
        this.requestPerSecond = new EzyActionFrameSecond();
        this.reponsePerSecond = new EzyActionFrameSecond();
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
        if (reponsePerSecond.isExpired()) {
            reponsePerSecond = new EzyActionFrameSecond();
        }
        reponsePerSecond.addActions(1);
    }

    public long getResponsePerSecond() {
        return reponsePerSecond.isExpired() ? 0L : reponsePerSecond.getActions();
    }}