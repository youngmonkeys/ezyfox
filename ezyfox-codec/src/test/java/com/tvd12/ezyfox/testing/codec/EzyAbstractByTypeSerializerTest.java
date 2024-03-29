package com.tvd12.ezyfox.testing.codec;

import com.tvd12.ezyfox.codec.EzyAbstractByTypeSerializer;
import com.tvd12.ezyfox.function.EzyParser;
import com.tvd12.test.base.BaseTest;
import org.testng.annotations.Test;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class EzyAbstractByTypeSerializerTest extends BaseTest {

    @Test
    public void test() {
        EzyAbstractByTypeSerializer serializer = new Serializer();
        assert serializer.serialize(null) == null;
        assert serializer.serialize("abc") != null;
    }

    @Test(expectedExceptions = {IllegalArgumentException.class})
    public void test1() {
        EzyAbstractByTypeSerializer serializer = new Serializer();
        serializer.serialize(new EzyAbstractByTypeSerializerTest());
    }

    @Test
    public void test2() {
        EzyAbstractByTypeSerializer serializer = new Serializer1();
        serializer.serialize(new EzyAbstractByTypeSerializerTest());
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void test3() {
        Serializer serializer = new Serializer();
        serializer.serialize("abc", Object.class);
    }

    @Test
    public void test4() {
        Serializer2 serializer = new Serializer2();
        assert serializer.serialize("abc", Object.class) == null;
    }

    public static class Serializer extends EzyAbstractByTypeSerializer {

        @Override
        protected <T> T parseNil(Class<T> outType) {
            return null;
        }

        @SuppressWarnings("rawtypes")
        @Override
        protected void addParserMap(
            Map<Class<?>, Map<Class<?>, EzyParser>> parserMaps
        ) {
            Map<Class<?>, EzyParser> map = new ConcurrentHashMap<>();
            parserMaps.put(String.class, map);
            map.put(
                byte[].class,
                (EzyParser<Object, Object>) input -> input.toString().getBytes()
            );
        }
    }

    public static class Serializer1 extends EzyAbstractByTypeSerializer {

        @Override
        protected <T> T parseNil(Class<T> outType) {
            return null;
        }

        @Override
        protected <T> T parseWithNoParsers(Object value, Class<T> outType) {
            return (T) new byte[]{};
        }

        @Override
        protected <T> T parseWithNoParser(Object value, Class<T> outType) {
            return (T) new byte[]{};
        }

        @SuppressWarnings("rawtypes")
        @Override
        protected void addParserMap(Map<Class<?>, Map<Class<?>, EzyParser>> parserMaps) {
            Map<Class<?>, EzyParser> map = new ConcurrentHashMap<>();
            parserMaps.put(String.class, map);
            map.put(
                byte[].class,
                (EzyParser<Object, Object>) input -> input.toString().getBytes()
            );
        }
    }

    public static class Serializer2 extends EzyAbstractByTypeSerializer {

        @Override
        protected <T> T parseNil(Class<T> outType) {
            return null;
        }

        @Override
        protected <T> T parseWithNoParser(Object value, Class<T> outType) {
            return null;
        }

        @Override
        protected <T> T parseWithNoParsers(Object value, Class<T> outType) {
            return null;
        }

        @SuppressWarnings("rawtypes")
        @Override
        protected void addParserMap(Map<Class<?>, Map<Class<?>, EzyParser>> parserMaps) {
            Map<Class<?>, EzyParser> map = new ConcurrentHashMap<>();
            parserMaps.put(String.class, map);
            map.put(
                byte[].class,
                (EzyParser<Object, Object>) input -> input.toString().getBytes()
            );
        }
    }
}
