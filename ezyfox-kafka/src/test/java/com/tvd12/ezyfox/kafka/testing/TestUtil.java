package com.tvd12.ezyfox.kafka.testing;

import java.util.Properties;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;

public final class TestUtil {

	public final static String BOOTSTRAP_SERVERS = "localhost:9092";
	
	private TestUtil() {
	}
	
	@SuppressWarnings("rawtypes")
	public static Producer newProducer() {
		Properties properties = new Properties();
		properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
		properties.put(ProducerConfig.CLIENT_ID_CONFIG, "KafkaExampleProducer");
		properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "com.tvd12.ezyfox.kafka.serialization.EzyDefaultSerializer");
		properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "com.tvd12.ezyfox.kafka.serialization.EzyDefaultSerializer");
		return new KafkaProducer<>(properties);
	}
	
	@SuppressWarnings("rawtypes")
	public static Consumer newConsumer() {
		Properties properties = new Properties();
		properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
		properties.put(ConsumerConfig.GROUP_ID_CONFIG, "KafkaExampleConsumer");
		properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "com.tvd12.ezyfox.kafka.serialization.EzyDefaultDeserializer");
		properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "com.tvd12.ezyfox.kafka.serialization.EzyDefaultDeserializer");
		return new KafkaConsumer<>(properties);
	}
}
