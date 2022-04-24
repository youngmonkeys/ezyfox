package com.tvd12.ezyfox.bean.testing;

import com.tvd12.ezyfox.bean.impl.EzySimpleSingletonFactory;
import com.tvd12.ezyfox.util.EzyMapBuilder;
import com.tvd12.test.assertion.Asserts;
import com.tvd12.test.base.BaseTest;
import org.testng.annotations.Test;

public class EzySimpleSingletonFactoryTest extends BaseTest {

    @SuppressWarnings("unchecked")
    @Test
    public void test() {
        EzySimpleSingletonFactory factory = new EzySimpleSingletonFactory();
        factory.addSingleton("a", new A());
        factory.addSingletons(EzyMapBuilder.mapBuilder()
            .put("b", new A())
            .put("c", new C())
            .build()
        );
        assert factory.getSingleton("a", A1.class) == null;
        Asserts.assertNotNull(factory.getSingleton("b", A.class));
        Asserts.assertThat(factory.getSingleton("c", C.class))
            .test(it -> it.getClass() == C.class);
    }

    public static class A {}

    public static class A1 {}

    public static class C {}
}
