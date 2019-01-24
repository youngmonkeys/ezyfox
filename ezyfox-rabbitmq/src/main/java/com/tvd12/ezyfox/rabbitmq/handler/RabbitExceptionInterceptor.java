package com.tvd12.ezyfox.rabbitmq.handler;

public interface RabbitExceptionInterceptor {
	
	void intercept(String cmd, Object requestData, Exception e);
	
}
