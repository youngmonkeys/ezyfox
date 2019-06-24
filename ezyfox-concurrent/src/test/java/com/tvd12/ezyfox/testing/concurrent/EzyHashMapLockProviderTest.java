package com.tvd12.ezyfox.testing.concurrent;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.concurrent.EzyHashMapLockProvider;

public class EzyHashMapLockProviderTest {

	@Test
	public void test() {
		EzyHashMapLockProvider provider = new EzyHashMapLockProvider();
		provider.provideLock("a");
		provider.removeLock("b");
	}
	
}