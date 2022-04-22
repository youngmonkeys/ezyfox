package com.tvd12.ezyfox.bean.v120.testing;

import java.util.Arrays;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.bean.EzyBeanContext;
import com.tvd12.ezyfox.bean.EzyBeanContextBuilder;
import com.tvd12.ezyfox.bean.impl.EzyBeanKey;
import com.tvd12.ezyfox.bean.v120.testing.packet01.ConfigAfter11;
import com.tvd12.ezyfox.bean.v120.testing.packet01.ConfigurationBefore11;
import com.tvd12.ezyfox.bean.v120.testing.packet01.LastSingleton11;
import com.tvd12.ezyfox.bean.v120.testing.packet01.Singleton11;
import com.tvd12.ezyfox.bean.v120.testing.packet02.ConfigAfter21;
import com.tvd12.ezyfox.bean.v120.testing.packet02.ConfigAfter22;
import com.tvd12.ezyfox.bean.v120.testing.packet02.ConfigAfter23;
import com.tvd12.ezyfox.bean.v120.testing.packet02.LastSingleton21;
import com.tvd12.ezyfox.bean.v120.testing.packet02.LastSingleton22;
import com.tvd12.ezyfox.bean.v120.testing.packet02.LastSingleton23;
import com.tvd12.ezyfox.bean.v120.testing.packet02.Singleton21;
import com.tvd12.ezyfox.bean.v120.testing.packet03.Config33;
import com.tvd12.ezyfox.bean.v120.testing.packet03.ConfigFailed31;
import com.tvd12.ezyfox.bean.v120.testing.packet03.Singleton31;
import com.tvd12.ezyfox.bean.v120.testing.packet03.Singleton32;
import com.tvd12.ezyfox.collect.Sets;
import com.tvd12.ezyfox.reflect.EzyReflection;
import com.tvd12.ezyfox.reflect.EzyReflectionProxy;
import com.tvd12.test.assertion.Asserts;

public class EzySimpleBeanContextTest {

    @Test
    public void scanPackagesScanClassesTest() {
        // given
        EzyReflection reflection = new EzyReflectionProxy("com.tvd12.ezyfox.bean.v120.testing");
        EzyBeanContext beanContext = EzyBeanContext.builder()
                .addAllClasses(reflection)
                .build();

        // when
        Set<String> packagesToScan = beanContext.getPackagesToScan();

        // then
        Set<String> expectation = Sets.newHashSet(
            "com.tvd12.ezyfox.bean.v120.testing.packet01",
            "com.tvd12.ezyfox.bean.v120.testing.packet02"
        );
        Asserts.assertEquals(expectation, packagesToScan);
    }

    @Test
    public void configAfterTest() {
        // given
        EzyBeanContext beanContext = EzyBeanContext.builder()
                .scan("com.tvd12.ezyfox.bean.v120.testing")
                .addConfigurationAfterClass(ConfigAfter21.class)
                .addConfigurationAfterClasses(ConfigAfter22.class, ConfigAfter22.class)
                .addConfigurationAfterClasses(Arrays.asList(ConfigAfter23.class))
                .build();

        // when
        LastSingleton11 lastSingleton11 = beanContext.getSingleton(LastSingleton11.class);
        LastSingleton21 lastSingleton21 = beanContext.getSingleton(LastSingleton21.class);
        LastSingleton22 lastSingleton22 = beanContext.getSingleton(LastSingleton22.class);
        LastSingleton23 lastSingleton23 = beanContext.getSingleton(LastSingleton23.class);

        // then
        Asserts.assertNotNull(lastSingleton11);
        Asserts.assertNotNull(lastSingleton21);
        Asserts.assertNotNull(lastSingleton22);
        Asserts.assertNotNull(lastSingleton23);
    }

    @Test
    public void addSingletonTest() {
        // given
        EzyBeanContext beanContext = EzyBeanContext.builder()
                .scan("com.tvd12.ezyfox.bean.v120.testing")
                .build();
        EzyBeanContext sut = EzyBeanContext.builder()
                .addSingleton(beanContext.getBean(Singleton11.class))
                .addSingleton(beanContext.getBean(Singleton21.class))
                .build();

        // when
        Map<EzyBeanKey, Object> singletonMapByKey = sut.getSingletonMapByKey();

        // then
        Asserts.assertNotNull(singletonMapByKey.get(EzyBeanKey.of("singleton11", Singleton11.class)));
        Asserts.assertNotNull(singletonMapByKey.get(EzyBeanKey.of("singleton11", Object.class)));
        Asserts.assertNotNull(singletonMapByKey.get(EzyBeanKey.of("singleton21", Singleton21.class)));
        Asserts.assertNotNull(singletonMapByKey.get(EzyBeanKey.of("singleton21", Object.class)));
    }

    @Test
    public void addSingletonsByKeyTest() {
        // given
        EzyBeanContext beanContext = EzyBeanContext.builder()
                .scan("com.tvd12.ezyfox.bean.v120.testing")
                .build();
        EzyBeanContext sut = EzyBeanContext.builder()
                .addSingletonsByKey(beanContext.getSingletonMapByKey())
                .build();

        // when
        Map<EzyBeanKey, Object> singletonMapByKey = sut.getSingletonMapByKey();

        // then
        Asserts.assertNotNull(singletonMapByKey.get(EzyBeanKey.of("singleton11", Singleton11.class)));
        Asserts.assertNotNull(singletonMapByKey.get(EzyBeanKey.of("singleton11", Object.class)));
        Asserts.assertNotNull(singletonMapByKey.get(EzyBeanKey.of("singleton21", Singleton21.class)));
        Asserts.assertNotNull(singletonMapByKey.get(EzyBeanKey.of("singleton21", Object.class)));
    }

