package com.tvd12.ezyfox.rabbitmq.entity;

import com.tvd12.ezyfox.entity.EzyArray;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public abstract class EzyRpcAbstractProcedure implements EzyRpcProcedure {

	protected String name;
	protected String parent;
	protected EzyArray arguments;

}
