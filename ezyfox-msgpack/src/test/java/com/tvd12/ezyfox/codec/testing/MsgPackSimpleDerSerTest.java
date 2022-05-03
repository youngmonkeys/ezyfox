package com.tvd12.ezyfox.codec.testing;

import com.tvd12.ezyfox.codec.MsgPackConstant;
import com.tvd12.ezyfox.codec.MsgPackSimpleDeserializer;
import com.tvd12.ezyfox.codec.MsgPackSimpleSerializer;
import com.tvd12.ezyfox.entity.EzyArray;
import com.tvd12.ezyfox.entity.EzyObject;
import com.tvd12.test.assertion.Asserts;
import com.tvd12.test.util.RandomUtil;
import org.testng.annotations.Test;

import java.nio.ByteBuffer;
import java.util.Map;

public class MsgPackSimpleDerSerTest {

    private final MsgPackSimpleSerializer ser = new MsgPackSimpleSerializer();
    private final MsgPackSimpleDeserializer der = new MsgPackSimpleDeserializer();

    @Test
    public void serializePositiveFixInt() {
        // given
        int number = RandomUtil.randomInt(0x00, 0x7f + 1);

        // when
        byte[] bytes = ser.serialize(number);
        int actual = der.deserialize(bytes);

        //then
        Asserts.assertEquals(bytes.length, 1);
        Asserts.assertEquals(actual, number);
        ByteBuffer buffer = ByteBuffer.allocate(1);
        buffer.put((byte) number);
        buffer.flip();
        Asserts.assertEquals(buffer, (ByteBuffer.wrap(bytes)));
    }

