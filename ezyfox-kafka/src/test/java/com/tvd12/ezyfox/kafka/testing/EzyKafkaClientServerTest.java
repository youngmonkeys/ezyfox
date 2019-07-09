package com.tvd12.ezyfox.kafka.testing;

import java.util.Collections;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.producer.Producer;

import com.tvd12.ezyfox.binding.EzyBindingContext;
import com.tvd12.ezyfox.binding.EzyMarshaller;
import com.tvd12.ezyfox.binding.EzyUnmarshaller;
import com.tvd12.ezyfox.codec.EzyEntityCodec;
import com.tvd12.ezyfox.codec.EzyMessageDeserializer;
import com.tvd12.ezyfox.codec.EzyMessageSerializer;
import com.tvd12.ezyfox.codec.MsgPackSimpleDeserializer;
import com.tvd12.ezyfox.codec.MsgPackSimpleSerializer;
import com.tvd12.ezyfox.concurrent.EzyThreadList;
import com.tvd12.ezyfox.identifier.EzyIdFetchers;
import com.tvd12.ezyfox.identifier.EzySimpleIdFetcherImplementer;
import com.tvd12.ezyfox.kafka.EzyKafkaCaller;
import com.tvd12.ezyfox.kafka.EzyKafkaHandler;
import com.tvd12.ezyfox.kafka.codec.EzyKafkaBytesDataCodec;
import com.tvd12.ezyfox.kafka.codec.EzyKafkaBytesEntityCodec;
import com.tvd12.ezyfox.kafka.codec.EzyKafkaDataCodec;
import com.tvd12.ezyfox.kafka.endpoint.EzyKafkaClient;
import com.tvd12.ezyfox.kafka.endpoint.EzyKafkaServer;
import com.tvd12.ezyfox.kafka.handler.EzyKafkaRequestHandler;
import com.tvd12.ezyfox.kafka.handler.EzyKafkaRequestHandlers;
import com.tvd12.ezyfox.kafka.testing.entity.KafkaChatMessage;
import com.tvd12.ezyfox.message.EzyMessageIdFetchers;
import com.tvd12.test.base.BaseTest;

@SuppressWarnings("rawtypes")
public class EzyKafkaClientServerTest extends BaseTest {
	
	protected EzyMarshaller marshaller;
	protected EzyUnmarshaller unmarshaller;
	protected EzyMessageSerializer messageSerializer;
	protected EzyMessageDeserializer messageDeserializer;
	protected final static String TOPIC = "my-example-topic";
	
	public EzyKafkaClientServerTest() {
		EzyBindingContext bindingContext = newBindingContext();
		marshaller = bindingContext.newMarshaller();
		unmarshaller = bindingContext.newUnmarshaller();
		messageSerializer = newMessageSerializer();
		messageDeserializer = newMessageDeserializer();
	}
	
	public static void main(String[] args) throws Exception {
		new EzyKafkaClientServerTest().run();
	}
	
	private void run() throws Exception {
		EzyKafkaCaller client = newClient();
		runClient(client, 5);
		runServer();
		Thread.sleep(3000L);
	}
	
	private EzyKafkaHandler newServer() {
		Consumer consumer = newConsumer();
		EzyKafkaServer server = new EzyKafkaServer(consumer, 100, t -> new EzyThreadList(1, t, "test-kafka-server"));
		EzyKafkaDataCodec dataCodec = EzyKafkaBytesDataCodec.builder()
				.unmarshaller(unmarshaller)
				.messageDeserializer(messageDeserializer)
				.mapRequestType(TOPIC, KafkaChatMessage.class)
				.build();
		EzyKafkaRequestHandlers requestHandlers = new EzyKafkaRequestHandlers();
		requestHandlers.addHandler(TOPIC, new EzyKafkaRequestHandler<KafkaChatMessage, Boolean>() {
					@Override
					public Boolean handle(KafkaChatMessage message) throws Exception {
						System.out.println("GREAT! We have just received message: " + message);
						return Boolean.TRUE;
					}
				});
		EzyKafkaHandler handler = new EzyKafkaHandler(server, dataCodec, requestHandlers);
		return handler;
	}
	
	private EzyKafkaCaller newClient() {
		EzySimpleIdFetcherImplementer.setDebug(true);
		Producer producer = newProducer();
		EzyKafkaClient client = new EzyKafkaClient(producer);
		EzyEntityCodec entityCodec = EzyKafkaBytesEntityCodec.builder()
				.marshaller(marshaller)
				.messageSerializer(messageSerializer)
				.build();
		EzyKafkaCaller caller = new EzyKafkaCaller(client, entityCodec);
		return caller;
	}
	
	@SuppressWarnings("unchecked")
	private Consumer newConsumer() {
		Consumer consumer = TestUtil.newConsumer();
		consumer.subscribe(Collections.singletonList(TOPIC));
		return consumer;
	}
	
	private Producer newProducer() {
		return TestUtil.newProducer();
	}

	private EzyBindingContext newBindingContext() {
		return EzyBindingContext.builder()
				.scan("com.tvd12.ezyfox.kafka.testing.entity")
				.build();
	}
	
	protected EzyIdFetchers newMessageIdFetchers() {
		return EzyMessageIdFetchers.builder()
				.scan("com.tvd12.ezyfox.kafka.testing.entity")
				.build();
	}
	
	protected EzyMessageSerializer newMessageSerializer() {
		return new MsgPackSimpleSerializer();
	}
	
	protected EzyMessageDeserializer newMessageDeserializer() {
		return new MsgPackSimpleDeserializer();
	}
	
	private void runServer() throws Exception {
		EzyKafkaHandler server = newServer();
		server.start();
	}
	
	private void runClient(EzyKafkaCaller client, int sendMessageCount) throws Exception {
		long time = System.currentTimeMillis();
		for (long index = time; index < time + sendMessageCount; index++) {
			KafkaChatMessage message = new KafkaChatMessage(index, "Meessage#" + index);
			client.send(TOPIC, message);
			long elapsedTime = System.currentTimeMillis() - time;
			System.out.printf("sent record(value=%s), time=%d\n", message, elapsedTime);

		}
	}
	
	
}
