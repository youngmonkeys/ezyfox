package com.tvd12.ezyfox.kafka.serialization;

import java.util.Map;

import org.apache.kafka.common.serialization.Deserializer;

public class EzyDefaultDeserializer implements Deserializer<Object> {

	@Override
	public void configure(Map<String, ?> configs, boolean isKey) {
	}
	
	@Override
	public Object deserialize(String topic, byte[] data) {
		return data;
	}
	
	@Override
	public void close() {
	}
}
	
	

