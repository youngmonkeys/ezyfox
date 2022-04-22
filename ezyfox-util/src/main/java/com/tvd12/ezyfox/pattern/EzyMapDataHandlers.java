package com.tvd12.ezyfox.pattern;

import java.util.HashMap;
import java.util.Map;

import com.tvd12.ezyfox.util.EzyLoggable;

@SuppressWarnings("rawtypes")
public class EzyMapDataHandlers extends EzyLoggable implements EzyDataHandlers {

    protected Map<Object, EzyDataHandler> handlers = new HashMap<>();

    @Override
    public EzyDataHandler getHandler(Object dataType) {
        EzyDataHandler handler = handlers.get(dataType);
        if(handler != null)
            return handler;
        throw new IllegalArgumentException("has no handler with data type: " + dataType);
    }

    @Override
    public void addHandler(Object dataType, EzyDataHandler handler) {
        handlers.put(dataType, handler);
    }

}
