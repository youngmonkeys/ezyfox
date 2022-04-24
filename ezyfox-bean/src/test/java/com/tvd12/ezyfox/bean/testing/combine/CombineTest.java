package com.tvd12.ezyfox.bean.testing.combine;

import com.tvd12.ezyfox.annotation.EzyProperty;
import com.tvd12.ezyfox.bean.*;
import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.bean.impl.EzyByConstructorPrototypeSupplierLoader;
import com.tvd12.ezyfox.bean.testing.combine.pack0.ApplicationProperties0;
import com.tvd12.ezyfox.bean.testing.combine.pack0.PropertiesPack0;
import com.tvd12.ezyfox.bean.testing.combine.pack0.PropertiesPack1;
import com.tvd12.ezyfox.bean.testing.combine.pack1.ClassA1;
import com.tvd12.ezyfox.bean.testing.combine.pack1.ClassB1;
import com.tvd12.ezyfox.bean.testing.combine.pack1.Singleton1;
import com.tvd12.ezyfox.bean.testing.combine.pack2.Singleton20;
import com.tvd12.ezyfox.collect.Lists;
import com.tvd12.ezyfox.collect.Sets;
import com.tvd12.ezyfox.io.EzyMaps;
import com.tvd12.ezyfox.reflect.EzyReflectionProxy;
import com.tvd12.test.assertion.Asserts;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;

public class CombineTest {

