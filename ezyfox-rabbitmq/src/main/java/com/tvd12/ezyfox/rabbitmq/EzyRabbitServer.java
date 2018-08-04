package com.tvd12.ezyfox.rabbitmq;

import com.tvd12.ezyfox.message.handler.EzyMessageHandler;
import com.tvd12.ezyfox.util.EzyExceptionHandler;
import com.tvd12.ezyfox.util.EzyShutdownable;
import com.tvd12.ezyfox.util.EzyStartable;

@SuppressWarnings("rawtypes")
public interface EzyRabbitServer extends EzyStartable, EzyShutdownable {
	
	void addMessagesHandler(EzyMessageHandler messageHandler);
	
	void addExceptionHandler(EzyExceptionHandler exceptionHandler);
	
}
