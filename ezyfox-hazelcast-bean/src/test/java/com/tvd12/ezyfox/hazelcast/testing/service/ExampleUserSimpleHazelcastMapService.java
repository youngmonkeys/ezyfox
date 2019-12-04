package com.tvd12.ezyfox.hazelcast.testing.service;

import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyfox.hazelcast.testing.entity.ExampleUser;
import com.tvd12.ezyfox.hazelcast.service.EzyBeanSimpleHazelcastMapService;

@EzySingleton
public class ExampleUserSimpleHazelcastMapService 
		extends EzyBeanSimpleHazelcastMapService<String, ExampleUser> {

	@Override
	protected String getMapName() {
		return "hazelcast-bean-simple-hazelcast-user";
	}
}
