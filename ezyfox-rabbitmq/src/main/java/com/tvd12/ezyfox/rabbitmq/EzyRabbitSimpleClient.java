package com.tvd12.ezyfox.rabbitmq;

import com.rabbitmq.client.Channel;
import com.tvd12.ezyfox.codec.EzyEntitySerializer;
import com.tvd12.ezyfox.rabbitmq.message.EzyRabbitMessage;
import com.tvd12.ezyfox.util.EzyLoggable;

public class EzyRabbitSimpleClient 
		extends EzyLoggable 
		implements EzyRabbitClient {

	protected final Channel channel;
	protected final EzyEntitySerializer entitySerializer;
	
	public EzyRabbitSimpleClient(
			Channel channel, 
			EzyEntitySerializer entitySerializer) {
		this.channel = channel;
		this.entitySerializer = entitySerializer;
	}
	
	@Override
	public void shutdown() {
	}
	
	@Override
	public void send(EzyRabbitMessage message) {
		try {
			byte[] body = object2bytes(message.getBody());
			channel.basicPublish(
					message.getExchange(), 
					message.getRoutingKey(), 
					message.isMandatory(), 
					message.isImmediate(), 
					message.getProperties(),
					body
			);
		}
		catch(Exception e) {
			throw new IllegalArgumentException("can't send message: " + message, e);
		}
	}
	
	protected byte[] object2bytes(Object object) {
		byte[] bytes = entitySerializer.serialize(object);
		return bytes;
	}
	
}
