package com.tvd12.ezyfox.testing.util;

import com.tvd12.ezyfox.function.EzyApply;
import com.tvd12.ezyfox.reflect.EzyInterfaces;
import com.tvd12.test.base.BaseTest;
import org.testng.annotations.Test;

public class EzyInterfacesTest extends BaseTest {

    @Override
    public Class<?> getTestClass() {
        return EzyInterfaces.class;
    }

    @Test
    public void test() {
        assert EzyInterfaces.getInterface(InterfaceB.class, InterfaceC.class) != null;
        assert EzyInterfaces.getInterface(InterfaceB.class, EzyApply.class) == null;
    }

    @SuppressWarnings("ALL")
    public interface InterfaceA<I, E> {}

    public interface InterfaceB extends InterfaceA<String, ClassA>, InterfaceC<String, ClassA> {}

    @SuppressWarnings("ALL")
    public interface InterfaceC<I, E> {}

    public static class ClassA {}
}
