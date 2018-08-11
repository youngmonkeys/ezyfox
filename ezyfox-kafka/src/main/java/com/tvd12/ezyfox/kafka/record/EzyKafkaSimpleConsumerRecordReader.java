package com.tvd12.ezyfox.kafka.record;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import com.tvd12.ezyfox.binding.EzyUnmarshaller;
import com.tvd12.ezyfox.binding.EzyUnmarshallerAware;
import com.tvd12.ezyfox.kafka.message.EzyKafkaMessage;
import com.tvd12.ezyfox.kafka.message.EzyKafkaMessageBuilder;
import com.tvd12.ezyfox.kafka.message.EzyKafkaMessageConfig;
import com.tvd12.ezyfox.util.EzyLoggable;

import lombok.Setter;

@Setter
@SuppressWarnings("rawtypes")
public class EzyKafkaSimpleConsumerRecordReader
		extends EzyLoggable 
		implements EzyKafkaConsumerRecordReader, EzyUnmarshallerAware {

	protected EzyUnmarshaller unmarshaller;
	
	@Override
	public EzyKafkaMessage read(ConsumerRecord record, EzyKafkaMessageConfig config) {
		Object key = unmarshalObject0(record.key(), config.getKeyType());
		Object value = unmarshalObject0(record.value(), config.getValueType());
		return newMessageBuilder()
				.key(key)
				.value(value)
				.topic(record.topic())
				.partition(record.partition())
				.timestamp(record.timestamp())
				.headers(record.headers().toArray())
				.build();
	}
	
	protected EzyKafkaMessageBuilder newMessageBuilder() {
		return EzyKafkaMessageBuilder.messageBuilder();
	}
	
	protected Object unmarshalObject0(Object object, Class<?> type) {
		return object == null ? null : unmarshalObject(object, type);
	}
	
	protected Object unmarshalObject(Object object, Class<?> type) {
		return unmarshaller.unmarshal(object, type);
	}
}
