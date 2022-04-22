package com.tvd12.ezyfox.testing.concurrent;

import java.lang.reflect.Field;
import java.util.Map;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.concurrent.EzyLockProxy;
import com.tvd12.ezyfox.concurrent.EzyMapLockProxyProvider;
import com.tvd12.ezyfox.concurrent.EzyTreeMapLockProxyProvider;

public class EzyTreeMapLockProxyProviderTest {

    @SuppressWarnings("unchecked")
    @Test
    public void test() {
        EzyTreeMapLockProxyProvider provider = new EzyTreeMapLockProxyProvider();
        provider.provideLock("a");
        provider.removeLock("a");
        provider.provideLock("a");
        provider.provideLock("a");
        provider.provideLock("b");
        provider.removeLock("a");
        provider.removeLock("a");
        provider.removeLock("b");
        provider.removeLock("c");

        try {
            Field field = EzyMapLockProxyProvider.class.getDeclaredField("locks");
            field.setAccessible(true);
            Map<Object, EzyLockProxy> locks = (Map<Object, EzyLockProxy>) field.get(provider);
            locks.put("1", new ExLockProxy());
            try {
                provider.provideLock("1");
            }
            catch (Exception e) {
            }
            try {
                provider.removeLock("1");
            }
            catch (Exception e) {
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static class ExLockProxy extends EzyLockProxy {
        @Override
        public void retain() {
            throw new IllegalStateException();
        }

        @Override
        public void release() {
            throw new IllegalStateException();
        }
    }
}