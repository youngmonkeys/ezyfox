package com.tvd12.ezyfox.message.handler;

public interface EzyMessageHandler<M> {

    void handleMessage(M message);

    default void handleException(Thread thread, Throwable throwable) {}
}