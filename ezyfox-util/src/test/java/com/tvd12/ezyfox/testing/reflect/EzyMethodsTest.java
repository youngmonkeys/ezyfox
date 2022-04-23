package com.tvd12.ezyfox.testing.reflect;

import com.tvd12.ezyfox.reflect.EzyClass;
import com.tvd12.ezyfox.reflect.EzyMethod;
import com.tvd12.ezyfox.reflect.EzyMethods;
import com.tvd12.test.base.BaseTest;
import org.testng.annotations.Test;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

public class EzyMethodsTest extends BaseTest {

    @Override
    public Class<?> getTestClass() {
        return EzyMethods.class;
    }

    @Test
    public void test() {
        assertNotNull(EzyMethods.getPublicMethod(ClassB.class, "setValue", String.class));
        assertEquals(EzyMethods.getAnnotatedMethods(ClassB.class, ExampleAnnotation.class).size(), 1);
    }

    @Test
    public void test2() {
        List<Method> methods = EzyMethods.getPublicMethods(ClassC.class);
        System.out.println("public methods: " + methods);
        assertEquals(methods.size(), 5);
    }

    @Test
    public void test3() {
        EzyClass clazz = new EzyClass(World.class);
        Collection<EzyMethod> methods = clazz.getMethods();
        System.out.println("test3: " + methods);
    }

    @Test
    public void test4() {
        List<Method> methods = EzyMethods.getMethods(ClassA2.class);
        assert EzyMethods.filterOverriddenMethods(methods).size() >= 2;
        EzyClass clazz = new EzyClass(ClassA2.class);
        List<EzyMethod> ms = clazz.getMethods();
        assert EzyMethods.filterOverriddenMethods(ms).size() >= 2;
    }

    @Test
    public void test5() throws Exception {
        Method m1 = ClassA1.class.getDeclaredMethod("setA");
        Method m2 = ClassA2.class.getDeclaredMethod("setA");
        assert !EzyMethods.isOverriddenMethod(m1, m1);
        assert EzyMethods.isOverriddenMethod(m1, m2);
        assert EzyMethods.isOverriddenMethod(m2, m1);
        Method m3 = ClassA1.class.getDeclaredMethod("setA1");
        assert !EzyMethods.isOverriddenMethod(m1, m3);
        assert !EzyMethods.isOverriddenMethod(m3, m1);
        assert !EzyMethods.isOverriddenMethod(m2, m3);
        assert !EzyMethods.isOverriddenMethod(m3, m2);
        Method m4 = ClassA2.class.getDeclaredMethod("setA1", String.class);
        assert !EzyMethods.isOverriddenMethod(m3, m4);
        assert !EzyMethods.isOverriddenMethod(m4, m3);
        Method m5 = ClassA3.class.getDeclaredMethod("setA1", String.class);
        assert !EzyMethods.isOverriddenMethod(m4, m5);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void test6() throws Exception {
        EzyMethods.isOverriddenMethod((Method) null, null);
    }

    @Test
    public void test7() {
        assert EzyMethods.getPublicMethod(Class21.class, "setValue", String.class).getName().equals("setValue");
        assert EzyMethods.getPublicMethod(Class21.class, "setValue1", String.class).getName().equals("setValue1");
        assert EzyMethods.getPublicMethod(Class21.class, "setValue2", String.class).getName().equals("setValue2");
        assert EzyMethods.getPublicMethod(Class21.class, "setValue3", String.class) == null;
        assert EzyMethods.getPublicMethod(Class21.class, "setValue4", String.class) == null;
        assert EzyMethods.getPublicMethod(Class21.class, "setValue no", String.class) == null;
    }

    @Test
    public void test8() {
        assert EzyMethods.getAnnotatedMethods(Class21.class, Ann21.class).size() == 2;
    }

    @Retention(RUNTIME)
    @Target({TYPE, FIELD, METHOD, PARAMETER, CONSTRUCTOR, LOCAL_VARIABLE, ANNOTATION_TYPE, PACKAGE, TYPE_PARAMETER,
        TYPE_USE})
    public static @interface Ann21 {
    }

    public static interface Interface21 extends Interface21Base {
        void setValue1(String value1);
    }

    public static interface Interface21Base {
        @Ann21
        void setValue(String value);
    }

    public static interface Hello {

        String getMessageType();

    }

    public abstract static class Class21 extends Class21Base implements Interface21 {

        @Override
        public void setValue1(String value1) {
        }

        protected void setValue5(String value5) {
        }

    }

    public static class Class21Base {
        public void setValue2(String value2) {
        }

        @Ann21
        protected void setValue3(String value3) {
        }
    }

    public static class ClassA3 {
        public void setA1(String str) {
        }
    }

    public static class ClassA2 extends ClassA1 {

        public void setB() {
        }

        @Override
        public void setA() {
            super.setA();
        }

        public void setA1(String str) {
        }

    }

    public static class ClassA1 {

        public void setA() {
        }

        public void setA1() {
        }

    }

    public static class ClassC extends ClassB {

        public static void c1() {
        }

        public static final void c2() {
        }

        public final void c3() {
        }
    }

    public static class ClassA {

        public void setValue(String value) {
        }

    }

    public static class ClassB extends ClassA {

        @ExampleAnnotation
        public void setName(String name) {

        }
    }

    public static class World implements Hello {

        @Override
        public String getMessageType() {
            return "hello world";
        }

    }
}
