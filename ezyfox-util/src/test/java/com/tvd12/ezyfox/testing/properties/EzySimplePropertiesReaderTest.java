package com.tvd12.ezyfox.testing.properties;

import com.tvd12.ezyfox.properties.EzyPropertiesReader;
import com.tvd12.ezyfox.properties.EzySimplePropertiesReader;
import com.tvd12.test.base.BaseTest;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

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
}
