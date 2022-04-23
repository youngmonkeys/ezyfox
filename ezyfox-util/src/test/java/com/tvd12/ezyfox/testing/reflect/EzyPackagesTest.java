package com.tvd12.ezyfox.testing.reflect;

import com.tvd12.ezyfox.collect.Sets;
import com.tvd12.ezyfox.reflect.EzyPackages;
import com.tvd12.test.base.BaseTest;
import org.testng.annotations.Test;

public class EzyPackagesTest extends BaseTest {

    public java.lang.Class<?> getTestClass() {
        return EzyPackages.class;
    }

    @Test
    public void test() {
        EzyPackages.scanPackage("com.tvd12.ezyfox.testing.reflect");
        EzyPackages.scanPackages(Sets.newHashSet("com.tvd12.ezyfox.testing.reflect"));
    }

    public interface InterfaceA {}

    @ExampleAnnotation
    public static class ClassA {}

    public static class ClassB extends ClassA {}
}
