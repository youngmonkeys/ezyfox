package com.tvd12.ezyfox.bean.testing;

import com.tvd12.ezyfox.bean.EzyBeanContext;
import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.bean.impl.EzyBeanKey;
import com.tvd12.ezyfox.bean.impl.EzyBeanMetadataCache;
import com.tvd12.ezyfox.bean.impl.EzyByMethodSingletonLoader;
import com.tvd12.ezyfox.io.EzyMaps;
import com.tvd12.ezyfox.reflect.EzyMethod;
import com.tvd12.test.assertion.Asserts;
import com.tvd12.test.base.BaseTest;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class EzyByMethodSingletonLoaderTest extends BaseTest {

    public A newA() {
        return new A();
    }

    public B newB() {
        return new B();
    }

    public B createB() {
        return new B();
    }

    public C newC() {
        return new C();
    }

    @Test
    public void test() throws Exception {
        EzyBeanContext context = EzyBeanContext.builder()
            .build();
        EzyMethod methodA = new EzyMethod(getClass().getDeclaredMethod("newA"));
        EzyMethod methodB = new EzyMethod(getClass().getDeclaredMethod("newB"));
        EzyByMethodSingletonLoader loader = new EzyByMethodSingletonLoader(
            "a",
            methodA,
            this,
            EzyMaps.newHashMap(EzyBeanKey.of("b", B.class), methodB),
            new EzyBeanMetadataCache()
        );

        Method getConstructorParameterTypes = EzyByMethodSingletonLoader.class
            .getDeclaredMethod("getConstructorParameterTypes", Class.class);
        getConstructorParameterTypes.setAccessible(true);
        getConstructorParameterTypes.invoke(loader, B.class);
        getConstructorParameterTypes.invoke(loader, Object.class);

        loader.load(context);
    }

    @Test
    public void loadByTypeWhenNameNotMatchedTest() throws Exception {
        // given
        EzyBeanContext context = EzyBeanContext.builder()
            .build();
        EzyMethod methodA = new EzyMethod(getClass().getDeclaredMethod("newA"));
        EzyMethod methodB = new EzyMethod(getClass().getDeclaredMethod("createB"));
        EzyMethod methodC = new EzyMethod(getClass().getDeclaredMethod("newC"));
        Map<EzyBeanKey, EzyMethod> methodsByKey = new HashMap<>();
        methodsByKey.put(EzyBeanKey.of("createB", B.class), methodB);
        methodsByKey.put(EzyBeanKey.of("b", C.class), methodC);
        EzyByMethodSingletonLoader loader = new EzyByMethodSingletonLoader(
            "a",
            methodA,
            this,
            methodsByKey,
            new EzyBeanMetadataCache()
        );

        // when
        A a = (A) loader.load(context);

        // then
        Asserts.assertNotNull(a.b);
        Asserts.assertEquals(context.getBean("createB", B.class), a.b);
        Asserts.assertNull(context.getSingletonFactory().getSingleton(EzyBeanKey.of("b", B.class)));
        Asserts.assertEquals(methodsByKey.size(), 1);
    }

    @Test
    public void useExistedSingletonWhenNameMatchedTest() throws Exception {
        // given
        B existed = new B();
        EzyBeanContext context = EzyBeanContext.builder()
            .addSingleton("b", existed)
            .build();
        EzyMethod methodA = new EzyMethod(getClass().getDeclaredMethod("newA"));
        EzyMethod methodB = new EzyMethod(getClass().getDeclaredMethod("newB"));
        Map<EzyBeanKey, EzyMethod> methodsByKey = new HashMap<>();
        methodsByKey.put(EzyBeanKey.of("b", B.class), methodB);
        EzyByMethodSingletonLoader loader = new EzyByMethodSingletonLoader(
            "a",
            methodA,
            this,
            methodsByKey,
            new EzyBeanMetadataCache()
        );

        // when
        A a = (A) loader.load(context);

        // then
        Asserts.assertEquals(a.b, existed);
        Asserts.assertEquals(methodsByKey.size(), 1);
    }

    public static class A {
        @EzyAutoBind
        public B b;
    }

    public static class B {}

    public static class C {}
}
