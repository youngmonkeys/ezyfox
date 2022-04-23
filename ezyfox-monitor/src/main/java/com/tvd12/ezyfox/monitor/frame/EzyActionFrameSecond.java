package com.tvd12.ezyfox.monitor.frame;

public class EzyActionFrameSecond extends EzyActionFrame {

    public EzyActionFrameSecond() {
        this(Integer.MAX_VALUE);
    }
    
    public EzyActionFrameSecond(long maxActions) {
        super(maxActions);
    }
    
    public EzyActionFrameSecond(long maxActions, long startTime) {
        super(maxActions, startTime);
    }
    
    @Override
    protected final int getExistsTime() {
        return 1000;
    }

    @Override
    public final EzyActionFrame nextFrame() {
        return new EzyActionFrameSecond(maxActions, endTime);
    }
}
