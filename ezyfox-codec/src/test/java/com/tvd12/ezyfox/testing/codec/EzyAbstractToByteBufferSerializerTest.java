package com.tvd12.ezyfox.testing.codec;

import com.tvd12.ezyfox.codec.EzyAbstractToByteBufferSerializer;
import com.tvd12.ezyfox.function.EzyParser;
import org.testng.annotations.Test;

import java.nio.ByteBuffer;
import java.util.Map;

public class EzyAbstractToByteBufferSerializerTest {

    @Test
    public void test() {
        ExEzyAbstractToByteBufferSerializer serializer = new ExEzyAbstractToByteBufferSerializer();
        assert serializer.write(null) == null;
        assert serializer.write(1) != null;
        assert serializer.serialize(1) != null;
        try {
            serializer.serialize(new Object());
        } catch (Exception e) {
            assert e instanceof IllegalArgumentException;
        }
    }

    public static class ExEzyAbstractToByteBufferSerializer extends EzyAbstractToByteBufferSerializer {

        @Override
        protected ByteBuffer parseNil() {
            return null;
        }

        @Override
        protected void addParsers(Map<Class<?>, EzyParser<Object, ByteBuffer>> parsers) {
            parsers.put(Integer.class, input -> {
                ByteBuffer buffer = ByteBuffer.allocate(4);
                buffer.putInt((Integer) input);
                return buffer;
            });
        }
    }
}
