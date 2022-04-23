package com.tvd12.ezyfox.message.handler;

import java.util.HashMap;
import java.util.Map;

import com.tvd12.ezyfox.util.EzyLoggable;

@SuppressWarnings("rawtypes")
public class EzyMapMessageHandlers extends EzyLoggable implements EzyMessageHandlers {

    protected Map<Object, EzyMessageHandler> handlers = new HashMap<>();

    @Override
    public EzyMessageHandler getHandler(Object messageType) {
        EzyMessageHandler handler = handlers.get(messageType);
        if(handler != null)
            return handler;
        throw new IllegalArgumentException("has no handler with message type: " + messageType);
    }

    @Override
    public void addHandler(Object messageType, EzyMessageHandler handler) {
        handlers.put(messageType, handler);
    }
}
