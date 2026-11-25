package com.tvd12.ezyfox.codec.testing;

import com.tvd12.ezyfox.codec.MsgPackSimpleDeserializer;
import com.tvd12.ezyfox.entity.EzyArray;
import com.tvd12.ezyfox.entity.EzyObject;
import com.tvd12.test.assertion.Asserts;
import org.testng.annotations.Test;

import java.nio.ByteBuffer;

public class MsgPackSimpleDeserializerParseMethodsTest {

    @Test
    public void deserializeByTypeTest() {
        new ExDeserializer().verifyDeserializeByType();
    }

    @Test
    public void parseMapMethodsTest() {
        new ExDeserializer().verifyMaps();
    }

    @Test
    public void parseArrayMethodsTest() {
        new ExDeserializer().verifyArrays();
    }

    private static ByteBuffer wrap(int... values) {
        byte[] bytes = new byte[values.length];
        for (int i = 0; i < values.length; ++i) {
            bytes[i] = (byte) values[i];
        }
        return ByteBuffer.wrap(bytes);
    }

    private static class ExDeserializer extends MsgPackSimpleDeserializer {

        private void verifyDeserializeByType() {
            ByteBuffer buffer = wrap(0xa3, 'a', 'b', 'c', 0x01);
            buffer.position(1);

            Object actual = deserialize(buffer, 0xa3);

            Asserts.assertEquals(actual, "abc");
            Asserts.assertEquals(buffer.remaining(), 1);
        }

        private void verifyMaps() {
            ByteBuffer fixMapBuffer = wrap(0x81, 0xa1, 'a', 0x02);
            ByteBuffer map16Buffer = wrap(0xde, 0x00, 0x01, 0xa1, 'b', 0x03);
            ByteBuffer map32Buffer = wrap(0xdf, 0x00, 0x00, 0x00, 0x01, 0xa1, 'c', 0x04);
            ByteBuffer emptyBuffer = wrap(0x01);

            EzyObject fixMap = parseFixMap(fixMapBuffer);
            EzyObject map16 = parseMap16(map16Buffer);
            EzyObject map32 = parseMap32(map32Buffer);
            EzyObject empty = parseMap(emptyBuffer, 0);

            Asserts.assertEquals(fixMap.size(), 1);
            Asserts.assertEquals(((Number) fixMap.get("a")).intValue(), 2);
            Asserts.assertEquals(map16.size(), 1);
            Asserts.assertEquals(((Number) map16.get("b")).intValue(), 3);
            Asserts.assertEquals(map32.size(), 1);
            Asserts.assertEquals(((Number) map32.get("c")).intValue(), 4);
            Asserts.assertEquals(empty.size(), 0);
            Asserts.assertEquals(fixMapBuffer.remaining(), 0);
            Asserts.assertEquals(map16Buffer.remaining(), 0);
            Asserts.assertEquals(map32Buffer.remaining(), 0);
            Asserts.assertEquals(emptyBuffer.remaining(), 1);
        }

        private void verifyArrays() {
            ByteBuffer fixArrayBuffer = wrap(0x92, 0x01, 0xa1, 'a');
            ByteBuffer array16Buffer = wrap(0xdc, 0x00, 0x01, 0x05);
            ByteBuffer array32Buffer = wrap(0xdd, 0x00, 0x00, 0x00, 0x01, 0xc3);
            ByteBuffer emptyBuffer = wrap(0x01);

            EzyArray fixArray = parseFixArray(fixArrayBuffer);
            EzyArray array16 = parseArray16(array16Buffer);
            EzyArray array32 = parseArray32(array32Buffer);
            EzyArray empty = parseArray(emptyBuffer, 0);

            Asserts.assertEquals(fixArray.size(), 2);
            Asserts.assertEquals(((Number) fixArray.get(0)).intValue(), 1);
            Asserts.assertEquals(fixArray.<String>get(1), "a");
            Asserts.assertEquals(array16.size(), 1);
            Asserts.assertEquals(((Number) array16.get(0)).intValue(), 5);
            Asserts.assertEquals(array32.size(), 1);
            Asserts.assertEquals(array32.<Boolean>get(0), Boolean.TRUE);
            Asserts.assertEquals(empty.size(), 0);
            Asserts.assertEquals(fixArrayBuffer.remaining(), 0);
            Asserts.assertEquals(array16Buffer.remaining(), 0);
            Asserts.assertEquals(array32Buffer.remaining(), 0);
            Asserts.assertEquals(emptyBuffer.remaining(), 1);
        }
    }
}
