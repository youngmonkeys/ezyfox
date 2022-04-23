package com.tvd12.ezyfox.util;

import java.util.ArrayList;
import java.util.List;

public class EzyListExceptionHandlers 
        extends EzyLoggable
        implements EzyExceptionHandlers {

    protected List<EzyExceptionHandler> handlers = new ArrayList<>();

    @Override
    public void addExceptionHandler(EzyExceptionHandler handler) {
        handlers.add(handler);
    }

    @Override
    public void handleException(Thread thread, Throwable throwable) {
        for(EzyExceptionHandler handler : handlers)
            handler.handleException(thread, throwable);
    }
}
