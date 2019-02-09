package com.tvd12.ezyfox.hazelcast.service;

import com.hazelcast.core.HazelcastInstance;
import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;

@EzySingleton("maxIdService")
public class EzyBeanTransactionalMaxIdService extends EzyTransactionalMaxIdService {

	@EzyAutoBind
	public EzyBeanTransactionalMaxIdService(HazelcastInstance hazelcastInstance) {
		super(hazelcastInstance);
	}
	
}
