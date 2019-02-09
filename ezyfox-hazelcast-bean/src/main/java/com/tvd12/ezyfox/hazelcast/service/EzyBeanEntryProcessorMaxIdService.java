package com.tvd12.ezyfox.hazelcast.service;

import com.hazelcast.core.HazelcastInstance;
import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;

@EzySingleton("maxIdService")
public class EzyBeanEntryProcessorMaxIdService extends EzyEntryProcessorMaxIdService {

	@EzyAutoBind
	public EzyBeanEntryProcessorMaxIdService(HazelcastInstance hazelcastInstance) {
		super(hazelcastInstance);
	}
	
}
