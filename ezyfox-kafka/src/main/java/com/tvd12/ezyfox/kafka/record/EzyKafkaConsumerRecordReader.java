package com.tvd12.ezyfox.kafka.record;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import com.tvd12.ezyfox.kafka.message.EzyKafkaMessage;
import com.tvd12.ezyfox.kafka.message.EzyKafkaMessageConfig;

@SuppressWarnings("rawtypes")
public interface EzyKafkaConsumerRecordReader {

	EzyKafkaMessage read(ConsumerRecord record, EzyKafkaMessageConfig config);
	
}
