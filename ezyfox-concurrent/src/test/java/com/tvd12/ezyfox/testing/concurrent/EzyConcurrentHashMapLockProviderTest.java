package com.tvd12.ezyfox.testing.concurrent;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.concurrent.EzyConcurrentHashMapLockProvider;

public class EzyConcurrentHashMapLockProviderTest {

    @Test
    public void test() {
        EzyConcurrentHashMapLockProvider provider = new EzyConcurrentHashMapLockProvider();
        provider.provideLock("a");
        provider.removeLock("a");
    }

}
