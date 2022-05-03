package com.tvd12.ezyfox.testing.properties;

import com.tvd12.ezyfox.properties.EzyPropertiesReader;
import com.tvd12.ezyfox.properties.EzySimplePropertiesReader;
import com.tvd12.test.assertion.Asserts;
import com.tvd12.test.base.BaseTest;
import com.tvd12.test.util.RandomUtil;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class EzySimplePropertiesReaderTest extends BaseTest {

    @Test
    public void test() {
        EzyPropertiesReader reader = new EzySimplePropertiesReader();
        Map<Object, Object> map = new HashMap<>();
        assert !reader.containsKey(map, "hello");
        map.put("hello", "world");
        assert reader.get(map, "hello").equals("world");
        assert reader.get(map, "hello", String.class).equals("world");
        assert reader.get(map, "foo", "bar").equals("bar");
        assert reader.get(map, "foo", String.class, "bar").equals("bar");
        map.put("foo", "bar");
        assert reader.get(map, "foo", "bar").equals("bar");
        assert reader.get(map, "foo", String.class, "bar").equals("bar");
    }

    @Test
    public void getFromSystemPropertyTest() {
        // given
        Properties properties = new Properties();
        String key = RandomUtil.randomShortAlphabetString();
        String value = RandomUtil.randomShortAlphabetString();
        System.setProperty(key, value);

        EzyPropertiesReader reader = new EzySimplePropertiesReader();

        // when
        Object actual = reader.get(properties, key);

        // then
        Asserts.assertEquals(actual, value);
    }

    @Test
    public void getFromSystemEnvironmentTest() {
        // given
        Properties properties = new Properties();

        EzyPropertiesReader reader = new EzySimplePropertiesReader();

        // when
        Object actual = reader.get(properties, "PATH");

        // then
        Asserts.assertEquals(actual, System.getenv("PATH"));
    }
}
