package com.tvd12.ezyfox.kafka.codec;

public interface EzyKafkaDataSerializer {

	byte[] serialize(Object data);
	
}
