package com.tvd12.ezyfox.rabbitmq.handler;

import com.rabbitmq.client.Delivery;
import com.rabbitmq.client.AMQP.BasicProperties;

public interface EzyRabbitRpcCallHandler {
	
	void handleFire(
			BasicProperties requestProperties,
			byte[] requestBody);
	
	byte[] handleCall(
			BasicProperties requestProperties,
			byte[] requestBody, 
			BasicProperties.Builder replyPropertiesBuilder);
	
	default void handleFire(Delivery request) {
		handleFire(
				request.getProperties(),
				request.getBody());
	}

	default byte[] handleCall(
			Delivery request, 
			BasicProperties.Builder replyPropertiesBuilder) {
		return handleCall(
				request.getProperties(),
				request.getBody(), replyPropertiesBuilder);
	}
	
}
