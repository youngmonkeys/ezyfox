package com.tvd12.ezyfox.testing.reflect;

import com.tvd12.ezyfox.reflect.EzyMethodFinder;
import com.tvd12.test.base.BaseTest;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;

public class EzyMethodFinderTest extends BaseTest {

    @Test
    public void test1() {
        Method m1 = EzyMethodFinder.builder()
            .clazz(B.class)
            .methodName("getName")
            .parameterTypes()
            .build()
            .find();
        assertNotNull(m1);

        Method m2 = EzyMethodFinder.builder()
            .clazz(B.class)
            .methodName("getName1")
            .parameterTypes()
            .build()
            .find();
        assertNotNull(m2);

        Method m3 = EzyMethodFinder.builder()
            .clazz(B.class)
            .methodName("getName2")
            .parameterTypes()
            .build()
            .find();
        assertNotNull(m3);

        Method m4 = EzyMethodFinder.builder()
            .clazz(B.class)
            .methodName("getName3")
            .parameterTypes()
            .build()
            .find();
        assertNull(m4);
    }

    public interface I {
        String getName();
    }

    @SuppressWarnings("unused")
    public static abstract class A implements I {
        public abstract String getName1();
    }

    @SuppressWarnings("unused")
    public static abstract class B extends A {
        public abstract String getName2();
    }
}
