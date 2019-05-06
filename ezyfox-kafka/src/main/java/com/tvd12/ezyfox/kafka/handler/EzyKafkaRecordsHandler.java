package com.tvd12.ezyfox.kafka.handler;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;

@SuppressWarnings({"rawtypes", "unchecked"})
public interface EzyKafkaRecordsHandler {

	void handleRecord(ConsumerRecord record);
	
	default void handleRecords(ConsumerRecords records) {
		records.forEach(record -> handleRecord((ConsumerRecord) record));
	}
	
}
