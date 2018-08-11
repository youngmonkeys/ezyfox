package com.tvd12.ezyfox.rabbitmq.codec;

import com.tvd12.ezyfox.rabbitmq.entity.EzyRpcProcedure;

public interface EzyRpcProcedureSerializer {

	byte[] serialize(EzyRpcProcedure procedure);
	
}
