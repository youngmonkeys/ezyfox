package com.tvd12.ezyfox.codec.testing;

import com.tvd12.ezyfox.builder.EzyObjectBuilder;
import com.tvd12.ezyfox.codec.MsgPackSimpleDeserializer;
import com.tvd12.ezyfox.entity.EzyArray;
import com.tvd12.ezyfox.entity.EzyObject;
import com.tvd12.ezyfox.exception.EzyCodecException;
import com.tvd12.ezyfox.io.EzyInts;
import com.tvd12.ezyfox.io.EzyLongs;
import com.tvd12.test.assertion.Asserts;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Arrays;

public class MsgPackSimpleDeserializerTest extends MsgPackCodecTest {

    @Test
    public void unsupportedTypeTest() {
        // given
        MsgPackSimpleDeserializer deserializer = new MsgPackSimpleDeserializer();
        byte[] neverUsed = new byte[]{(byte) 0xc1};
        byte[] fixExt1 = new byte[]{(byte) 0xd4, 1, 1};

        // when
        Throwable e1 = Asserts.assertThrows(() -> deserializer.deserialize(neverUsed));
        Throwable e2 = Asserts.assertThrows(() -> deserializer.deserialize(fixExt1));

        // then
        Asserts.assertEqualsType(e1, EzyCodecException.class);
        Asserts.assertEqualsType(e2, EzyCodecException.class);
    }

    @Test
    public void deepNestedArrayTest() {
        // given
        MsgPackSimpleDeserializer deserializer = new MsgPackSimpleDeserializer();
        int depth = 100_000;
        byte[] bytes = new byte[depth + 1];
        Arrays.fill(bytes, (byte) 0x91);
        bytes[depth] = (byte) 0x90;

        // when
        EzyArray actual = deserializer.deserialize(bytes);

        // then
        EzyArray current = actual;
        for (int i = 0; i < depth; ++i) {
            Asserts.assertEquals(current.size(), 1);
            current = current.get(0);
        }
        Asserts.assertEquals(current.size(), 0);
    }

    @Test
    public void deepNestedMapTest() {
        // given
        MsgPackSimpleDeserializer deserializer = new MsgPackSimpleDeserializer();
        int depth = 50_000;
        byte[] bytes = new byte[depth * 2 + 1];
        for (int i = 0; i < depth; ++i) {
            bytes[i * 2] = (byte) 0x81;
            bytes[i * 2 + 1] = 1;
        }
        bytes[depth * 2] = (byte) 0x80;

        // when
        EzyObject actual = deserializer.deserialize(bytes);

        // then
        EzyObject current = actual;
        for (int i = 0; i < depth; ++i) {
            Asserts.assertEquals(current.size(), 1);
            current = current.get(1);
        }
        Asserts.assertEquals(current.size(), 0);
    }

    @Test
    public void truncatedNestedDataTest() {
        // given
        MsgPackSimpleDeserializer deserializer = new MsgPackSimpleDeserializer();
        byte[] bytes = new byte[100_000];
        Arrays.fill(bytes, (byte) 0x91);

        // when
        Throwable e = Asserts.assertThrows(() -> deserializer.deserialize(bytes));

        // then
        Asserts.assertEqualsType(e, EzyCodecException.class);
    }

    @Test
    public void mixedNestedTest() {
        // given
        MsgPackSimpleDeserializer deserializer = new MsgPackSimpleDeserializer();
        byte[] bytes = new byte[]{
            (byte) 0x92,
            (byte) 0x82, 1, (byte) 0x91, 2, 3, (byte) 0x80,
            (byte) 0xa1, 'x'
        };

        // when
        EzyArray actual = deserializer.deserialize(bytes);

        // then
        Asserts.assertEquals(actual.size(), 2);
        EzyObject first = actual.get(0);
        Asserts.assertEquals(((EzyArray) first.get(1)).size(), 1);
        Asserts.assertEquals(((EzyObject) first.get(3)).size(), 0);
        Asserts.assertEquals(actual.get(1, String.class), "x");
    }

    @Test
    public void test1() throws IOException {
        EzyObjectBuilder dataBuilder = newObjectBuilder()
            .append("a", 1)
            .append("b", 2)
            .append("c", (Integer) null)
            .append("d", false)
            .append("e", true)
            .append("f", new byte[]{'a', 'b', 'c'})
            .append("g", 1) // fix uint
            .append("h", -10)
            .append("i", EzyInts.bin2int(6))
            .append("j", EzyInts.bin2int(12))
            .append("k", EzyInts.bin2int(18))
            .append("l", EzyLongs.bin2long(34))
            .append("m", -EzyInts.bin2int(6))
            .append("n", -EzyInts.bin2int(12))
            .append("o", -EzyInts.bin2int(18))
            .append("p", -EzyLongs.bin2long(34));
        EzyArray request = newArrayBuilder()
            .append(15)
            .append(26)
            .append("abcdef")
            .append(dataBuilder)
            .build();
        byte[] bytes = msgPack.write(request);
        MsgPackSimpleDeserializer deserializer = new MsgPackSimpleDeserializer();
        EzyArray answer = deserializer.deserialize(bytes);
        getLogger().info("answer = {}", answer);
        int appId = answer.get(0);
        int command = answer.get(1);
        String token = answer.get(2);
        assert appId == 15 : "deserialize error";
        assert command == 26 : "deserialize error";
        assert token.equals("abcdef") : "deserialize error";
        Asserts.assertEquals(token, "abcdef");
    }
}
