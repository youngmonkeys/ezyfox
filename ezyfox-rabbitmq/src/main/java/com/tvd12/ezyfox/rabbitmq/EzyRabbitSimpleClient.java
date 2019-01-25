package com.tvd12.ezyfox.rabbitmq;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.tvd12.ezyfox.rabbitmq.codec.EzyRabbitDataSerializer;
import com.tvd12.ezyfox.util.EzyLoggable;

public class EzyRabbitSimpleClient 
		extends EzyLoggable 
		implements EzyRabbitClient {

	protected Channel channel;
	protected String exchange;
	protected String routingKey;
	protected EzyRabbitDataSerializer dataSerializer;
	
	public EzyRabbitSimpleClient(
			Channel channel, 
			String exchange,
			String routingKey,
			EzyRabbitDataSerializer dataSerializer) {
		this.channel = channel;
		this.exchange = exchange;
		this.routingKey = routingKey;
		this.dataSerializer = dataSerializer;
	}
	
	@Override
	public void shutdown() {
	}
	
	@Override
	public void send(String cmd, Object data) {
		try {
			byte[] body = dataSerializer.serialize(data);
			BasicProperties properties = new BasicProperties.Builder()
					.type(cmd)
					.build();
			channel.basicPublish(
					exchange, 
					routingKey, properties, body
			);
		}
		catch(Exception e) {
			throw new IllegalArgumentException("can't send cmd: " + cmd + " with data: " + data, e);
		}
	}
	
}
