package com.tvd12.ezyfox.testing.reflect;

import com.tvd12.ezyfox.reflect.EzyInterfaces;
import org.testng.annotations.Test;

public class EzyInterfacesTest {

    @SuppressWarnings("rawtypes")
    @Test
    public void test() {
        ClassA classA = new ClassA();
        Class itf = EzyInterfaces.getInterfaceAnyway(classA.getClass(), InterfaceA.class);
        System.out.println(itf);
    }

    public static interface InterfaceB extends InterfaceA<String> {
    }

    public static interface InterfaceA<D> {
    }

    public static class ClassA extends AbstractA<Integer> {
    }

    public static class AbstractA<D> implements InterfaceA<D> {
    }
}
