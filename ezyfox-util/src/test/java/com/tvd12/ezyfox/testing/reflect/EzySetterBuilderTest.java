package com.tvd12.ezyfox.testing.reflect;

import com.tvd12.ezyfox.reflect.EzyClass;
import com.tvd12.ezyfox.reflect.EzyField;
import com.tvd12.ezyfox.reflect.EzySetterBuilder;
import lombok.Setter;
import org.testng.annotations.Test;

import java.util.function.BiConsumer;

public class EzySetterBuilderTest {

    public static void main(String[] args) {
        new EzySetterBuilderTest().test();
    }

    @Test
    @SuppressWarnings("unchecked")
    public void test() {
        EzyClass clazz = new EzyClass(A.class);
        EzyField field = clazz.getField("value");
        EzySetterBuilder.setDebug(true);
        BiConsumer<A, String> setter = new EzySetterBuilder()
            .field(field)
            .build();
        A a = new A();
        setter.accept(a, "dung");
        System.out.println(a.value);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testFailure() {
        EzyClass clazz = new EzyClass(A.class);
        EzyField field = clazz.getField("name");
        new EzySetterBuilder()
            .field(field)
            .build();
    }

    @Setter
    public static class A {
        protected final String name = "name";
        protected String value;
    }
}
