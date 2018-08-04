package com.tvd12.ezyfox.hazelcast.factory;

import com.tvd12.ezyfox.hazelcast.entity.EzyMongoAccount;
import com.tvd12.ezyfox.hazelcast.factory.EzyAbstractAccoutFactory;

public class EzyMongoAccoutFactory extends EzyAbstractAccoutFactory {

	@Override
	protected EzyMongoAccount newAccount() {
		return new EzyMongoAccount();
	}
	
}
