package com.tvd12.ezyfox.bean.testing.supplier;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.bean.EzyBeanContext;
import com.tvd12.test.base.BaseTest;

public class SuppliersTest extends BaseTest {

	@Test
	public void test() {
		EzyBeanContext context = EzyBeanContext.builder()
			.scan("com.tvd12.ezyfox.bean.testing.supplier")
			.build();
		ClassA classA = (ClassA)context.getBean(ClassA.class);
		assert classA.getHashSet() != null;
	}
	
}
