package com.tvd12.ezyfox.testing.reflect;

import com.tvd12.ezyfox.reflect.EzyMethod;
import com.tvd12.test.base.BaseTest;
import org.testng.annotations.Test;

public class EzyMethod2Test extends BaseTest {

    @Test
    public void test() throws Exception {

        EzyMethod m1 = new EzyMethod(ClassA.class.getDeclaredMethod("get"));
        EzyMethod m2 = new EzyMethod(ClassA.class.getDeclaredMethod("set"));
        EzyMethod m3 = new EzyMethod(ClassA.class.getDeclaredMethod("hello"));
        EzyMethod m4 = new EzyMethod(ClassA.class.getDeclaredMethod("getJava"));
        assert m1.getParameters().length >= 0;

        assert m1.getFieldName().equals("get");
        assert m2.getFieldName().equals("set");
        assert m3.getFieldName().equals("hello");
        assert m4.getFieldName().equals("java");

        m4.invoke(new ClassA());
    }

    @Test(expectedExceptions = {IllegalArgumentException.class})
    public void test2() throws Exception {
        EzyMethod m1 = new EzyMethod(ClassA.class.getDeclaredMethod("pMethod"));
        m1.invoke(new ClassA());
    }

    public static class ClassA {

        protected void pMethod() {
            throw new RuntimeException();
        }

        public String get() {
            return "";
        }

        public void set() {}

        public void hello() {}

        public String getJava() {
            return "java";
        }
    }
}
