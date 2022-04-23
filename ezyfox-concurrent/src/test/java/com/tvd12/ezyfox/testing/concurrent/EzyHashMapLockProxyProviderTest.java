package com.tvd12.ezyfox.testing.concurrent;

import java.util.concurrent.locks.Lock;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.collect.Sets;
import com.tvd12.ezyfox.concurrent.EzyHashMapLockProxyProvider;

public class EzyHashMapLockProxyProviderTest {

    @Test
    public void test() {
        EzyHashMapLockProxyProvider provider = new EzyHashMapLockProxyProvider();
        Lock locka = provider.provideLock("a");
        assert provider.getLock("a") == locka;
        assert provider.size() == 1;
        provider.removeLock("a");
        provider.provideLock("a");
        provider.provideLock("a");
        provider.provideLock("b");
        provider.removeLock("a");
        provider.removeLock("a");
        provider.removeLock("b");
        provider.removeLock("c");
        provider.provideLock("d");
        provider.provideLock("d");
        provider.removeLocks(Sets.newHashSet("a", "b", "d"));
        provider.removeLocks(Sets.newHashSet("a", "b", "d"));
        provider.removeLocks(Sets.newHashSet("a", "b", "d"));
    }
}
