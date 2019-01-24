package com.tvd12.ezyfox.rabbitmq.codec;

public interface EzyRabbitDataSerializer {

	byte[] serialize(Object data);
	
}