    @Test
    public void serializeNegativeFixInt() {
        // given
        int number = (byte) RandomUtil.randomInt(0xe0, 0xff + 1);

        // when
        byte[] bytes = ser.serialize(number);
        int actual = der.deserialize(bytes);

        //then
        Asserts.assertEquals(bytes.length, 1);
        Asserts.assertEquals(actual, number);
        ByteBuffer buffer = ByteBuffer.allocate(1);
        buffer.put((byte) number);
        buffer.flip();
        Asserts.assertEquals(buffer, (ByteBuffer.wrap(bytes)));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void serializeFixMap() {
        // given
        int size = RandomUtil.randomInt(0, MsgPackConstant.MAX_FIXMAP_SIZE);
        Map<Integer, String> map = RandomUtil.randomMap(size, int.class, String.class);

        // when
        byte[] bytes = ser.serialize(map);
        Map<Integer, String> actual = ((EzyObject) der.deserialize(bytes)).toMap();

        //then
        Asserts.assertEquals(actual, map);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void serializeMap16() {
        // given
        int size = RandomUtil.randomInt(MsgPackConstant.MAX_FIXMAP_SIZE + 1, MsgPackConstant.MAX_MAP16_SIZE);
        Map<Integer, String> map = RandomUtil.randomMap(size, int.class, String.class);

        // when
        byte[] bytes = ser.serialize(map);
        Map<Integer, String> actual = ((EzyObject) der.deserialize(bytes)).toMap();

        //then
        Asserts.assertEquals((byte) 0xde, bytes[0]);
        Asserts.assertEquals(actual, map);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void serializeMap32() {
        // given
        int size = RandomUtil.randomInt(MsgPackConstant.MAX_MAP16_SIZE + 1, MsgPackConstant.MAX_MAP16_SIZE + 2);
        Map<Integer, String> map = RandomUtil.randomMap(size, int.class, String.class);

        // when
        byte[] bytes = ser.serialize(map);
        Map<Integer, String> actual = ((EzyObject) der.deserialize(bytes)).toMap();

        //then
        Asserts.assertEquals((byte) 0xdf, bytes[0]);
        Asserts.assertEquals(actual, map);
    }

    @Test
    public void serializeFixArray() {
        // given
        int size = RandomUtil.randomInt(0, MsgPackConstant.MAX_FIXARRAY_SIZE);
        String[] array = RandomUtil.randomArray(size, String.class);

        // when
        byte[] bytes = ser.serialize(array);
        String[] actual = ((EzyArray) der.deserialize(bytes)).toArray(String.class);

        //then
        Asserts.assertEquals(actual, array);
    }

    @Test
    public void serializeArray16() {
        // given
        int size = RandomUtil.randomInt(MsgPackConstant.MAX_FIXARRAY_SIZE + 1, MsgPackConstant.MAX_ARRAY16_SIZE);
        String[] array = RandomUtil.randomArray(size, String.class);

        // when
        byte[] bytes = ser.serialize(array);
        String[] actual = ((EzyArray) der.deserialize(bytes)).toArray(String.class);

        //then
        Asserts.assertEquals((byte) 0xdc, bytes[0]);
        Asserts.assertEquals(actual, array);
    }

    @Test
    public void serializeArray32() {
        // given
        int size = RandomUtil.randomInt(MsgPackConstant.MAX_ARRAY16_SIZE + 1, MsgPackConstant.MAX_ARRAY16_SIZE + 2);
        String[] array = RandomUtil.randomArray(size, String.class);

        // when
        byte[] bytes = ser.serialize(array);
        String[] actual = ((EzyArray) der.deserialize(bytes)).toArray(String.class);

        //then
        Asserts.assertEquals((byte) 0xdd, bytes[0]);
        Asserts.assertEquals(actual, array);
    }

    @Test
    public void serializeFixString() {
        // given
        int size = RandomUtil.randomInt(0, MsgPackConstant.MAX_FIXSTR_SIZE);
        String string = RandomUtil.randomAlphabetString(size);

        // when
        byte[] bytes = ser.serialize(string);
        String actual = der.deserialize(bytes);

        //then
        Asserts.assertEquals(actual, string);
    }

    @Test
    public void serializeString8() {
        // given
        int size = RandomUtil.randomInt(MsgPackConstant.MAX_FIXSTR_SIZE + 1, MsgPackConstant.MAX_STR8_SIZE);
        String string = RandomUtil.randomAlphabetString(size);

        // when
        byte[] bytes = ser.serialize(string);
        String actual = der.deserialize(bytes);

        //then
        Asserts.assertEquals((byte) 0xd9, bytes[0]);
        Asserts.assertEquals(actual, string);
    }

    @Test
    public void serializeString16() {
        // given
        int size = RandomUtil.randomInt(MsgPackConstant.MAX_STR8_SIZE + 1, MsgPackConstant.MAX_STR16_SIZE);
        String string = RandomUtil.randomAlphabetString(size);

        // when
        byte[] bytes = ser.serialize(string);
        String actual = der.deserialize(bytes);

        //then
        Asserts.assertEquals((byte) 0xda, bytes[0]);
        Asserts.assertEquals(actual, string);
    }

    @Test
    public void serializeString32() {
        // given
        int size = RandomUtil.randomInt(MsgPackConstant.MAX_STR16_SIZE + 1, MsgPackConstant.MAX_STR16_SIZE + 2);
        String string = RandomUtil.randomAlphabetString(size);

        // when
        byte[] bytes = ser.serialize(string);
        String actual = der.deserialize(bytes);

        //then
        Asserts.assertEquals((byte) 0xdb, bytes[0]);
        Asserts.assertEquals(actual, string);
    }

    @Test
    public void serializeFloat32() {
        // given
        float number = RandomUtil.randomFloat();

        // when
        byte[] bytes = ser.serialize(number);
        float actual = der.deserialize(bytes);

        //then
        assert bytes.length == 5;
        assert actual == number;
        ByteBuffer buffer = ByteBuffer.allocate(5);
        buffer.put((byte) 0xca);
        buffer.putFloat(number);
        buffer.flip();
        assert buffer.equals(ByteBuffer.wrap(bytes));
    }

    @Test
    public void serializeFloat64() {
        // given
        double number = RandomUtil.randomDouble();

        // when
        byte[] bytes = ser.serialize(number);
        double actual = der.deserialize(bytes);

        //then
        assert bytes.length == 9;
        assert actual == number;
        ByteBuffer buffer = ByteBuffer.allocate(9);
        buffer.put((byte) 0xcb);
        buffer.putDouble(number);
        buffer.flip();
        assert buffer.equals(ByteBuffer.wrap(bytes));
    }

    @Test
    public void serializeNegateByte() {
        // given
        byte number = (byte) RandomUtil.randomInt(Byte.MIN_VALUE, (int) MsgPackConstant.MIN_NEGATIVE_FIXINT);

        // when
        byte[] bytes = ser.serialize(number);
        int actual = der.deserialize(bytes);

        //then
        Asserts.assertEquals(2, bytes.length);
        assert actual == number;
        ByteBuffer buffer = ByteBuffer.allocate(2);
        buffer.put((byte) 0xd0);
        buffer.put(number);
        buffer.flip();
        assert buffer.equals(ByteBuffer.wrap(bytes));
    }

    @Test
    public void serializeNegateShort() {
        // given
        short number = RandomUtil.randomShort(Short.MIN_VALUE, (short) 0);

        // when
        byte[] bytes = ser.serialize(number);
        int actual = der.deserialize(bytes);

        //then
        assert bytes.length == 3;
        assert actual == number;
        ByteBuffer buffer = ByteBuffer.allocate(3);
        buffer.put((byte) 0xd1);
        buffer.putShort(number);
        buffer.flip();
        assert buffer.equals(ByteBuffer.wrap(bytes));
    }

    @Test
    public void serializeNegateInt32() {
        // given
        int number = -765067265;

        // when
        byte[] bytes = ser.serialize(number);
        int actual = der.deserialize(bytes);

        //then
        assert bytes.length == 5;
        assert actual == number;
        ByteBuffer buffer = ByteBuffer.allocate(5);
        buffer.put((byte) 0xd2);
        buffer.putInt(number);
        buffer.flip();
        assert buffer.equals(ByteBuffer.wrap(bytes));
    }

    @Test
    public void serializeNegateInt64() {
        // given
        long number = RandomUtil.randomLong(Long.MIN_VALUE, 0);

        // when
        byte[] bytes = ser.serialize(number);
        long actual = der.deserialize(bytes);

        //then
        assert bytes.length == 9;
        assert actual == number;
        ByteBuffer buffer = ByteBuffer.allocate(9);
        buffer.put((byte) 0xd3);
        buffer.putLong(number);
        buffer.flip();
        assert buffer.equals(ByteBuffer.wrap(bytes));
    }

    @Test
    public void serializeBin8() {
        // given
        int size = RandomUtil.randomInt(0, MsgPackConstant.MAX_BIN8_SIZE);
        byte[] array = RandomUtil.randomByteArray(size);

        // when
        byte[] bytes = ser.serialize(array);
        byte[] actual = der.deserialize(bytes);

        //then
        Asserts.assertEquals((byte) 0xc4, bytes[0]);
        Asserts.assertEquals(actual, array);
    }

    @Test
    public void serializeBin16() {
        // given
        int size = RandomUtil.randomInt(MsgPackConstant.MAX_BIN8_SIZE, MsgPackConstant.MAX_BIN16_SIZE);
        byte[] array = RandomUtil.randomByteArray(size);

        // when
        byte[] bytes = ser.serialize(array);
        byte[] actual = der.deserialize(bytes);

        //then
        Asserts.assertEquals((byte) 0xc5, bytes[0]);
        Asserts.assertEquals(actual, array);
    }

    @Test
    public void serializeBin32() {
        // given
        int size = RandomUtil.randomInt(MsgPackConstant.MAX_BIN16_SIZE + 1, MsgPackConstant.MAX_BIN16_SIZE + 2);
        byte[] array = RandomUtil.randomByteArray(size);

        // when
        byte[] bytes = ser.serialize(array);
        byte[] actual = der.deserialize(bytes);

        //then
        Asserts.assertEquals((byte) 0xc6, bytes[0]);
        Asserts.assertEquals(actual, array);
    }

    @Test
    public void deserializeFixNegateByteMiddle() {
        // given
        byte[] bytes = new byte[] {
            (byte) (0xe0 | -15)
        };

        // when
        int actual = der.deserialize(bytes);

        //then
        Asserts.assertEquals(actual, -15, false);
    }

    @Test
    public void deserializeFixNegateByteBoundary() {
        // given
        byte[] bytes = new byte[] {
            (byte) (0xe0 | MsgPackConstant.MIN_NEGATIVE_FIXINT)
        };

        // when

        int actual = der.deserialize(bytes);

        //then
        Asserts.assertEquals(actual, MsgPackConstant.MIN_NEGATIVE_FIXINT, false);
    }

    @Test
    public void deserializeNegateByteBoundary() {
        // given
        byte[] bytes = new byte[] {
            (byte) 0xd0,
            Byte.MIN_VALUE
        };

        // when

        int actual = der.deserialize(bytes);

        //then
        Asserts.assertEquals(actual, Byte.MIN_VALUE, false);
    }

    @Test
    public void deserializeNegateByteBoundaryOld() {
        // given
        byte[] bytes = new byte[] {
            (byte) 0xd1,
            (byte) -1,
            Byte.MIN_VALUE
        };

        // when

        int actual = der.deserialize(bytes);

        //then
        Asserts.assertEquals(actual, Byte.MIN_VALUE, false);
    }

    @Test
    public void deserializeNegateShortBoundary() {
        // given
        byte[] bytes = new byte[] {
            (byte) 0xd1,
            (byte) 128,
            (byte) 0
        };

        // when

        int actual = der.deserialize(bytes);

        //then
        Asserts.assertEquals(actual, Short.MIN_VALUE, false);
    }

    @Test
    public void deserializeNegateShortBoundaryOld() {
        // given
        byte[] bytes = new byte[] {
            (byte) 0xd2,
            (byte) 0xff,
            (byte) 0xff,
            (byte) 128,
            (byte) 0
        };

        // when

        int actual = der.deserialize(bytes);

        //then
        Asserts.assertEquals(actual, Short.MIN_VALUE, false);
    }

    @Test
    public void deserializeNegateIntBoundary() {
        // given
        byte[] bytes = new byte[] {
            (byte) 0xd2,
            (byte) 128,
            (byte) 0,
            (byte) 0,
            (byte) 0
        };

        // when

        int actual = der.deserialize(bytes);

        //then
        Asserts.assertEquals(actual, Integer.MIN_VALUE, false);
    }

    @Test
    public void deserializeNegateIntBoundaryOld() {
        // given
        byte[] bytes = new byte[] {
            (byte) 0xd3,
            (byte) 0xff,
            (byte) 0xff,
            (byte) 0xff,
            (byte) 0xff,
            (byte) 128,
            (byte) 0,
            (byte) 0,
            (byte) 0
        };

        // when

        long actual = der.deserialize(bytes);

        //then
        Asserts.assertEquals(actual, Integer.MIN_VALUE, false);
    }

    @Test
    public void deserializeNegateLongBoundary() {
        // given
        byte[] bytes = new byte[] {
            (byte) 0xd3,
            (byte) 128,
            (byte) 0,
            (byte) 0,
            (byte) 0,
            (byte) 0,
            (byte) 0,
            (byte) 0,
            (byte) 0
        };

        // when

        long actual = der.deserialize(bytes);

        //then
        Asserts.assertEquals(actual, Long.MIN_VALUE, false);
    }
}
