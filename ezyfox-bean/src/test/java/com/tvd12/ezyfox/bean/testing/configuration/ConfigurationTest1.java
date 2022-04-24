package com.tvd12.ezyfox.bean.testing.configuration;

import com.tvd12.ezyfox.bean.EzyBeanContext;
import com.tvd12.ezyfox.bean.impl.EzySimpleConfigurationLoader;
import com.tvd12.test.base.BaseTest;
import org.testng.annotations.Test;

public class ConfigurationTest1 extends BaseTest {

    @Test
    public void test() {
        EzyBeanContext context = EzyBeanContext.builder()
            .addSingletonClass(Singleton1.class)
            .addSingletonClass(Singleton0.class)
            .addSingletonClass(SingletonA1.class)
            .build();
        context.getSingletonFactory().addSingleton(new AvailableSingleton1());

        new EzySimpleConfigurationLoader()
            .clazz(ConfigClassA.class)
            .context(context)
            .load();
    }
}
