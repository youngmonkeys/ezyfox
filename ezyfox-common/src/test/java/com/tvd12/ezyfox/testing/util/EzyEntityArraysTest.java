package com.tvd12.ezyfox.testing.util;

import java.util.ArrayList;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.factory.EzyEntityFactory;
import com.tvd12.ezyfox.util.EzyEntityArrays;
import com.tvd12.test.base.BaseTest;

public class EzyEntityArraysTest extends BaseTest {

	@Override
	public Class<?> getTestClass() {
		return EzyEntityArrays.class;
	}
	
	@Test
	public void test() {
		EzyEntityArrays.newArray(new ArrayList<>());
		EzyEntityArrays.newArray();
		EzyEntityArrays.newArray(1, 2, 3);
		assert EzyEntityArrays.isEmpty(null);
		assert EzyEntityArrays.isEmpty(EzyEntityFactory.newArray());
		assert !EzyEntityArrays.isEmpty(EzyEntityArrays.newArray(1, 2, 3));
	}
	
}
