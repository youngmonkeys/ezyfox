package com.tvd12.ezyfox.hazelcast.service;

import com.hazelcast.core.HazelcastInstance;
import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.bean.annotation.EzyPostInit;
import com.tvd12.ezyfox.hazelcast.service.EzySimpleHazelcastMapService;

public abstract class EzyBeanSimpleHazelcastMapService<K,V> 
		extends EzySimpleHazelcastMapService<K,V> {

	@EzyAutoBind
	@Override
	public void setHazelcastInstance(HazelcastInstance hazelcastInstance) {
		super.setHazelcastInstance(hazelcastInstance);
	}
	
	@EzyPostInit
	@Override
	public void init() {
		super.init();
	}
}
