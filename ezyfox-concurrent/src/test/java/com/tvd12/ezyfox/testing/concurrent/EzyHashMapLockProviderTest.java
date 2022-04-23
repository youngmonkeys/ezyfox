package com.tvd12.ezyfox.testing.concurrent;

import java.util.concurrent.locks.Lock;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.collect.Sets;
import com.tvd12.ezyfox.concurrent.EzyHashMapLockProvider;

public class EzyHashMapLockProviderTest {

    @Test
    public void test() {
        EzyHashMapLockProvider provider = new EzyHashMapLockProvider();
        Lock lock = provider.provideLock("a");
        assert provider.getLock("a") == lock;
        assert provider.size() == 1;
        provider.removeLock("b");
        provider.removeLocks(Sets.newHashSet("a", "b"));
        provider.removeLocks(Sets.newHashSet("a", "b"));
    }

}
