package com.tvd12.ezyfox.testing.codec;

import com.tvd12.ezyfox.codec.EzyAbstractToBytesSerializer;
import com.tvd12.ezyfox.function.EzyParser;
import com.tvd12.test.base.BaseTest;
import org.testng.annotations.Test;

import java.util.Map;

public class EzyAbstractSerializerTest extends BaseTest {

    @Test
    public void test() {
        EzyAbstractToBytesSerializer serializer = new Serializer();
        assert serializer.serialize(null) == null;
        assert serializer.serialize("abc") != null;
    }

    @Test(expectedExceptions = {IllegalArgumentException.class})
    public void test1() {
        EzyAbstractToBytesSerializer serializer = new Serializer();
        serializer.serialize(new EzyAbstractSerializerTest());
    }

    @Test
    public void test2() {
        EzyAbstractToBytesSerializer serializer = new Serializer1();
        serializer.serialize(new EzyAbstractSerializerTest());
    }

    public static class Serializer extends EzyAbstractToBytesSerializer {

        @Override
        protected byte[] parseNil() {
            return null;
        }

        @Override
        protected void addParsers(Map<Class<?>, EzyParser<Object, byte[]>> parsers) {
            parsers.put(String.class, input -> input.toString().getBytes());
        }
    }

    public static class Serializer1 extends EzyAbstractToBytesSerializer {

        @Override
        protected byte[] parseNil() {
            return null;
        }

        @Override
        protected byte[] parseWithNoParser(Object value) {
            return new byte[]{};
        }

        @Override
        protected void addParsers(Map<Class<?>, EzyParser<Object, byte[]>> parsers) {
            parsers.put(String.class, input -> input.toString().getBytes());
        }
    }
}
