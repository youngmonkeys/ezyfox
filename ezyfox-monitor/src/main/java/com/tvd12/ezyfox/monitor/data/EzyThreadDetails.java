package com.tvd12.ezyfox.monitor.data;

import java.lang.management.ThreadInfo;

import lombok.Getter;

@Getter
public class EzyThreadDetails extends EzyThreadDetail {

    protected final long blockedTime;
    protected final long blockedCount;
    protected final long waitedTime;
    protected final long waitedCount;
    protected final String lockName;
    protected final long lockOwnerId;
    protected final String lockOwnerName;
    protected final boolean inNative;
    protected final boolean suspended;
    protected final Thread.State state;
    protected final String overview;

    public EzyThreadDetails(ThreadInfo info) {
        super(info);
        this.blockedTime = info.getBlockedTime();
        this.blockedCount = info.getBlockedCount();
        this.waitedTime = info.getWaitedTime();
        this.waitedCount = info.getWaitedCount();
        this.lockName = info.getLockName();
        this.lockOwnerId = info.getLockOwnerId();
        this.lockOwnerName = info.getLockOwnerName();
        this.inNative = info.isInNative();
        this.suspended = info.isSuspended();
        this.state = info.getThreadState();
        this.overview = info.toString();
    }

    @Override
    public String toString() {
        return overview;
    }}