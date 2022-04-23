package com.tvd12.ezyfox.testing.concurrent;

import com.tvd12.ezyfox.concurrent.EzyTreeMapLockProvider;
import org.testng.annotations.Test;

public class EzyTreeMapLockProviderTest {

    @Test
    public void test() {
        EzyTreeMapLockProvider provider = new EzyTreeMapLockProvider();
        provider.provideLock("a");
        provider.removeLock("b");
    }
}
