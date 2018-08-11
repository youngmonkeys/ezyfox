package com.tvd12.ezyfox.hazelcast.transaction;

import com.tvd12.ezyfox.function.EzyExceptionApply;

public interface EzyApplyTransaction<T> extends EzyTransaction {
	
	void apply(EzyExceptionApply<T> func) throws Exception;
	
}
