package com.tvd12.ezyfox.rabbitmq.handler;

public interface RabbitRequestInterceptor {
	
	void intercept(String cmd, Object requestData);
	
}
