package com.tvd12.ezyfox.rabbitmq;

import java.io.IOException;
import java.util.concurrent.ExecutorService;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.ShutdownSignalException;
import com.tvd12.ezyfox.pattern.EzyDataHandler;
import com.tvd12.ezyfox.pattern.EzyDataHandlers;
import com.tvd12.ezyfox.rabbitmq.codec.EzyRabbitDataDeserializer;
import com.tvd12.ezyfox.util.EzyLoggable;

public class EzyRabbitSimpleServer
		extends EzyLoggable
		implements EzyRabbitServer, Consumer, Runnable {

	protected Channel channel;
	protected ExecutorService startService;
	protected EzyRabbitServerConfig serverConfig;
	protected EzyRabbitDataDeserializer dataDeserializer;
	protected EzyDataHandlers requestHandlers;
	
	public EzyRabbitSimpleServer(
			EzyRabbitServerConfig serverConfig,
			Channel channel,
			EzyRabbitDataDeserializer dataDeserializer,
			EzyDataHandlers requestHandlers,
			ExecutorService startService) {
	}
	
	@Override
	public void run() {
		try {
			start0();
		}
		catch(Exception e) {
			throw new IllegalStateException("can't start server", e);
		}
	}
	
	@Override
	public void start() throws Exception {
		if(startService == null)
			start0();
		else
			startService.execute(this);
	}
	
	protected void start0() throws Exception {
		channel.basicConsume(
				serverConfig.getQueue(), 
				serverConfig.isAutoAck(), 
				serverConfig.getConsumerTag(), 
				serverConfig.isNoLocal(), 
				serverConfig.isExclusive(), 
				serverConfig.getArguments(),
				this
		);
	}
	
	@Override
	public void handleConsumeOk(String consumerTag) {
	}

	@Override
	public void handleCancelOk(String consumerTag) {
	}

	@Override
	public void handleCancel(String consumerTag) throws IOException {
	}

	@SuppressWarnings({"rawtypes", "unchecked"})
	@Override
	public void handleDelivery(String consumerTag,
            Envelope envelope,
            AMQP.BasicProperties properties,
            byte[] body) throws IOException {
		String cmd = properties.getType();
		EzyDataHandler handler = requestHandlers.getHandler(cmd);
		try {
			Object requestData = dataDeserializer.deserialize(cmd, body);
			handler.handleData(requestData);
		}
		catch(Exception e) {
			handler.handleException(Thread.currentThread(), e);
		}
	}

	@Override
	public void handleShutdownSignal(String consumerTag, ShutdownSignalException sig) {
	}

	@Override
	public void handleRecoverOk(String consumerTag) {
	}
	
	@Override
	public void shutdown() {
		startService.shutdown();
	}

	
}
