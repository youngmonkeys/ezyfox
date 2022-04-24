package com.tvd12.ezyfox.bean.testing;

import com.tvd12.ezyfox.annotation.EzyKeyValue;
import com.tvd12.ezyfox.bean.EzyBeanContext;
import com.tvd12.ezyfox.bean.EzyPrototypeSupplier;
import com.tvd12.ezyfox.bean.annotation.EzyPrototype;
import com.tvd12.ezyfox.bean.impl.EzySimplePrototypeFactory;
import com.tvd12.test.base.BaseTest;
import org.testng.annotations.Test;

public class EzySimplePrototypeFactoryTest extends BaseTest {

    public static void main(String[] args) {
        System.out.println(Object.class.getSuperclass());
    }

    @Test
    public void test() {
        EzySimplePrototypeFactory factory = new EzySimplePrototypeFactory();
        factory.addSupplier("classA2", new EzyPrototypeSupplier() {

            @Override
            public Object supply(EzyBeanContext context) {
                return new ClassA2();
            }

            @Override
            public Class<?> getObjectType() {
                return ClassA2.class;
            }
        });
        factory.addSupplier("classA1", new EzyPrototypeSupplier() {

            @Override
            public Object supply(EzyBeanContext context) {
                return new ClassA2();
            }

            @Override
            public Class<?> getObjectType() {
                return ClassA2.class;
            }
        });
        factory.addSupplier("classA2", new EzyPrototypeSupplier() {

            @Override
            public Object supply(EzyBeanContext context) {
                return new ClassA2();
            }

            @Override
            public Class<?> getObjectType() {
                return ClassA2.class;
            }
        });
        assert factory.getSupplier("classA2", ClassA2.class) != null;
        assert factory.getSupplier("classA1", ClassA1.class) == null;
    }

    @EzyPrototype(properties = {
        @EzyKeyValue(key = "hello", value = "world")
    })
    public static class ClassA1 {}

    @EzyPrototype(properties = {
        @EzyKeyValue(key = "hello", value = "world")
    })
    public static class ClassA2 {}
}
