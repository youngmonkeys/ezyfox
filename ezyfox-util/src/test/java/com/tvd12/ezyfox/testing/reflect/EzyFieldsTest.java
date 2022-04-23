package com.tvd12.ezyfox.testing.reflect;

import com.tvd12.ezyfox.reflect.EzyFields;
import com.tvd12.test.base.BaseTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class EzyFieldsTest extends BaseTest {

    @Override
    public Class<?> getTestClass() {
        return EzyFields.class;
    }

    @Test
    public void test() {
        assert EzyFields.getFields(null).isEmpty();
        assertEquals(EzyFields.getAnnotatedFields(ClassB.class, ExampleAnnotation.class).size(), 2);
    }

    @SuppressWarnings("unused")
    public static class ClassA {
        @ExampleAnnotation
        String value;
    }

    @SuppressWarnings("unused")
    public static class ClassB extends ClassA {
        @ExampleAnnotation
        String name;
    }
}
