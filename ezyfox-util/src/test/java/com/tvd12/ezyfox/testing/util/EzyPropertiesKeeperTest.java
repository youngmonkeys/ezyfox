package com.tvd12.ezyfox.testing.util;

import java.util.Properties;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.util.EzyPropertiesKeeper;
import com.tvd12.test.assertion.Asserts;
import com.tvd12.test.base.BaseTest;
import com.tvd12.test.util.RandomUtil;

public class EzyPropertiesKeeperTest extends BaseTest {

    @Test
    public void test() {
        // given
        Properties properties1 = new Properties();
        properties1.setProperty("database.username", "dungtv");
        Properties properties2 = new Properties();
        properties2.setProperty("cache.username", "dungtv1");
        properties2.setProperty("queue.username", "dungtv1");
        String key = RandomUtil.randomShortAlphabetString();
        String value = RandomUtil.randomShortHexString();
        ExPropertiesKeeper keeper = new ExPropertiesKeeper()
                .properties(properties1)
                .properties(properties1, "database")
                .properties(properties2, "cache", true)
                .propertiesFile("application-test.properties")
                .propertiesFile("application-test.properties", "alpha")
                .property(key, value);

        // when
        Properties props = keeper.getProperties();

        // then
        Asserts.assertEquals("dungtv", props.get("database.username"));
        Asserts.assertEquals("dungtv", props.get("username"));
        Asserts.assertEquals("dungtv1", props.get("cache.username"));
        Asserts.assertNull(props.get("queue.username"));
        Asserts.assertEquals(value, props.get(key));
        Asserts.assertEquals("world", props.get("hello"));
        Asserts.assertEquals("bar", props.get("foo"));
    }

    private static class ExPropertiesKeeper extends EzyPropertiesKeeper<ExPropertiesKeeper> {
        public Properties getProperties() {
            return properties;
        }
    }
}
