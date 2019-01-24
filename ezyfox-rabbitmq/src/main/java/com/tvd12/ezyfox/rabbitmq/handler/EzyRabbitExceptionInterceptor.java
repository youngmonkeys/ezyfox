package com.tvd12.ezyfox.rabbitmq.handler;

public interface EzyRabbitExceptionInterceptor {
	
	void intercept(String cmd, Object requestData, Exception e);
	
}
