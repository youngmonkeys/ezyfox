package com.tvd12.ezyfox.message.handler;

@SuppressWarnings("rawtypes")
public interface EzyMessageHandlers extends EzyMessageHandler {

	void addMessageHandler(EzyMessageHandler handler);
	
}
