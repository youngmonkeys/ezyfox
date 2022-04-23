package com.tvd12.ezyfox.testing.reflect;

import com.tvd12.ezyfox.reflect.EzyGetterMethod;
import com.tvd12.test.base.BaseTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class EzyGetterMethodTest extends BaseTest {

    @Test
    public void test() throws Exception {
        EzyGetterMethod getter = new EzyGetterMethod(
            ClassA.class.getDeclaredMethod("getValue"));
        assertEquals(getter.getType(), String.class);
        assertEquals(getter.getGenericType(), String.class);
        assertEquals(getter.isMapType(), false);
        assertEquals(getter.isCollection(), false);
        assertEquals(getter.getFieldName(), "value");
        assertEquals(getter.getTypeName(), String.class.getTypeName());

        EzyGetterMethod getter2 = new EzyGetterMethod(
            ClassA.class.getDeclaredMethod("isHello"));
        assert getter2.getFieldName().equals("hello");
    }

    @Test
    public void test2() throws Exception {
        EzyGetterMethod getter = new EzyGetterMethod(
            ClassA.class.getDeclaredMethod("getA"));
        assertEquals(getter.getFieldName(), "a");
    }

    @Test
    public void test3() throws Exception {
        EzyGetterMethod getter = new EzyGetterMethod(
            ClassA.class.getDeclaredMethod("duplicateValue"));
        assertEquals(getter.getFieldName(), "duplicateValue");
    }

    @Test
    public void test4() throws Exception {
        EzyGetterMethod get = new EzyGetterMethod(
            ClassA.class.getDeclaredMethod("get"));
        assert get.getFieldName().equals("get");
    }

    public static class ClassA {

        public String getA() {
            return "";
        }

        public String getValue() {
            return "value";
        }

        public String duplicateValue() {
            return "";
        }

        public String get() {
            return "get";
        }

        public boolean isHello() {
            return true;
        }

    }
}
