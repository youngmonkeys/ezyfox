package com.tvd12.ezyfox.kafka.handler;

public interface EzyKafkaRequestInterceptor {
	
	void intercept(String cmd, Object requestData);
	
}
