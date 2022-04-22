package com.tvd12.ezyfox.message.handler;

@SuppressWarnings("rawtypes")
public interface EzyMessageHandlers {

    EzyMessageHandler getHandler(Object messageType);
    
    void addHandler(Object messageType, EzyMessageHandler handler);
    }