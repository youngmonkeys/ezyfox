package com.tvd12.ezyfox.rabbitmq.handler;

import com.rabbitmq.client.Delivery;
import com.rabbitmq.client.AMQP.BasicProperties;

public interface EzyRabbitRpcCallHandler {

	default byte[] handleCall(
			Delivery request, 
			BasicProperties.Builder replyPropertiesBuilder) {
		return handleCall(
				request.getProperties(),
				request.getBody(), replyPropertiesBuilder);
	}
	
	default byte[] handleCall(
			BasicProperties requestProperties,
			byte[] requestBody, 
			BasicProperties.Builder replyPropertiesBuilder) {
		return new byte[0];
	}
	
}
