package com.tvd12.ezyfox.hazelcast.testing;

import org.testng.annotations.Test;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.tvd12.ezyfox.bean.EzyBeanContext;
import com.tvd12.ezyfox.hazelcast.EzyBeanHazelcastFactory;
import com.tvd12.ezyfox.hazelcast.service.EzyBeanEntryProcessorMaxIdService;
import com.tvd12.ezyfox.hazelcast.service.EzyBeanTransactionalMaxIdService;
import com.tvd12.test.base.BaseTest;

public class EzyBeanHazelcastFactoryTest extends BaseTest {

	@Test
	public void test() {
		HazelcastInstance hazelcastInstance = Hazelcast.newHazelcastInstance();
		EzyBeanContext beanContext = EzyBeanContext.builder()
				.addSingleton("hazelcastInstance", hazelcastInstance)
				.scan("com.tvd12.ezyfox.hazelcast.testing.mapstore")
				.scan("com.tvd12.ezyfox.hazelcast.testing.service")
				.addSingletonClass(EzyBeanTransactionalMaxIdService.class)
				.addSingletonClass(EzyBeanEntryProcessorMaxIdService.class)
				.build();
		EzyBeanHazelcastFactory factory = new EzyBeanHazelcastFactory();
		factory.setContext(beanContext);
		factory.newHazelcast(new Config());
	}
	
}
