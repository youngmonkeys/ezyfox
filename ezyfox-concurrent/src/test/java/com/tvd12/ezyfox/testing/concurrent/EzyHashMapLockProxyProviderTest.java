package com.tvd12.ezyfox.testing.concurrent;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.concurrent.EzyHashMapLockProxyProvider;

public class EzyHashMapLockProxyProviderTest {

	@Test
	public void test() {
		EzyHashMapLockProxyProvider provider = new EzyHashMapLockProxyProvider();
		provider.provideLock("a");
		provider.removeLock("a");
		provider.provideLock("a");
		provider.provideLock("a");
		provider.provideLock("b");
		provider.removeLock("a");
		provider.removeLock("a");
		provider.removeLock("b");
		provider.removeLock("c");
	}
	
}
