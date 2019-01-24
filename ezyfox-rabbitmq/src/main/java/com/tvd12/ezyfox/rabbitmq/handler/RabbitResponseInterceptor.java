package com.tvd12.ezyfox.rabbitmq.handler;

public interface RabbitResponseInterceptor {
	
	void intercept(String cmd, Object requestData, Object responseData);
	
}
