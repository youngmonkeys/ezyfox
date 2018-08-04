package com.tvd12.ezyfox.rabbitmq.entity;

public interface EzyRpcResponseEntity {

	<T> T getBody();
	
	EzyRpcHeaders getHeaders();
}
