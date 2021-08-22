package com.tvd12.ezyfox.bean.v120.testing;

import java.util.Set;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.bean.EzyBeanContext;
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
	
}
