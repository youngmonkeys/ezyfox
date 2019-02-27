package com.tvd12.ezyfox.rabbitmq.handler;

import com.rabbitmq.client.AMQP;

public interface EzyRabbitResponseConsumer {

	void consume(AMQP.BasicProperties properties, byte[] responseBody);
	
}
