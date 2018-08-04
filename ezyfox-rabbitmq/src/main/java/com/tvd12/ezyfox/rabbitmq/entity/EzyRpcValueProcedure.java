package com.tvd12.ezyfox.rabbitmq.entity;

public interface EzyRpcValueProcedure extends EzyRpcProcedure {

	/**
	 * @return the return type
	 */
	@SuppressWarnings("rawtypes")
	Class getReturnType();
	
}
