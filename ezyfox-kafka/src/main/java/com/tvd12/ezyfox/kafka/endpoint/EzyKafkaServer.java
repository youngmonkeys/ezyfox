package com.tvd12.ezyfox.kafka.endpoint;

import java.time.Duration;
import java.util.concurrent.ExecutorService;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;

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
	protected final ExecutorService executorService;
	
	@Setter
	protected EzyKafkaRecordsHandler recordsHandler;
	
	public EzyKafkaServer(
			Consumer consumer, 
			long poolTimeOut, ExecutorService executorService) {
		this.consumer = consumer;
		this.pollTimeOut = poolTimeOut;
		this.executorService = executorService;
	}
	
	@Override
	public void start() throws Exception {
		this.active = true;
		if(executorService == null)
			loop();
		else
			executorService.execute(this::loop);
	}
	
	protected void loop() {
		while(active) {
			pollRecords();
		}
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
		EzyProcessor.processWithLogException(() -> executorService.shutdown());
		EzyProcessor.processWithLogException(() -> consumer.close());
	}
	
}
