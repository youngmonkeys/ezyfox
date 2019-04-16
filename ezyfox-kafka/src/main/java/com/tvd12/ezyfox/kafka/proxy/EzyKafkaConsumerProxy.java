package com.tvd12.ezyfox.kafka.proxy;

import org.apache.kafka.clients.consumer.Consumer;

@SuppressWarnings("rawtypes")
public class EzyKafkaConsumerProxy {

	protected final long poolTimeOut;
	protected final Consumer consumer;
	protected volatile boolean active;
	
	public EzyKafkaConsumerProxy(Consumer consumer, long poolTimeOut) {
		this.consumer = consumer;
		this.poolTimeOut = poolTimeOut;
	}
	
	public void loop() {
		consumer.poll(poolTimeOut)
	}
	
}
