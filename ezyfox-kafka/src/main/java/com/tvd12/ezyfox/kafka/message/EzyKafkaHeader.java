package com.tvd12.ezyfox.kafka.message;

public interface EzyKafkaHeader {

	String getKey();
	
	<T> T getValue();
	
}
