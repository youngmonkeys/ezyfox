package com.tvd12.ezyfox.testing.reflect;

import com.tvd12.ezyfox.reflect.EzyFieldFinder;
import com.tvd12.test.base.BaseTest;
import org.testng.annotations.Test;

import java.lang.reflect.Field;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;

public class EzyFieldFinderTest extends BaseTest {

    @Test
    public void test() {
        Field a = EzyFieldFinder.builder()
            .clazz(B.class)
            .fieldName("a")
            .build()
            .find();
        assertNotNull(a);

        Field b = EzyFieldFinder.builder()
            .clazz(B.class)
            .fieldName("b")
            .build()
            .find();
        assertNotNull(b);

        Field c = EzyFieldFinder.builder()
            .clazz(B.class)
            .fieldName("c")
            .build()
            .find();
        assertNotNull(c);

        Field d = EzyFieldFinder.builder()
            .clazz(B.class)
            .fieldName("d")
            .build()
            .find();
        assertNull(d);
    }

    public static interface I {
        String a = "";
    }

    public abstract class A implements I {
        String b;
    }

    public abstract class B extends A {
        String c;
    }
}
