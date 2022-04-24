package com.tvd12.ezyfox.testing.codec;

import com.tvd12.ezyfox.codec.EzyObjectDeserializer;
import org.testng.annotations.Test;

import java.nio.ByteBuffer;

@SuppressWarnings("unchecked")
public class EzyObjectDeserializerTest {

    @Test
    public void test() {
        ExEzyObjectDeserializer deserializer = new ExEzyObjectDeserializer();
        ByteBuffer buffer = ByteBuffer.allocate(1);
        assert deserializer.read(buffer) == buffer;
        assert deserializer.read(new byte[]{1}) != null;
        assert deserializer.deserialize(new byte[]{1}) != null;
        assert deserializer.deserialize("abc") != null;
    }

    public static class ExEzyObjectDeserializer implements EzyObjectDeserializer {
        @Override
        public <T> T deserialize(ByteBuffer buffer) {
            return (T) buffer;
        }
    }
}
