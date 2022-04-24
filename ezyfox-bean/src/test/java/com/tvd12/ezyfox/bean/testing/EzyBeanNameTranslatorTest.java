package com.tvd12.ezyfox.bean.testing;

import com.tvd12.ezyfox.bean.EzyBeanContext;
import com.tvd12.ezyfox.bean.EzyBeanContextBuilder;
import com.tvd12.ezyfox.bean.EzyBeanNameTranslator;
import com.tvd12.ezyfox.bean.testing.combine.*;
import com.tvd12.ezyfox.properties.EzySimplePropertiesReader;
import com.tvd12.ezyfox.reflect.EzyClasses;
import com.tvd12.test.base.BaseTest;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class EzyBeanNameTranslatorTest extends BaseTest {

    @Test
    public void test() {
        EzyBeanContextBuilder builder = EzyBeanContext.builder()
            .scan(
                "com.tvd12.ezyfox.bean.testing.combine"
            )
            .addProperties(new HashMap<>())
            .propertiesReader(new EzySimplePropertiesReader())
            .addSingletonClasses(SingletonX1.class,
                SingletonX2.class,
                Singleton12.class)
            .addPrototypeClasses(ClassA12.class)
            .addSingleton("singleton2", new SingletonX3());
        EzyBeanContext context = builder.build();
        EzyBeanNameTranslator translator = context.getBeanNameTranslator();
        translator.map("map", ConcurrentHashMap.class, EzyClasses.getVariableName(ConcurrentHashMap.class));
        Object concurrentHashMap = context.getBean(ConcurrentHashMap.class);
        assert concurrentHashMap != null;
        Object map = context.getBean("map", ConcurrentHashMap.class);
        assert map != null;
        assert map instanceof ConcurrentHashMap;
    }
}
