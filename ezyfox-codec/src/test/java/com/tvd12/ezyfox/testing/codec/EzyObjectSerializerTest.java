package com.tvd12.ezyfox.testing.codec;

import com.tvd12.ezyfox.codec.EzyObjectSerializer;
import org.testng.annotations.Test;

public class EzyObjectSerializerTest {

    @Test
    public void test() {
        ExEzyObjectSerializer serializer = new ExEzyObjectSerializer();
        assert serializer.write("abc") != null;
    }

    public static class ExEzyObjectSerializer implements EzyObjectSerializer {

        public byte[] serialize(Object value) {
            return value.toString().getBytes();
        }
    }
}
