package com.tvd12.ezyfox.testing.util;

import com.tvd12.ezyfox.annotation.EzyProperty;
import com.tvd12.ezyfox.reflect.EzyClass;
import com.tvd12.ezyfox.reflect.EzyField;
import com.tvd12.ezyfox.reflect.EzyMethod;
import com.tvd12.ezyfox.reflect.EzyReflectElement;
import com.tvd12.ezyfox.util.EzyPropertyAnnotations;
import com.tvd12.test.base.BaseTest;
import org.testng.annotations.Test;

public class EzyPropertyAnnotationsTest extends BaseTest {

    @Override
    public Class<?> getTestClass() {
        return EzyPropertyAnnotations.class;
    }

    @Test
    public void test() {
        EzyClass clazz = new EzyClass(ClassA.class);
        EzyField a = clazz.getField("a");
        assert EzyPropertyAnnotations.getPropertyName(clazz, (EzyReflectElement) a).equals("a");
        EzyField b = clazz.getField("b");
        assert EzyPropertyAnnotations.getPropertyName(clazz, (EzyReflectElement) b).equals("1");

        EzyMethod c = clazz.getMethod("setC");
        assert EzyPropertyAnnotations.getPropertyName(clazz, (EzyReflectElement) c).equals("c");

        EzyMethod d = clazz.getMethod("setD");
        assert EzyPropertyAnnotations.getPropertyName(clazz, (EzyReflectElement) d).equals("2");

        EzyMethod e = clazz.getMethod("setE");
        assert EzyPropertyAnnotations.getPropertyName(clazz, (EzyReflectElement) e).equals("3");

        EzyMethod f = clazz.getMethod("setF");
        assert EzyPropertyAnnotations.getPropertyName(clazz, (EzyReflectElement) f).equals("f");
    }

    public static class ClassA {

        @EzyProperty
        public String a;

        @EzyProperty("1")
        public String b;

        @EzyProperty
        private String c;

        @EzyProperty("2")
        private String d;

        public void setC(String c) {
            this.c = c;
        }

        public void setD(String d) {
            this.d = d;
        }

        @EzyProperty("3")
        public void setE(String e) {

        }

        @EzyProperty
        public void setF(String f) {

        }

    }
}
