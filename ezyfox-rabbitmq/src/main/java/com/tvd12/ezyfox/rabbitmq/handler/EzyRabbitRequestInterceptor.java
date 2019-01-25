package com.tvd12.ezyfox.rabbitmq.handler;

public interface EzyRabbitRequestInterceptor {
	
	void intercept(String cmd, Object requestData);
	
}
