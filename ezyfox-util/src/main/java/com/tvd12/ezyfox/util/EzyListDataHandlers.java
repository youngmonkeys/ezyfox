package com.tvd12.ezyfox.util;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("rawtypes")
public class EzyListDataHandlers
        extends EzyLoggable
        implements EzyDataHandlers {

    protected List<EzyDataHandler> handlers = new ArrayList<>();

    @SuppressWarnings("unchecked")
    @Override
    public void handleData(Object message) {
        for(EzyDataHandler handler : handlers)
            handler.handleData(message);
    }

    @Override
    public void addDataHandler(EzyDataHandler handler) {
        this.handlers.add(handler);
    }
}