package com.tvd12.ezyfox.testing.util;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.util.EzyLoggable;

public class EzyLoggableTest extends EzyLoggable {

	@Test
	public void test() {
		getLogger().debug("abc");
	}
	
}