    @Test
    public void setPropertiesVariables() {
        // given
        System.setProperty(EzyBeanContext.ACTIVE_PROFILES_KEY, "alpha");
        System.setProperty("v120.hello", "World");
        EzyBeanContext sut = EzyBeanContext.builder()
                .addProperty("hi", "${v120.hello}")
                .addProperties("v120_application.properties")
                .build();

        // when
        Properties properties = sut.getProperties();

        // then
        Asserts.assertEquals("World", properties.get("v120.hello"));
        Asserts.assertEquals("World", properties.get("hi"));
        Asserts.assertEquals("alpha", properties.get("url"));
    }

    @Test
    public void excludePackagesTest() {
        // given
        EzyBeanContext beanContext = EzyBeanContext.builder()
                .scan("com.tvd12.ezyfox.bean.v120.testing.packet01")
                .scan("com.tvd12.ezyfox.bean.v120.testing.packet02")
                .excludePackages("com.tvd12.ezyfox.bean.v120.testing.packet01")
                .excludePackages(Arrays.asList("com.tvd12.ezyfox.bean.v120.testing.packet02"))
                .build();

        // when
        Set<String> packagesToScan = beanContext.getPackagesToScan();

        // then
        Asserts.assertTrue(packagesToScan.isEmpty());
    }

    @Test
    public void excludePackages2Test() {
        // given
        EzyBeanContext beanContext = EzyBeanContext.builder()
                .addAllClasses(
                    new EzyReflectionProxy(
                        Arrays.asList(
                            "com.tvd12.ezyfox.bean.v120.testing.packet01",
                            "com.tvd12.ezyfox.bean.v120.testing.packet02"
                        )
                    )
                )
                .excludePackages("com.tvd12.ezyfox.bean.v120.testing.packet01")
                .excludePackages(Arrays.asList("com.tvd12.ezyfox.bean.v120.testing.packet02"))
                .build();

        // when
        Set<String> packagesToScan = beanContext.getPackagesToScan();

        // then
        Asserts.assertTrue(packagesToScan.isEmpty());
    }

    @Test
    public void excludeConfigurationClassesTest() {
        // given
        EzyBeanContext beanContext = EzyBeanContext.builder()
                .scan("com.tvd12.ezyfox.bean.v120.testing")
                .addConfigurationAfterClass(ConfigAfter21.class)
                .addConfigurationAfterClasses(ConfigAfter22.class, ConfigAfter22.class)
                .addConfigurationAfterClasses(Arrays.asList(ConfigAfter23.class))
                .excludeConfigurationClass(ConfigAfter11.class)
                .excludeConfigurationClasses(ConfigAfter21.class, ConfigAfter22.class)
                .excludeConfigurationClasses(Arrays.asList(ConfigAfter22.class, ConfigAfter23.class))
                .build();

        // when
        LastSingleton11 lastSingleton11 = beanContext.getSingleton(LastSingleton11.class);
        LastSingleton21 lastSingleton21 = beanContext.getSingleton(LastSingleton21.class);
        LastSingleton22 lastSingleton22 = beanContext.getSingleton(LastSingleton22.class);
        LastSingleton23 lastSingleton23 = beanContext.getSingleton(LastSingleton23.class);

        // then
        Asserts.assertNull(lastSingleton11);
        Asserts.assertNull(lastSingleton21);
        Asserts.assertNull(lastSingleton22);
        Asserts.assertNull(lastSingleton23);
    }

    @Test
    public void addConfigurationBeforeClassTest() {
        // given
        EzyBeanContext beanContext = EzyBeanContext.builder()
                .addConfigurationClass(ConfigurationBefore11.class)
                .build();

        // when
        ConfigurationBefore11 configurationBefore11 = beanContext.getSingleton(ConfigurationBefore11.class);

        // then
        Asserts.assertNotNull(configurationBefore11);
    }

    @Test
    public void addConfigurationAfterClassesTest() {
        // given
        EzyBeanContext beanContext = EzyBeanContext.builder()
                .addSingletonClass(Singleton11.class)
                .addConfigurationClass(ConfigAfter11.class)
                .build();

        // when
        LastSingleton11 lastSingleton11 = beanContext.getSingleton(LastSingleton11.class);

        // then
        Asserts.assertNotNull(lastSingleton11);
    }

    @Test
    public void exclusiveClassConfigTest() {
        // given
        EzyBeanContext beanContext = EzyBeanContext.builder()
                .scan("com.tvd12.ezyfox.bean.v120.testing.packet03")
                .build();

        // when
        Singleton31 singleton31 = beanContext.getSingleton(Singleton31.class);
        Singleton32 singleton32 = beanContext.getSingleton(Singleton32.class);

        // then
        Asserts.assertNotNull(singleton31);
        Asserts.assertNull(singleton32);
    }

    @Test
    public void autoConfigurationFailed() {
        // given
        EzyBeanContext beanContext = EzyBeanContext.builder()
                .addConfigurationBeforeClass(Config33.class)
                .build();

        // when
        Config33 config33 = beanContext.getSingleton(Config33.class);

        // then
        Asserts.assertNotNull(config33);
    }

    @Test
    public void configurationFailed() {
        // given
        EzyBeanContextBuilder builder = EzyBeanContext.builder()
                .addConfigurationBeforeClass(ConfigFailed31.class);

        // when
        Throwable e = Asserts.assertThrows(() -> builder.build());

        // then
        Asserts.assertEquals(RuntimeException.class, e.getClass());
    }}