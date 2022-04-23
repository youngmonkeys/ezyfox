package com.tvd12.ezyfox.testing.util;

import com.tvd12.ezyfox.util.EzyRoProperties;
import com.tvd12.test.base.BaseTest;
import org.testng.annotations.Test;

import java.util.Properties;

public class EzyRoPropertiesTest extends BaseTest {

    @Test
    public void test() {
        EzyRoProperties properties = new EzyRoProperties() {

            @Override
            public <T> T getProperty(Object key, Class<T> clazz) {
                return null;
            }

            @SuppressWarnings("unchecked")
            @Override
            public String getProperty(Object key) {
                return "hello";
            }

            @Override
            public Properties getProperties() {
                return null;
            }

            @Override
            public boolean containsKey(Object key) {
                return false;
            }
        };

        assert properties.getProperty(String.class).equals("hello");
    }
}
