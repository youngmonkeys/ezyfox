package com.tvd12.ezyfox.rabbitmq.handler;

public interface EzyRabbitActionInterceptor extends
		EzyRabbitRequestInterceptor, 
		EzyRabbitResponseInterceptor, 
		EzyRabbitExceptionInterceptor{

}
