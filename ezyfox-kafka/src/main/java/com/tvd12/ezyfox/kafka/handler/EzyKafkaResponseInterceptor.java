package com.tvd12.ezyfox.kafka.handler;

public interface EzyKafkaResponseInterceptor {
	
	void intercept(String cmd, Object requestData, Object responseData);
	
}
