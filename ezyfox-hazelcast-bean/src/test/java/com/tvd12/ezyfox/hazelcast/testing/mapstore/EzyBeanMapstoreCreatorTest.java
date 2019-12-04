package com.tvd12.ezyfox.hazelcast.testing.mapstore;

import java.util.Properties;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.bean.EzyBeanContext;
import com.tvd12.ezyfox.hazelcast.mapstore.EzyBeanMapstoreCreator;
import com.tvd12.test.base.BaseTest;

public class EzyBeanMapstoreCreatorTest extends BaseTest {

	@Test
	public void test() {
		EzyBeanContext beanContext = EzyBeanContext.builder()
				.addSingletonClass(ExampleUserMapstore.class)
				.build();
		EzyBeanMapstoreCreator creator = new EzyBeanMapstoreCreator();
		creator.setContext(beanContext);
		assert creator.create("example_users", new Properties()) != null;
	}
	
}
