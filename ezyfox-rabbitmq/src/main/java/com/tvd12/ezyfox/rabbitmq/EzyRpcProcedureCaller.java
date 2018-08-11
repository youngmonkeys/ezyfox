package com.tvd12.ezyfox.rabbitmq;

import com.tvd12.ezyfox.rabbitmq.entity.EzyRpcProcedure;
import com.tvd12.ezyfox.rabbitmq.exception.EzyRpcException;

public interface EzyRpcProcedureCaller {

	Object call(EzyRpcProcedure procedure) throws EzyRpcException;
	
}
