package com.tvd12.ezyfox.testing.reflect;

import com.tvd12.ezyfox.reflect.EzySetterMethod;
import com.tvd12.test.base.BaseTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

public class EzySetterMethodTest extends BaseTest {

    @Test
    public void test() throws Exception {
        EzySetterMethod setter = new EzySetterMethod(
            ClassA.class.getDeclaredMethod("setValue", String.class));
        assertEquals(setter.getType(), String.class);
        assertEquals(setter.getGenericType(), String.class);
        assertFalse(setter.isMapType());
        assertFalse(setter.isCollection());
        assertEquals(setter.getFieldName(), "value");
        assert setter.getParameterCount() >= 0;
    }

    @Test
    public void test2() throws Exception {
        EzySetterMethod setter = new EzySetterMethod(
            ClassA.class.getDeclaredMethod("setA", String.class));
        assertEquals(setter.getFieldName(), "a");
    }

    @Test
    public void test3() throws Exception {
        EzySetterMethod setter = new EzySetterMethod(
            ClassA.class.getDeclaredMethod("duplicateValue", String.class));
        assertEquals(setter.getFieldName(), "duplicateValue");
    }

    @Test
    public void test4() throws Exception {
        EzySetterMethod set = new EzySetterMethod(
            ClassA.class.getDeclaredMethod("set", String.class));
        assert set.getFieldName().equals("set");
    }

    @SuppressWarnings("unused")
    public static class ClassA {

        public void setA(String a) {}

        public void setValue(String value) {}

        public void duplicateValue(String value) {}

        public void set(String a) {}
    }
}
