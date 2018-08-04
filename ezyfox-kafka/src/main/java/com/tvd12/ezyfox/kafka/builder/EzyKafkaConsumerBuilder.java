package com.tvd12.ezyfox.kafka.builder;

import org.apache.kafka.clients.consumer.Consumer;

import com.tvd12.ezyfox.builder.EzyBuilder;
import com.tvd12.ezyfox.kafka.builder.impl.EzyKafkaSimpleConsumerBuilder;

@SuppressWarnings("rawtypes")
public interface EzyKafkaConsumerBuilder
		extends EzyKafkaEndpointBuilder<EzyKafkaConsumerBuilder>, EzyBuilder<Consumer> {
	
	static EzyKafkaConsumerBuilder consumerBuilder() {
		return new EzyKafkaSimpleConsumerBuilder();
	}
	
}
