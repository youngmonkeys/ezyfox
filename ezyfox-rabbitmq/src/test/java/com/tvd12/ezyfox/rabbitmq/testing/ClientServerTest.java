package com.tvd12.ezyfox.rabbitmq.testing;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.tvd12.ezyfox.binding.EzyBindingContext;
import com.tvd12.ezyfox.binding.impl.EzySimpleBindingContext;
import com.tvd12.ezyfox.message.handler.EzyMessageHandler;
import com.tvd12.ezyfox.rabbitmq.EzyRabbitClient;
import com.tvd12.ezyfox.rabbitmq.EzyRabbitServer;
import com.tvd12.ezyfox.rabbitmq.EzyRabbitSimpleServer;
import com.tvd12.ezyfox.rabbitmq.message.EzyRabbitMessage;
import com.tvd12.ezyfox.rabbitmq.message.EzyRabbitMessageBuilder;
import com.tvd12.ezyfox.rabbitmq.testing.entity.HelloMessage;
import com.tvd12.ezyfox.util.EzyExceptionHandler;

public class ClientServerTest {

	@SuppressWarnings("unused")
	private EzyBindingContext bindingContext;
	
	public ClientServerTest() {
		this.bindingContext = newBindingContext();
	}
	
	public static void main(String[] args) throws Exception {
		new ClientServerTest().start();
	}
	
	private void start() throws Exception {
		EzyRabbitServer server = newServer();
		EzyRabbitClient client = newClient();
		server.start();
		EzyRabbitMessage message = EzyRabbitMessageBuilder.messageBuilder()
				.body(new HelloMessage(1))
				.build();
		client.send(message);
		System.out.println("message sent");
	}
	
	private EzyRabbitClient newClient() throws Exception {
//		EzyMarshaller marshaller = bindingContext.newMarshaller();
//		Channel channel = createChannel();
//		EzyRabbitSimpleClient client = new EzyRabbitSimpleClient();
//		client.setChannel(channel);
//		client.setMarshaller(marshaller);
//		client.setMessageSerializer(new MsgPackSimpleSerializer());
//		return client;
		return null;
	}
	
	private EzyRabbitServer newServer() throws Exception {
//		EzyUnmarshaller unmarshaller = bindingContext.newUnmarshaller();
//		Channel channel = createChannel();
		EzyRabbitSimpleServer server = new EzyRabbitSimpleServer();
//		server.setChannel(channel);
//		server.setUnmarshaller(unmarshaller);
//		server.setMessageConfig(EzyRabbitMessageConfigBuilder.messageConfigBuilder()
//				.bodyType(HelloMessage.class)
//				.build());
//		server.setMessageDeserializer(new MsgPackSimpleDeserializer());
//		server.setServerConfig(EzyRabbitServerConfigBuilder.serverConfigBuilder()
//				.queue("example-queue")
//				.build());
//		server.setStartService(EzyExecutors.newSingleThreadExecutor("rabbit-server"));
		server.addMessagesHandler(new EzyMessageHandler<EzyRabbitMessage>() {
			@Override
			public void handleMessage(EzyRabbitMessage message) {
				System.out.println("received message: " + message);
			}
		});
		server.addExceptionHandler(new EzyExceptionHandler() {
			
			@Override
			public void handleException(Thread thread, Throwable throwable) {
				throwable.printStackTrace();
			}
		});
		return server;
	}
	
	private EzyBindingContext newBindingContext() {
		return EzySimpleBindingContext.builder()
				.scan("com.tvd12.ezyfox.rabbitmq.testing.entity")
				.build();
	}
	
	@SuppressWarnings("unused")
	private Channel createChannel() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare("example-queue", false, false, false, null);
        return channel;
	}
	
}
