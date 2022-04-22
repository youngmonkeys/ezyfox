package com.tvd12.ezyfox.pattern;

public interface EzyDataHandler<D> {

    void handleData(D data);

    default void handleException(Thread thread, Throwable throwable) {}
}