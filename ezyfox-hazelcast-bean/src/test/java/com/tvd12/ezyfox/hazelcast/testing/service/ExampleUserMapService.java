package com.tvd12.ezyfox.hazelcast.testing.service;

import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyfox.hazelcast.service.EzyBeanAbstractMapService;
import com.tvd12.ezyfox.hazelcast.testing.entity.ExampleUser;

@EzySingleton
public class ExampleUserMapService extends EzyBeanAbstractMapService<String, ExampleUser> {

	@Override
	protected String getMapName() {
		return "hazelcast-bean-user-map";
	}

	
	
}
