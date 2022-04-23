package com.tvd12.ezyfox.bean.testing.combine;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.bean.EzyBeanContext;
import com.tvd12.ezyfox.bean.EzyBeanContextBuilder;
import com.tvd12.ezyfox.bean.EzyPrototypeFactory;
import com.tvd12.ezyfox.bean.EzyPrototypeSupplier;
import com.tvd12.ezyfox.bean.impl.EzyByConstructorPrototypeSupplierLoader;
import com.tvd12.ezyfox.bean.impl.EzySimpleBeanContext;
import com.tvd12.ezyfox.bean.testing.combine.pack1.Singleton1;
import com.tvd12.ezyfox.properties.EzySimplePropertiesReader;

public class Combine2Test {

    @SuppressWarnings("unchecked")
    @Test
    public void test() throws Exception {
        EzyByConstructorPrototypeSupplierLoader.setDebug(true);
        EzyBeanContextBuilder builder = EzyBeanContext.builder()
                .scan(
                        "com.tvd12.ezyfox.bean.testing.combine"
                )
                .addProperties(new HashMap<>())
                .propertiesReader(new EzySimplePropertiesReader())
                .addSingletonClasses(new Class[] {
                        SingletonX1.class,
                        SingletonX2.class,
                        Singleton12.class
                })
                .addPrototypeClasses(new Class[] {
                        ClassA12.class
                })
                .addSingleton("list", new ArrayList<>())
                .addSingleton("map", new HashMap<>())
                .addSingleton("singleton2", new SingletonX3());

        Field prototypeFactoryField = EzySimpleBeanContext.Builder.class
                .getDeclaredField("prototypeFactory");
        prototypeFactoryField.setAccessible(true);
        EzyPrototypeFactory prototypeFactory = (EzyPrototypeFactory) prototypeFactoryField.get(builder);
        prototypeFactory.addSupplier(new EzyPrototypeSupplier() {

            @Override
            public Object supply(EzyBeanContext context) {
                return new ClassA12();
            }

            @Override
            public Class<?> getObjectType() {
                return ClassA12.class;
            }
        });

        EzyBeanContext context = builder.build();
        SingletonX1 x1 = (SingletonX1) context.getBean("singletonX1", SingletonX1.class);
        SingletonX2 x2 = (SingletonX2) context.getBean("singletonX2", SingletonX2.class);

        assert x1.getSingletonX1() == x1;
        assert x1.getSingletonX2() == x2;
        assert x2.getSingletonX1() == x1;
        assert x2.getSingletonX2() == x2;

        Singleton1 singleton1 = (Singleton1) context.getBean("s1", Singleton1.class);
        assert singleton1 != null;
        assert singleton1.getMap() != null;

        ClassA12 a121 = (ClassA12) context.getBean("classA12", ClassA12.class);
        ClassA12 a122 = (ClassA12) context.getBean("classA12", ClassA12.class);
        assert a121 != a122;
        assert context.getSingletons().size() > 0;
        System.out.println("number of singletons: " + context.getSingletons().size());
        List<Object> singletonsOf = context.getSingletonsOf(V112SingletonBase.class);
        System.out.println("singletonsOf: " + singletonsOf);
        assert singletonsOf.size() == 2;
        List<Object> singletons = context.getSingletons(o -> V112SingletonBase.class.isAssignableFrom(o.getClass()));
        assert singletons.size() == 2;
        System.out.println("number of prototypes: " + prototypeFactory.getSuppliers().size());
        List<EzyPrototypeSupplier> suppliers = prototypeFactory.getSuppliersOf(V112PrototypeBase.class);
        System.out.println("suppliers: " + suppliers);
        assert suppliers.size() == 2;
    }
}
