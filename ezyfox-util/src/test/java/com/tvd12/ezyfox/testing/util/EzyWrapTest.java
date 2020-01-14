package com.tvd12.ezyfox.testing.util;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.util.EzyWrap;

public class EzyWrapTest {

	@Test
	public void test() {
		EzyWrap<String> wrap = new EzyWrap<>();
		assert wrap.getValue() == null;
		assert wrap.hasNoValue();
		assert !wrap.hasValue();
		wrap.setValue("hello");
		assert wrap.hasValue();
		assert !wrap.hasNoValue();
		assert wrap.getValue().equals("hello");
		wrap = new EzyWrap<>("world");
		assert wrap.getValue().equals("world");
	}
	
}
