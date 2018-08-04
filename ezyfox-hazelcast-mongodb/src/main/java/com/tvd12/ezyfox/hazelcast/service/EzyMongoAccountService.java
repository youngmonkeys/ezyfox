package com.tvd12.ezyfox.hazelcast.service;

import com.hazelcast.core.HazelcastInstance;
import com.tvd12.ezyfox.hazelcast.service.EzySimpleAccountService;

public class EzyMongoAccountService extends EzySimpleAccountService {
	
	public EzyMongoAccountService() {
	}

	public EzyMongoAccountService(HazelcastInstance hazelcastInstance) {
		super(hazelcastInstance);
	}

}
