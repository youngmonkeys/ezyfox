package com.tvd12.ezyfox.testing.concurrent;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.locks.Lock;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.collect.Sets;
import com.tvd12.ezyfox.concurrent.EzyLockProxy;
import com.tvd12.ezyfox.concurrent.EzyMixedMapLockProxyProvider;
import com.tvd12.ezyfox.util.EzyMapBuilder;
import com.tvd12.ezyfox.util.EzyMixedMap;

import lombok.AllArgsConstructor;

public class EzyMixedMapLockProxyProviderTest {

    @SuppressWarnings("unchecked")
    @Test
    public void test() {
        EzyMixedMapLockProxyProvider provider = new EzyMixedMapLockProxyProvider();
        Lock lock1 = provider.provideLock(new Key("a"));
        assert provider.provideLock(new Key("a")) == lock1;
        assert provider.getLock(new Key("a")) == lock1;
        provider.removeLock(new Key("a"));
        provider.removeLock(new Key("a"));
        provider.removeLock(new Key("a"));
        provider.provideLock(new Key("b"));
        provider.provideLock(new Key("b"));
        assert provider.size() == 1;

        try {
            Field field = EzyMixedMapLockProxyProvider.class.getDeclaredField("locks");
            field.setAccessible(true);
            EzyMixedMap<EzyLockProxy> locks = (EzyMixedMap<EzyLockProxy>) field.get(provider);
            locks.computeIfAbsent(new Key("1"), () -> new ExLockProxy());
            try {
                provider.provideLock(new Key("1"));
            }
            catch (Exception e) {
            }
            try {
                provider.removeLock(new Key("1"));
            }
            catch (Exception e) {
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        provider.removeLocks(Sets.newHashSet(new Key("b")));
        provider.removeLocks(Sets.newHashSet(new Key("b")));
        provider.removeLocks(Sets.newHashSet(new Key("b")));
        assert provider.size() == 1;
    }

    @AllArgsConstructor
    public static class Key implements EzyMixedMap.EzyMixedKey {

        protected final String k;

        @SuppressWarnings("unchecked")
        @Override
        public Map<Object, Object> getKeys() {
            return EzyMapBuilder.mapBuilder()
                    .put("k", k)
                    .build();
        }

        @Override
        public boolean equals(Object obj) {
            return k.equals(((Key)obj).k);
        }

        @Override
        public int hashCode() {
            return k.hashCode();
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
