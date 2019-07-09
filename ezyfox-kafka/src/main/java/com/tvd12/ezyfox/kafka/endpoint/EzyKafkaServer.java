package com.tvd12.ezyfox.kafka.endpoint;

import java.time.Duration;
import java.util.function.Function;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;

import com.tvd12.ezyfox.concurrent.EzyThreadList;
import com.tvd12.ezyfox.kafka.handler.EzyKafkaRecordsHandler;
import com.tvd12.ezyfox.util.EzyDestroyable;
import com.tvd12.ezyfox.util.EzyLoggable;
import com.tvd12.ezyfox.util.EzyProcessor;
import com.tvd12.ezyfox.util.EzyStartable;
import com.tvd12.ezyfox.util.EzyStoppable;

import lombok.Setter;

@SuppressWarnings("rawtypes")
public class EzyKafkaServer 
		extends EzyLoggable
		implements EzyStartable, EzyStoppable, EzyDestroyable {
	
	protected final long pollTimeOut;
	protected final Consumer consumer;
	protected volatile boolean active;
	protected final EzyThreadList executorService;
	
	@Setter
	protected EzyKafkaRecordsHandler recordsHandler;
	
	public EzyKafkaServer(Consumer consumer, long poolTimeOut) {
		this(consumer, poolTimeOut, 0);
	}
	
	public EzyKafkaServer(
			Consumer consumer, long poolTimeOut, int threadPoolSize) {
		this(consumer, poolTimeOut, newExecutorServiceSupplier(threadPoolSize));
	}
	
	public EzyKafkaServer(
			Consumer consumer, 
			long poolTimeOut, 
			Function<Runnable, EzyThreadList> executorServiceSupplier) {
		this.consumer = consumer;
		this.pollTimeOut = poolTimeOut;
		this.executorService = newExecutorService(executorServiceSupplier);
	}
	
	protected EzyThreadList newExecutorService(
			Function<Runnable, EzyThreadList> executorServiceSupplier) {
		EzyThreadList answer = null;
		if(executorServiceSupplier != null)
			answer = executorServiceSupplier.apply(() -> loop());
		return answer;
	}
	
	protected static Function<Runnable, EzyThreadList> newExecutorServiceSupplier(
			int threadPoolSize) {
		if(threadPoolSize <= 0)
			return null;
		return t -> new EzyThreadList(threadPoolSize, t, "kafka-server");
	}
	
	@Override
	public void start() throws Exception {
		this.active = true;
		if(executorService == null)
			loop();
		else
			executorService.execute();
	}
	
	protected void loop() {
		while(active)
			pollRecords();
	}
	
	protected void pollRecords() {
		try {
			ConsumerRecords records = consumer.poll(Duration.ofMillis(pollTimeOut));
			recordsHandler.handleRecords(records);
		}
		catch(Exception e) {
			logger.warn("poll and handle records error", e);
		}
	}
	
	@Override
	public void stop() {
		this.active = false;
	}
	
	@Override
	public void destroy() {
		this.active = false;
		EzyProcessor.processWithLogException(() -> consumer.close());
	}
	
}
