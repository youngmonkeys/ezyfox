package com.tvd12.ezyfox.hazelcast.service;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.HazelcastInstanceAware;
import com.tvd12.ezyfox.util.EzyInitable;
import com.tvd12.ezyfox.util.EzyLoggable;

public abstract class EzyAbstractHazelcastService 
		extends EzyLoggable
		implements HazelcastInstanceAware, EzyInitable {

	protected HazelcastInstance hazelcastInstance;
	
	public EzyAbstractHazelcastService() {
	}
	
	public EzyAbstractHazelcastService(HazelcastInstance hazelcastInstance) {
		this.setHazelcastInstance(hazelcastInstance);
	}
	
	@Override
	public void init() {
	}
	
	@Override
	public void setHazelcastInstance(HazelcastInstance hazelcastInstance) {
		this.hazelcastInstance = hazelcastInstance;
	}
	
}
