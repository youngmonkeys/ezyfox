package com.tvd12.ezyfox.codec.testing;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tvd12.ezyfox.codec.EzyMessageDeserializer;
import com.tvd12.ezyfox.codec.JacksonSimpleDeserializer;
import com.tvd12.ezyfox.entity.EzyArray;
import com.tvd12.test.assertion.Asserts;
import com.tvd12.test.base.BaseTest;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class JacksonSimpleDeserializerTest extends BaseTest {

    private final ObjectMapper objectMapper
        = new ObjectMapper();
    private final EzyMessageDeserializer deserializer
        = new JacksonSimpleDeserializer(objectMapper);

    @Test(expectedExceptions = {IllegalArgumentException.class})
    public void test() {
        EzyArray a1 = deserializer.deserialize(ByteBuffer.wrap(new byte[]{1, 2, 3}));
        System.out.print("a1: " + a1);
    }

    @Test
    public void test2() {
        EzyArray a1 = deserializer.deserialize("[1, 2, 3]");
        System.out.println(a1);
        String json = "[\"1\", \"2.3\", \"" + UUID.randomUUID() + "\"]";
        EzyArray a2 = deserializer.deserialize(json);
        System.out.println("a2: " + a2);
        assert a2.get(0, BigInteger.class).equals(new BigInteger("1"));
        assert a2.get(1, BigDecimal.class).equals(new BigDecimal("2.3"));
        System.out.println(a2.get(2, UUID.class));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void test3() {
        EzyArray a1 = deserializer.deserialize("1, 2, 3");
        System.out.println(a1);
    }

    @Test
    public void deserializeOnlyRemainingBytes() {
        // given
        ByteBuffer buffer = ByteBuffer.wrap("x[1,2]".getBytes(StandardCharsets.UTF_8));
        buffer.get();

        // when
        EzyArray actual = deserializer.deserialize(buffer);

        // then
        Asserts.assertEquals(actual.size(), 2);
        Asserts.assertEquals(actual.get(0, Integer.class), 1);
        Asserts.assertEquals(actual.get(1, Integer.class), 2);
        Asserts.assertEquals(buffer.remaining(), 0);
    }
}
