package com.tvd12.ezyfox.rabbitmq.entity;

import com.tvd12.ezyfox.entity.EzyArray;

public interface EzyRpcProcedure {

	/**
	 * @return the procedure name
	 */
	String getName();
	
	/**
	 * @return the parent class or namespace 
	 */
	String getParent();
	
	/**
	 * @return the arguments
	 */
	EzyArray getArguments();
	
}
