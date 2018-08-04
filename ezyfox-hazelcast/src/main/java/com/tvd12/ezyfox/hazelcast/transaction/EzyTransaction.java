package com.tvd12.ezyfox.hazelcast.transaction;

public interface EzyTransaction {

	void begin();
	
	void commit();
	
	void rollback();
	
}
