package com.tvd12.ezyfox.testing.util;

import java.util.HashMap;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.factory.EzyEntityFactory;
import com.tvd12.ezyfox.util.EzyEntityObjects;
import com.tvd12.test.base.BaseTest;

public class EzyEntityObjectsTest extends BaseTest {

	@Override
	public Class<?> getTestClass() {
		return EzyEntityObjects.class;
	}
	
	@Test
	public void test() {
		EzyEntityObjects.newObject(new HashMap<>());
		assert EzyEntityObjects.isEmpty(null);
		assert EzyEntityObjects.isEmpty(EzyEntityFactory.newObject());
		assert !EzyEntityObjects.isEmpty(EzyEntityObjects.newObject("hello", "world"));
	}
	
}
