package com.tvd12.ezyfox.testing.reflect;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

import java.util.List;
import java.util.Map;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.reflect.EzyField;
import com.tvd12.ezyfox.reflect.EzyFields;
import com.tvd12.test.base.BaseTest;

import lombok.Getter;

public class EzyFieldTest extends BaseTest {

    @Test
    public void test() throws Exception {
        EzyField a = EzyField.builder()
                .clazz(ClassA.class)
                .fieldName("a")
                .build();
        a.setAccessible(true);
        assertNotNull(EzyFields.getField(ClassA.class, "a"));
        assertFalse(a.isMapType());
        assertFalse(a.isCollection());
        assertEquals(a.getType(), String.class);
        assertEquals(a.getGenericType(), String.class);
        assertEquals(a.getGetterMethod(), "getA");
        assertEquals(a.getSetterMethod(), "setA");
        assertFalse(a.isAnnotated(ExampleAnnotation.class));
        assertNull(a.getAnnotation(ExampleAnnotation.class));
        a.toString();

        EzyField ab = new EzyField(ClassA.class.getDeclaredField("ab"));
        assertEquals(ab.getGetterMethod(), "getAb");
        assertEquals(ab.getSetterMethod(), "setAb");
        assertTrue(ab.isMapType());

        EzyField xyz = new EzyField(ClassA.class.getDeclaredField("xyz"));
        assertEquals(xyz.getGetterMethod(), "isXyz");

        EzyField booleanValue = new EzyField(ClassA.class.getDeclaredField("booleanValue"));
        assertEquals(booleanValue.getGetterMethod(), "getBooleanValue");

        assert !a.equals(null);
        assert a.equals(a);
        assert a.equals(new EzyField(a.getField()));
        assert a.hashCode() == a.hashCode();
    }

    public static class ClassA {
        protected String a;
        protected Map<String,String> ab;
        protected List<String> abc;
        protected boolean xyz;
        @Getter
        protected Boolean booleanValue;
    }
}