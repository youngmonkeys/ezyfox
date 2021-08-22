package com.tvd12.ezyfox.bean.v120.testing;

import java.util.Arrays;
import java.util.Set;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.bean.EzyBeanContext;
import com.tvd12.ezyfox.bean.v120.testing.packet01.LastSingleton11;
import com.tvd12.ezyfox.bean.v120.testing.packet02.ConfigAfter21;
import com.tvd12.ezyfox.bean.v120.testing.packet02.ConfigAfter22;
import com.tvd12.ezyfox.bean.v120.testing.packet02.ConfigAfter23;
import com.tvd12.ezyfox.bean.v120.testing.packet02.LastSingleton21;
import com.tvd12.ezyfox.bean.v120.testing.packet02.LastSingleton22;
import com.tvd12.ezyfox.bean.v120.testing.packet02.LastSingleton23;
import com.tvd12.ezyfox.collect.Sets;
import com.tvd12.ezyfox.reflect.EzyReflection;
import com.tvd12.ezyfox.reflect.EzyReflectionProxy;
import com.tvd12.test.assertion.Asserts;

public class EzySimpleBeanContextTest {

	@Test
	public void scanPackagesScanClassesTest() {
		// given
		EzyReflection reflection = new EzyReflectionProxy("com.tvd12.ezyfox.bean.v120.testing");
		EzyBeanContext beanContext = EzyBeanContext.builder()
				.addAllClasses(reflection)
				.build();
		
		// when
		Set<String> packagesToScan = beanContext.getPackagesToScan();
		
		// then
		Set<String> expectation = Sets.newHashSet(
			"com.tvd12.ezyfox.bean.v120.testing.packet01",
			"com.tvd12.ezyfox.bean.v120.testing.packet02"
		);
		Asserts.assertEquals(expectation, packagesToScan);
	}
	
	@Test
	public void configAfterTest() {
		// given
		EzyBeanContext beanContext = EzyBeanContext.builder()
				.scan("com.tvd12.ezyfox.bean.v120.testing")
				.addConfigurationAfterClass(ConfigAfter21.class)
				.addConfigurationAfterClasses(ConfigAfter22.class, ConfigAfter22.class)
				.addConfigurationAfterClasses(Arrays.asList(ConfigAfter23.class))
				.build();
		
		// when
		LastSingleton11 lastSingleton11 = beanContext.getSingleton(LastSingleton11.class);
		LastSingleton21 lastSingleton21 = beanContext.getSingleton(LastSingleton21.class);
		LastSingleton22 lastSingleton22 = beanContext.getSingleton(LastSingleton22.class);
		LastSingleton23 lastSingleton23 = beanContext.getSingleton(LastSingleton23.class);
		
		// then
		Asserts.assertNotNull(lastSingleton11);
		Asserts.assertNotNull(lastSingleton21);
		Asserts.assertNotNull(lastSingleton22);
		Asserts.assertNotNull(lastSingleton23);
	}
}
