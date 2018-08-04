package com.tvd12.ezyfox.rabbitmq.codec;

import com.tvd12.ezyfox.codec.EzyMessageDeserializer;
import com.tvd12.ezyfox.entity.EzyArray;
import com.tvd12.ezyfox.rabbitmq.entity.EzyRpcProcedure;
import com.tvd12.ezyfox.rabbitmq.entity.EzyRpcSimpleProcedure;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class EzyRpcSimpleProcedureDeserializer implements EzyRpcProcedureDeserializer {

	protected EzyMessageDeserializer deserializer;
	
	@Override
	public EzyRpcProcedure deserialize(byte[] value) {
		return deserialize(toArray(value));
	}
	
	protected EzyRpcProcedure deserialize(EzyArray array) {
		EzyRpcSimpleProcedure procedure = new EzyRpcSimpleProcedure();
		procedure.setParent(array.get(0, String.class));
		procedure.setName(array.get(1, String.class));
		procedure.setArguments(array.get(2, EzyArray.class));
		return procedure; 
	}
	
	protected EzyArray toArray(byte[] value) {
		return deserializer.deserialize(value);
	}
	
}
