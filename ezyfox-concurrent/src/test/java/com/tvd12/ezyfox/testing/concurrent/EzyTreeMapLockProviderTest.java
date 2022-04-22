package com.tvd12.ezyfox.testing.concurrent;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.concurrent.EzyTreeMapLockProvider;

public class EzyTreeMapLockProviderTest {

    @Test
    public void test() {
        EzyTreeMapLockProvider provider = new EzyTreeMapLockProvider();
        provider.provideLock("a");
        provider.removeLock("b");
    }

}