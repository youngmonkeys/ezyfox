package com.tvd12.ezyfox.testing.reflect;

import com.tvd12.ezyfox.reflect.EzyField;
import com.tvd12.ezyfox.reflect.EzyFields;
import com.tvd12.test.base.BaseTest;
import lombok.Getter;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

import static org.testng.Assert.*;

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
        protected Map<String, String> ab;
        protected List<String> abc;
        protected boolean xyz;
        @Getter
        protected Boolean booleanValue;
    }
}