    @SuppressWarnings("unchecked")
    @Test
    public void test() throws Exception {
        EzyByConstructorPrototypeSupplierLoader.setDebug(true);
        EzyBeanContextBuilder builder = EzyBeanContext.builder()
            .scan(
                "com.tvd12.ezyfox.bean.testing.combine.pack0",
                "com.tvd12.ezyfox.bean.testing.combine.pack1",
                "com.tvd12.ezyfox.bean.testing.combine.pack2"
            )
            .scan(new ArrayList<>())
            .scan(() -> Lists.newArrayList("com.tvd12.ezyfox.bean.testing.combine.pack2").iterator())
            .addAllClasses(new Object())
            .addAllClasses(new EzyReflectionProxy("com.tvd12.ezyfox.bean.testing.combine.pack2"))
            .addProperty("hello", "world")
            .addProperty("foo", "bar")
            .addProperty("array", "1,2,3,4,5")
            .addProperty("ints", Lists.newArrayList(1, 2, 3))
            .addSingleton("list", new ArrayList<>())
            .addSingleton("map", new HashMap<>())
            .addSingletonClasses(
                SingletonX1.class,
                SingletonX2.class,
                Singleton12.class,
                SingletonX4.class
            )
            .addPrototypeClasses(
                ClassA12.class,
                PrototypeX1.class
            )
            .addSingleton("singleton2", new SingletonX3())
            .addConfigurationBeforeClasses(V111ConfigurationBefore01.class, V111ConfigurationBefore01.class)
            .addConfigurationBeforeClasses(Sets.newHashSet(V111ConfigurationBefore01.class, V111ConfigurationBefore01.class))
            .addConfigurationClasses(V111Configuration01.class, V111Configuration01.class)
            .addConfigurationClasses(Sets.newHashSet(V111Configuration01.class, V111Configuration01.class))
            .addSingletonClass("v111Singleton03New", V111Singleton03.class)
            .addSingletonClasses(EzyMaps.newHashMap("v111Singleton04New", V111Singleton04.class))
            .addPrototypeClass("v111Prototype02New", V111Prototype02.class)
            .addPrototypeClasses(EzyMaps.newHashMap("v111Prototype03New", V111Prototype03.class))
            .addSingletons(EzyMaps.newHashMap("v111Singleton05", new V111Singleton05()))
            .addPrototypeSuppliers(EzyMaps.newHashMap("v111Prototype04", new EzyPrototypeSupplier() {
                @Override
                public Object supply(EzyBeanContext context) {
                    return new V111Prototype04();
                }

                @Override
                public Class<?> getObjectType() {
                    return V111Prototype04.class;
                }
            }))
            .addProperty(EzyBeanContext.ACTIVE_PROFILES_KEY, "alpha")
            .addProperties("v111_props3.properties")
            .addProperties(new File("test-data/v111_props1.properties"))
            .addProperties(new FileInputStream("test-data/v111_props2.properties"))
            .addProperty("hello", "world")
            .addProperty("pack1.hello1", "world1")
            .addPrototypeClass(V111Prototype05.class)
            .propertiesMap(() -> {
                Map<String, String> m = new HashMap<>();
                m.put("v111_c", "hellococo");
                return m;
            })
            .propertiesBeanClass("", PropertiesCombine.class);
        EzyBeanContext context = builder.build();
        SingletonX1 x1 = (SingletonX1) context.getBean("singletonX1", SingletonX1.class);
        SingletonX2 x2 = (SingletonX2) context.getBean("singletonX2", SingletonX2.class);

        assert context.getAnnotatedBean(V111Combine0Ann1.class) != null;
        try {
            context.getAnnotatedBean(V111Combine0Ann2.class);
        } catch (Exception e) {
            assert e instanceof IllegalArgumentException;
        }
        assert context.getProperties().get("v111_a").equals("hello");
        assert context.getProperties().get("v111_b").equals("world");
        assert context.getProperties().get("v111_c").equals("helloworld");
        assert context.getProperties().get("hellococo").equals("helloworld");
        assert context.getBean(V111Singleton05.class) != null;
        assert context.getBean(V111Prototype04.class) != null;
        assert !V111Singleton01Impl01.class.isAssignableFrom(V111ISingleton01.class);

        assert x1.getSingletonX1() == x1;
        assert x1.getSingletonX2() == x2;
        assert x2.getSingletonX1() == x1;
        assert x2.getSingletonX2() == x2;
        assert context.getBean("v111Prototype02New", V111Prototype02.class) != null;
        assert context.getBean("v111Prototype03New", V111Prototype03.class) != null;
        assert context.getBean("v111Singleton03New", V111Singleton03.class) != null;
        assert context.getBean("v111Singleton04New", V111Singleton04.class) != null;
        assert context.getBean(V111Singleton01.class) != null;
        assert context.getSingleton(V111ISingleton01.class) != null;
        assert context.getSingleton(V111Singleton01Impl01.class) == null;
        assert context.getBean(V111Singleton02.class) != null;
        assert context.getBean(V111Prototype01.class) != null;
        assert context.getBean(V111IPrototype01.class) != null;
        try {
            assert context.getBean(V111Prototype01Impl01.class) == null;
        } catch (Exception e) {
            assert e instanceof IllegalArgumentException;
        }

        Singleton1 singleton1 = (Singleton1) context.getBean("s1", Singleton1.class);
        assert singleton1 != null;
        assert singleton1.getMap() != null;

        ClassA12 a121 = (ClassA12) context.getBean("classA12", ClassA12.class);
        ClassA12 a122 = (ClassA12) context.getBean("classA12", ClassA12.class);
        assert a121 != a122;

        EzySingletonFactory singletonFactory = context.getSingletonFactory();
        EzyPrototypeFactory prototypeFactory = context.getPrototypeFactory();

        EzyPrototypeSupplier a1Supplier = prototypeFactory.getSupplier("a1", ClassA1.class);
        prototypeFactory.getProperties(a1Supplier);

        Object s1Singleton = singletonFactory.getSingleton("s1", Object.class);
        singletonFactory.getProperties(s1Singleton);

        List<Object> singletons = singletonFactory.getSingletons(EzyCombine0Ann.class);
        assert singletons.size() == 2;
        assert context.getAnnotatedBean(EzyCombine0Ann.class) != null;

        List<EzyPrototypeSupplier> prototypeSuppliers = prototypeFactory.getSuppliers(EzyCombine0Ann.class);
        assert prototypeSuppliers.size() == 3;

        PropertiesCombine propertiesCombine = context.getSingleton(PropertiesCombine.class);
        assert propertiesCombine.getHello().equals("world");
        assert propertiesCombine.getPack1().getHello1().equals("world1");

        PropertiesPack0 propertiesPack0 = context.getSingleton(PropertiesPack0.class);
        assert propertiesPack0.getHello1().equals("world1");
        PropertiesPack1 propertiesPack1 = context.getSingleton(PropertiesPack1.class);
        assert propertiesPack1.getHello1().equals("world1");
        ApplicationProperties0 applicationProperties0 = context.getSingleton(ApplicationProperties0.class);
        assert applicationProperties0.getHello().equals("world");
        assert applicationProperties0.getServer().equals("1.1.1.1");
        assert applicationProperties0.getFoo().equals("bar");
        assert applicationProperties0.getPort() == 3006;

        Singleton20 singleton201 = context.getSingleton(Singleton20.class);
        Singleton20 singleton202 = context.getBeanCast(Singleton20.class);
        Singleton20 singleton203 = context.getSingletonFactory().getSingletonCast(Singleton20.class);
        assert singleton201 == singleton202;
        assert singleton201 == singleton203;
        assert singleton203.getSgt10() != null;
        assert singleton203.getList() != null;
        assert singleton203.getHello() != null;

        ClassB1 classB1 = context.getPrototype(ClassB1.class);
        assert classB1.getHello() == null;
        assert classB1.getFoo() == null;

        SingletonX1 singletonX1 = context.getSingleton(SingletonX1.class);
        SingletonX4 singletonX4 = context.getSingleton(SingletonX4.class);
        assert singletonX4.getSingletonX1() == singletonX1;

        PrototypeX1 prototypeX1 = context.getPrototype(PrototypeX1.class);
        assert prototypeX1.getSingletonX1() == singletonX1;
    }

    @Test
    public void bindingToFinalFieldTest() {
        // given
        EzyBeanContext beanContext = EzyBeanContext.builder()
            .addProperty("property1", "value2")
            .addSingletonClass(InternalSingleton1.class)
            .addSingletonClass(InternalSingleton2.class)
            .build();

        // when
        InternalSingleton2 internalSingleton2 = beanContext.getSingleton(InternalSingleton2.class);

        // then
        InternalSingleton1 internalSingleton1 = beanContext.getSingleton(InternalSingleton1.class);
        assert internalSingleton1.internalSingleton2 != internalSingleton2;
        Asserts.assertEquals(internalSingleton1.property1, "value1");
    }

    public static class InternalSingleton1 {
        @EzyProperty
        public final String property1 = "value1";
        @EzyAutoBind
        public final InternalSingleton2 internalSingleton2 = new InternalSingleton2();
    }

    public static class InternalSingleton2 {

    }
}
