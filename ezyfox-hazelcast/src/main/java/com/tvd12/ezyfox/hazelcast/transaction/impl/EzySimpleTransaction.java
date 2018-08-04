package com.tvd12.ezyfox.hazelcast.transaction.impl;

import com.hazelcast.transaction.TransactionContext;
import com.tvd12.ezyfox.hazelcast.transaction.EzyTransaction;
import com.tvd12.ezyfox.util.EzyLoggable;

public class EzySimpleTransaction 
		extends EzyLoggable
		implements EzyTransaction {

	protected final TransactionContext context;
	
	public EzySimpleTransaction(TransactionContext context) {
		this.context = context;
	}
	
	@Override
	public void begin() {
		context.beginTransaction();
	}
	
	@Override
	public void commit() {
		context.commitTransaction();
	}
	
	@Override
	public void rollback() {
		context.rollbackTransaction();
	}
	
}
