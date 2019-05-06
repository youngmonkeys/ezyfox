package com.tvd12.ezyfox.kafka.codec;

public interface EzyKafkaDataDeserializer {

	Object deserialize(String cmd, byte[] request);
	
}
