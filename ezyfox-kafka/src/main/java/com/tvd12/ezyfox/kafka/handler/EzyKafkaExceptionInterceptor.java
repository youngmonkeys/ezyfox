package com.tvd12.ezyfox.kafka.handler;

public interface EzyKafkaExceptionInterceptor {
	
	void intercept(String cmd, Object requestData, Exception e);
	
}
