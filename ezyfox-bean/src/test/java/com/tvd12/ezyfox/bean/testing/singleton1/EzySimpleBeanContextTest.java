package com.tvd12.ezyfox.bean.testing.singleton1;

import com.tvd12.ezyfox.bean.EzyBeanContext;
import com.tvd12.ezyfox.bean.impl.EzyByConstructorPrototypeSupplierLoader;
import com.tvd12.ezyfox.bean.impl.EzySimpleBeanContext;
import com.tvd12.ezyfox.collect.Lists;
import com.tvd12.ezyfox.util.EzyMapBuilder;
import org.testng.annotations.Test;

import java.util.List;

public class EzySimpleBeanContextTest {

    @SuppressWarnings("unchecked")
    @Test
    public void test() throws Exception {
        EzyByConstructorPrototypeSupplierLoader.setDebug(true);

        EzyBeanContext context = EzySimpleBeanContext.builder()
            .scan("com.tvd12.ezyfox.bean.testing.singleton1")
            .build();
        List<Object> singletons = context.getSingletons(EzyMapBuilder.mapBuilder()
            .put("type", "request_listener")
            .put("cmd", "2")
            .build());
        System.out.println(singletons);
        assert singletons.size() == 2;
        assert singletons.containsAll(Lists.newArrayList(
            context.getBean("classB", ClassB.class),
            context.getBean("classC", ClassC.class)
        ));

        singletons = context.getSingletons(EzyMapBuilder.mapBuilder()
            .put("type", "request_listener")
            .put("cmd", "1")
            .build());
        System.out.println(singletons);
        assert singletons.size() == 1;
        assert singletons.containsAll(Lists.newArrayList(
            context.getBean("classA", ClassA.class)
        ));

        Object singleton = context.getSingleton(EzyMapBuilder.mapBuilder()
            .put("type", "request_listener")
            .put("cmd", "1")
            .build());
        assert singleton == context.getBean("classA", ClassA.class);

        singleton = context.getSingleton(EzyMapBuilder.mapBuilder()
            .put("type", "request_listenerzz")
            .put("cmd", "1zz")
            .build());
        assert singleton == null;
    }
}
