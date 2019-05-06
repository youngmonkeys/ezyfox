package com.tvd12.ezyfox.kafka.endpoint;

import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import com.tvd12.ezyfox.util.EzyLoggable;

@SuppressWarnings({"rawtypes", "unchecked"})
public class EzyKafkaClient extends EzyLoggable {

	protected final Producer producer;
	
	public EzyKafkaClient(Producer producer) {
		this.producer = producer;
	}
	
	public void send(String cmd, byte[] message) throws Exception {
		ProducerRecord record = new ProducerRecord<>(cmd, message);
		producer.send(record);
	}
	
}
