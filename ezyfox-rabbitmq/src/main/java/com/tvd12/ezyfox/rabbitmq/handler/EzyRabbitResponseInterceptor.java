package com.tvd12.ezyfox.rabbitmq.handler;

public interface EzyRabbitResponseInterceptor {
	
	void intercept(String cmd, Object requestData, Object responseData);
	
}
