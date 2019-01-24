package com.tvd12.ezyfox.rabbitmq.codec;

public interface EzyRabbitDataDeserializer {

	Object deserialize(String cmd, byte[] request);
	
}
