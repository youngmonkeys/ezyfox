package com.tvd12.ezyfox.kafka.handler;

public interface EzyKafkaActionInterceptor extends
		EzyKafkaRequestInterceptor, 
		EzyKafkaResponseInterceptor, 
		EzyKafkaExceptionInterceptor{

}
