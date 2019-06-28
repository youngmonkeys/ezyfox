package com.tvd12.ezyfox.testing.util;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.util.EzyLoggable;
import com.tvd12.test.performance.Performance;

public class EzyLoggableTest extends EzyLoggable {

	@Test
	public void test() {
		getLogger().debug("abc");
	}
	
	@Test
	public void performanceTest() {
		long time1 = Performance.create()
				.test(() -> new A())
				.getTime();
		long time2 = Performance.create()
				.test(() -> new B())
				.getTime();
		System.out.println("EzyLoggableTest::performanceTest: time1: " + time1 + ", time2: " + time2);
	}
	
	public static class A extends EzyLoggable {
		
	}
	
	public static class B {
		
	}
	
}
