package com.tvd12.ezyfox.rabbitmq.handler;

public interface RabbitActionInterceptor extends
		RabbitRequestInterceptor, 
		RabbitResponseInterceptor, 
		RabbitExceptionInterceptor{

}
