package com.tvd12.ezyfox.testing.concurrent;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.concurrent.EzyLazyInitializer;
import com.tvd12.test.base.BaseTest;
import com.tvd12.test.reflect.MethodInvoker;

public class EzyLazyInitializerTest extends BaseTest {

    @Test(expectedExceptions = {IllegalStateException.class})
    public void test() {
        new EzyLazyInitializer<String>(()-> new String()) {
            @Override
            protected String initialize() {
                throw new IllegalStateException();
            };
        }.get();
    }
    
    @Test
    public void test1() {
        EzyLazyInitializer<String> t = new EzyLazyInitializer<>(() -> new String("abc"));
        String str = t.get();
        assert str.equals("abc");
        assert t.get().equals("abc");
    }
    
    @Test
    public void test2() {
        EzyLazyInitializer<String> t = new EzyLazyInitializer<>(() -> new String("abc"));
        Object s = MethodInvoker.create()
                .method("synGet")
                .object(t)
                .invoke();
        assert s.equals("abc");
    }
    
    @Test
    public void test3() {
        EzyLazyInitializer<String> t = new EzyLazyInitializer<>(() -> new String("abc"));
        t.get();
        Object s = MethodInvoker.create()
                .method("synGet")
                .object(t)
                .invoke();
        assert s.equals("abc");
    }
}
