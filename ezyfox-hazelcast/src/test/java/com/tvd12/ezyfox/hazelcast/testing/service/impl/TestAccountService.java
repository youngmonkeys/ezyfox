package com.tvd12.ezyfox.hazelcast.testing.service.impl;

import com.hazelcast.core.HazelcastInstance;
import com.tvd12.ezyfox.hazelcast.service.EzySimpleAccountService;

public class TestAccountService extends EzySimpleAccountService {
	
	public TestAccountService() {
		super();
	}

	public TestAccountService(HazelcastInstance hazelcastInstance) {
		super(hazelcastInstance);
	}

}
