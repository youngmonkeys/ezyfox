package com.tvd12.ezyfox.rabbitmq;

import com.tvd12.ezyfox.rabbitmq.entity.EzyRpcProcedure;
import com.tvd12.ezyfox.rabbitmq.entity.EzyRpcResponseEntity;

public interface EzyRpcClient {

	/**
	 * 
	 * sync call
	 * 
	 * @param <T> the value type
	 * @return the returned value
	 */
	EzyRpcResponseEntity sync(EzyRpcProcedure procedure);
	
}
