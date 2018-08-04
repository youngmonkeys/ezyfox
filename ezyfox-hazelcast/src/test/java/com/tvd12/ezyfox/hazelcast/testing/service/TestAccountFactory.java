package com.tvd12.ezyfox.hazelcast.testing.service;

import com.tvd12.ezyfox.hazelcast.entity.EzyAbstractAccount;
import com.tvd12.ezyfox.hazelcast.factory.EzyAbstractAccoutFactory;

public class TestAccountFactory extends EzyAbstractAccoutFactory {

	@Override
	protected EzyAbstractAccount newAccount() {
		return new TestAcount2();
	}
	
}
