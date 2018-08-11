package com.tvd12.ezyfox.rabbitmq.codec;

import java.util.ArrayList;
import java.util.List;

import com.tvd12.ezyfox.codec.EzyMessageSerializer;
import com.tvd12.ezyfox.rabbitmq.entity.EzyRpcProcedure;
import com.tvd12.ezyfox.util.EzyEntityBuilders;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class EzyRpcSimpleProcedureSerializer
		extends EzyEntityBuilders
		implements EzyRpcProcedureSerializer {

	protected EzyMessageSerializer messageSerializer;
	
	@Override
	public byte[] serialize(EzyRpcProcedure procedure) {
		return messageSerializer.serialize(toArray(procedure));
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected List toArray(EzyRpcProcedure proc) {
		List list = new ArrayList<>();
		list.add(proc.getParent());
		list.add(proc.getName());
		list.add(proc.getArguments());
		return list;
	}

}
