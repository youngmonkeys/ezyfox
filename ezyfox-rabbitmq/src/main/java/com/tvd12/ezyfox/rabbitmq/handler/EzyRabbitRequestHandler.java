package com.tvd12.ezyfox.rabbitmq.handler;

public interface EzyRabbitRequestHandler<I, O> {
	
    O handle(I request) throws Exception;
    
}
