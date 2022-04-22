package com.tvd12.ezyfox.testing.reflect;

import java.util.function.Function;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.reflect.EzyClass;
import com.tvd12.ezyfox.reflect.EzyField;
import com.tvd12.ezyfox.reflect.EzyGetterBuilder;
import com.tvd12.ezyfox.reflect.EzySetterBuilder;

import lombok.Getter;
import lombok.Setter;

public class EzyGetterBuilderTest {

    @Test
    @SuppressWarnings("unchecked")
    public void test() {
        EzyClass clazz = new EzyClass(A.class);
        EzyField field = clazz.getField("value");
        EzySetterBuilder.setDebug(true);
        Function<A,String> getter = new EzyGetterBuilder()
            .field(field)
            .build();
        A a = new A();
        a.setValue("foo");
        System.out.println(getter.apply(a));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testFailure() {
        EzyClass clazz = new EzyClass(A.class);
        new EzyGetterBuilder()
            .method(clazz.getMethod("getSomething"))
            .build();
    }

    public static void main(String[] args) {
        new EzyGetterBuilderTest().test();
    }

    @Setter
    @Getter
    public static class A {
        protected String value;
        protected final String name = "name";

        public void getSomething() {
        }
    }

}
