package com.tvd12.ezyfox.testing.reflect;

import com.tvd12.ezyfox.collect.Sets;
import com.tvd12.ezyfox.reflect.EzyClasses;
import com.tvd12.test.base.BaseTest;
import org.testng.annotations.Test;

import java.lang.reflect.Field;
import java.util.Set;

import static org.testng.Assert.assertEquals;

public class EzyClasses1Test extends BaseTest {

    public A a = new A() {};

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Test
    public void test1() {
        Class<E> clazzD = E.class;
        Set<Class> classes = EzyClasses.flatSuperAndInterfaceClasses(clazzD);
        for (Class cl : classes) {
            System.err.println(cl.getSimpleName() + ", ");
        }
        assertEquals(classes, Sets.newHashSet(A.class, B.class, C.class, D.class));
        classes = EzyClasses.flatSuperClasses(B.class);
        assertEquals(classes.size(), 0);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Test
    public void test2() {
        Class<E> clazzD = E.class;
        System.err.println("\n======\n");
        Set<Class> classes = EzyClasses.flatSuperAndInterfaceClasses(clazzD, true);
        for (Class cl : classes) {
            System.err.println(cl.getSimpleName() + ", ");
        }
        assertEquals(classes, Sets.newHashSet(A.class, B.class, C.class, D.class, Object.class));
        classes = EzyClasses.flatSuperClasses(B.class);
        assertEquals(classes.size(), 0);
    }

    @Test
    public void test3() throws Exception {
        Class<?> clazz = Class.forName("DefaultClassX");
        Object object = clazz.newInstance();
        Field objf = clazz.getDeclaredField("obj");
        objf.setAccessible(true);
        Object obj = objf.get(object);
        assert EzyClasses.getVariableName(clazz).equals("defaultClassX");
        System.out.println(EzyClasses.getVariableName(a.getClass()));
        System.out.println(EzyClasses.getVariableName(obj.getClass()));

        assert EzyClasses.getVariableName(Impl.class, "Impl").equals("impl");
        assert EzyClasses.getVariableName(AImpl.class, "Impl").equals("a");
        assert EzyClasses.getVariableName(abc.class, "abc").equals("abc");
    }

    public interface A {}

    public interface B extends A {}

    public static class C {}

    public static class D extends C {}

    public static class E extends D implements B {}
}
