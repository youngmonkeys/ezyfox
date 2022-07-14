package com.tvd12.ezyfox.concurrent;

public interface EzyEventLoopEvent {

    boolean call();

    default void onFinished() {}

    default void onRemoved() {}
}
