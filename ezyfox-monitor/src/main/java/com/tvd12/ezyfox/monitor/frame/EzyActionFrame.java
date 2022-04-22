package com.tvd12.ezyfox.monitor.frame;

import java.text.SimpleDateFormat;

import lombok.Getter;

@Getter
public abstract class EzyActionFrame {

    protected volatile long actions;
    protected final long endTime;
    protected final long startTime;
    protected final long maxActions;
    
    public EzyActionFrame(long maxActions) {
        this(maxActions, System.currentTimeMillis());
    }
    
    public EzyActionFrame(long maxActions, long startTime) {
        this.maxActions = maxActions;
        this.startTime = startTime;
        this.endTime = startTime + getExistsTime();
    }
    
    protected abstract int getExistsTime();
    
    public boolean addActions(long actions) {
        return (this.actions += actions) > maxActions;
    }
    
    public boolean isExpired() {
        return System.currentTimeMillis() > endTime;
    }
    
    public boolean isInvalid() {
        return actions > maxActions;
    }
    
    public abstract EzyActionFrame nextFrame();
    
    @Override
    public String toString() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss.SSS");
        return new StringBuilder()
                .append(getClass().getSimpleName())
                .append(": ")
                .append(df.format(startTime))
                .append(" -> ")
                .append(df.format(endTime))
                .toString();
    }
    }