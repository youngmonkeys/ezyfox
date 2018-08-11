package com.tvd12.ezyfox.kafka;

import org.apache.kafka.clients.consumer.Consumer;

@SuppressWarnings("rawtypes")
public interface EzyKafkaConsumerAware {

	void setConsumer(Consumer consumer);
	
}
