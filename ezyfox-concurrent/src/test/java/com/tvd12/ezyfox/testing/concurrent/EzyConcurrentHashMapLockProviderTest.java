package com.tvd12.ezyfox.testing.concurrent;

import com.tvd12.ezyfox.concurrent.EzyConcurrentHashMapLockProvider;
import org.testng.annotations.Test;

public class EzyConcurrentHashMapLockProviderTest {

    @Test
    public void test() {
        EzyConcurrentHashMapLockProvider provider = new EzyConcurrentHashMapLockProvider();
        provider.provideLock("a");
        provider.removeLock("a");
    }
}
