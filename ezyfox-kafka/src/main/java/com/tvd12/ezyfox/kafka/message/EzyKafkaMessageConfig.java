package com.tvd12.ezyfox.kafka.message;

public interface EzyKafkaMessageConfig {

	Class<?> getKeyType();
	
	Class<?> getValueType();
	
}
