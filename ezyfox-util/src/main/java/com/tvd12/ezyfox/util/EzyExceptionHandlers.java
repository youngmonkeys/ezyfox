package com.tvd12.ezyfox.util;

public interface EzyExceptionHandlers extends EzyExceptionHandler {

    boolean isEmpty();

    void addExceptionHandler(EzyExceptionHandler handler);
}
