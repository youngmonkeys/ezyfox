package com.tvd12.ezyfox.bean.testing;

import com.tvd12.ezyfox.bean.EzyBeanContext;
import com.tvd12.ezyfox.bean.EzyBeanContextBuilder;
import com.tvd12.test.assertion.Asserts;
import org.testng.annotations.Test;

import java.io.File;
import java.util.Collections;
import java.util.Properties;

public class EzySimpleBeanContextPropertiesTest {

    @Test
    public void classpathPropertiesFile() {
        System.setProperty(EzyBeanContext.EZYFOX_ACTIVE_PROFILES_KEY, "");
        System.setProperty(EzyBeanContext.ACTIVE_PROFILES_KEY, "alpha");
        EzyBeanContextBuilder beanContextBuilder = EzyBeanContext.builder()
            .addProperties("config/config.properties", "alpha")
            .addProperties("config/config.yaml", "alpha")
            .addProperties(Collections.singletonList("config/config.properties"))
            .addProperties(Collections.singletonList("config/config.properties"), "alpha");
        Asserts.assertTrue(beanContextBuilder.getProperties().size() > 0);
        EzyBeanContext beanContext = beanContextBuilder.build();
        Properties properties = beanContext.getProperties();
        Asserts.assertEquals("ezyfox", properties.getProperty("username"));
        Asserts.assertEquals("Going", properties.getProperty("nick"));
        Asserts.assertEquals("123456", properties.getProperty("password"));
        Asserts.assertEquals("abc", properties.getProperty("scret"));
        Asserts.assertEquals("world", properties.getProperty("hello"));
        Asserts.assertEquals("1.1.1.1", properties.getProperty("server"));
        Asserts.assertEquals("world", properties.getProperty("hello"));
        Asserts.assertEquals("bar", properties.getProperty("foo"));
        Asserts.assertEquals("3006", properties.getProperty("port"));
    }

    @Test
    public void systemPropertiesFile() {
        System.setProperty(EzyBeanContext.ACTIVE_PROFILES_KEY, "");
        System.setProperty(EzyBeanContext.EZYFOX_ACTIVE_PROFILES_KEY, "alpha");
        EzyBeanContext beanContext = EzyBeanContext.builder()
            .addProperties(new File("test-data/v111_props1.properties"), "alpha")
            .addProperties(Collections.singletonList(new File("test-data/v111_props1.properties")))
            .addProperties(Collections.singletonList(new File("test-data/v111_props1.properties")), "alpha")
            .build();
        Properties properties = beanContext.getProperties();
        Asserts.assertEquals("hello", properties.getProperty("v111_a"));
        Asserts.assertEquals("200", properties.getProperty("value"));
        Asserts.assertEquals("world", properties.getProperty("hello"));
        Asserts.assertEquals("1.1.1.1", properties.getProperty("server"));
        Asserts.assertEquals("world", properties.getProperty("hello"));
        Asserts.assertEquals("bar", properties.getProperty("foo"));
        Asserts.assertEquals("3006", properties.getProperty("port"));
    }
}
