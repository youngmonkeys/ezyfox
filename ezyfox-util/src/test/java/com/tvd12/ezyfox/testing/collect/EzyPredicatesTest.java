package com.tvd12.ezyfox.testing.collect;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.function.EzyPredicates;
import com.tvd12.test.base.BaseTest;

public class EzyPredicatesTest extends BaseTest {

	@Test
	public void test() {
		EzyPredicates.alwayTrue().test(null);
	}
	
	@Override
	public Class<?> getTestClass() {
		return EzyPredicates.class;
	}
	
}
