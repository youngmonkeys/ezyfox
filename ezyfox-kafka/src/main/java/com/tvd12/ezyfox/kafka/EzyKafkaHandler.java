package com.tvd12.ezyfox.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import com.tvd12.ezyfox.kafka.codec.EzyKafkaDataCodec;
import com.tvd12.ezyfox.kafka.endpoint.EzyKafkaServer;
import com.tvd12.ezyfox.kafka.handler.EzyKafkaActionInterceptor;
import com.tvd12.ezyfox.kafka.handler.EzyKafkaRecordsHandler;
import com.tvd12.ezyfox.kafka.handler.EzyKafkaRequestHandlers;
import com.tvd12.ezyfox.util.EzyLoggable;
import com.tvd12.ezyfox.util.EzyStartable;
import com.tvd12.ezyfox.util.EzyStoppable;

import lombok.Setter;

@SuppressWarnings("rawtypes")
public class EzyKafkaHandler
		extends EzyLoggable
		implements EzyKafkaRecordsHandler, EzyStartable, EzyStoppable {

	@Setter
	protected EzyKafkaActionInterceptor actionInterceptor;
	
	protected final EzyKafkaServer server;
	protected final EzyKafkaDataCodec dataCodec;
	protected final EzyKafkaRequestHandlers requestHandlers;
	
	public EzyKafkaHandler(
			EzyKafkaServer server,
			EzyKafkaDataCodec dataCodec,
			EzyKafkaRequestHandlers requestHandlers) {
		this.server = server;
		this.server.setRecordsHandler(this);
		this.dataCodec = dataCodec;
		this.requestHandlers = requestHandlers;
	}
	
	@Override
	public void start() throws Exception {
		server.start();
	}
	
	@Override
	public void stop() {
		server.stop();
	}
	
	@Override
	public void handleRecord(ConsumerRecord record) {
		String cmd = record.topic();
        Object requestEntity = null;
        Object responseEntity = null;
        try
        {
        		byte[] requestBody = (byte[])record.value();
            requestEntity = dataCodec.deserialize(cmd, requestBody);
            if (actionInterceptor != null)
                actionInterceptor.intercept(cmd, requestEntity);
            responseEntity = requestHandlers.handle(cmd, requestEntity);
            if (actionInterceptor != null)
                actionInterceptor.intercept(cmd, requestEntity, responseEntity);
        }
        catch (Exception e) {
        		if (actionInterceptor != null)
                actionInterceptor.intercept(cmd, requestEntity, e);
        }
	}
	
}
