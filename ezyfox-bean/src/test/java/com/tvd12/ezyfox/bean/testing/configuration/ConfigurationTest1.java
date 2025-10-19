package com.tvd12.ezyfox.bean.testing.configuration;

import com.tvd12.ezyfox.bean.EzyBeanContext;
import com.tvd12.ezyfox.bean.EzyBeanContextBuilder;
import com.tvd12.ezyfox.bean.impl.EzySimpleConfigurationLoader;
import com.tvd12.test.assertion.Asserts;
import com.tvd12.test.base.BaseTest;
import org.testng.annotations.Test;

public class ConfigurationTest1 extends BaseTest {

    @Test
    public void test() {
        // given
        EzyBeanContextBuilder contextBuilder = EzyBeanContext
            .builder();
        EzyBeanContext context = contextBuilder
            .addSingletonClass(Singleton1.class)
            .addSingletonClass(Singleton0.class)
            .addSingletonClass(SingletonA1.class)
            .build();
        context.getSingletonFactory().addSingleton(new AvailableSingleton1());

        // when
        new EzySimpleConfigurationLoader()
            .clazz(ConfigClassA.class)
            .context(context)
            .contextBuilder(contextBuilder)
            .load();

        // then
        context = contextBuilder.build();
        Asserts.assertNotNull(
            context.getBean(
                ConfigClassA.SingletonBySetContextBuilder.class
            )
        );
    }
}
