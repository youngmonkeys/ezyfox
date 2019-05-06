package com.tvd12.ezyfox.kafka.handler;

public interface EzyKafkaRequestHandler<I, O> {
	
    O handle(I request) throws Exception;
    
}
