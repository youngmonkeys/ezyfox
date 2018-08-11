package com.tvd12.ezyfox.hazelcast.factory;

import com.tvd12.ezyfox.hazelcast.entity.EzyAccount;

public interface EzyAccountFactory {

	EzyAccount newAccount(long accountId, boolean acceptNegativeValue);
	
	default EzyAccount newAccount(long accountId) {
		return newAccount(accountId, false);
	}
	
}
