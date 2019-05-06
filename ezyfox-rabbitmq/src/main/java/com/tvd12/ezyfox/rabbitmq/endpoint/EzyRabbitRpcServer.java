package com.tvd12.ezyfox.rabbitmq.endpoint;

import java.io.IOException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Delivery;
import com.rabbitmq.client.RpcServer;
import com.tvd12.ezyfox.builder.EzyBuilder;
import com.tvd12.ezyfox.rabbitmq.handler.EzyRabbitRpcCallHandler;

import lombok.Setter;

public class EzyRabbitRpcServer extends RpcServer {
	
	@Setter
	protected EzyRabbitRpcCallHandler callHandler;
	protected final String exchange;
	protected final String replyRoutingKey;
	protected final String queueName;
	
	public EzyRabbitRpcServer(
			Channel channel, 
			String exchange,
			String replyRoutingKey) throws IOException {
		this(channel, exchange, replyRoutingKey, null);
	}
	
	public EzyRabbitRpcServer(
			Channel channel, 
			String exchange, 
			String replyRoutingKey,
			String queueName) throws IOException {
		super(channel, queueName);
		this.exchange = exchange;
		this.replyRoutingKey = replyRoutingKey;
		this.queueName = queueName;
	}

	@Override
	public void processRequest(Delivery request)
	        throws IOException {
	        AMQP.BasicProperties requestProperties = request.getProperties();
	        String correlationId = requestProperties.getCorrelationId();
	        String responseRoutingKey = requestProperties.getReplyTo();
	        if(responseRoutingKey == null)
	        		responseRoutingKey = replyRoutingKey; 
	        if (correlationId != null) {
	            AMQP.BasicProperties.Builder replyPropertiesBuilder = new AMQP.BasicProperties.Builder();
	            byte[] replyBody = handleCall(request, replyPropertiesBuilder);
	            replyPropertiesBuilder.correlationId(correlationId);
	            AMQP.BasicProperties replyProperties = replyPropertiesBuilder.build();
	            getChannel().basicPublish(exchange, responseRoutingKey, replyProperties, replyBody);
	        } else {
	            handleCast(request);
	        }
	}
	
	protected byte[] handleCall(
			Delivery request, 
			BasicProperties.Builder replyPropertiesBuilder) {
		byte[] answer = callHandler.handleCall(request, replyPropertiesBuilder);
		return answer;
	}
	
	public void stop() throws Exception {
		this.callHandler = null;
		this.close();
	}
	
	public static Builder builder() {
		return new Builder();
	}
	
	public static class Builder implements EzyBuilder<EzyRabbitRpcServer> {
		protected Channel channel = null;
		protected String exchange = "";
		protected String replyRoutingKey = "";
		protected String queueName = null;
		protected EzyRabbitRpcCallHandler callHandler = null;
		
		public Builder channel(Channel channel) {
			this.channel = channel;
			return this;
		}
		
		public Builder exchange(String exchange) {
			this.exchange = exchange;
			return this;
		}
		
		public Builder queueName(String queueName) {
			this.queueName = queueName;
			return this;
		}
		
		public Builder replyRoutingKey(String replyRoutingKey) {
			this.replyRoutingKey = replyRoutingKey;
			return this;
		}
		
		public Builder callHandler(EzyRabbitRpcCallHandler callHandler) {
			this.callHandler = callHandler;
			return this;
		}
		
		@Override
		public EzyRabbitRpcServer build() {
			try {
				EzyRabbitRpcServer server = new EzyRabbitRpcServer(
						channel, exchange, replyRoutingKey, queueName);
				server.setCallHandler(callHandler);
				return server;
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		
	}
	
}
