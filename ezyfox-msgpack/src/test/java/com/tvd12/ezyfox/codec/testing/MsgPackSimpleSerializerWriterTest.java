package com.tvd12.ezyfox.codec.testing;

import com.tvd12.ezyfox.codec.MsgPackArraySizeSerializer;
import com.tvd12.ezyfox.codec.MsgPackBinSizeSerializer;
import com.tvd12.ezyfox.codec.MsgPackDoubleSerializer;
import com.tvd12.ezyfox.codec.MsgPackFloatSerializer;
import com.tvd12.ezyfox.codec.MsgPackIntSerializer;
import com.tvd12.ezyfox.codec.MsgPackMapSizeSerializer;
import com.tvd12.ezyfox.codec.MsgPackSimpleDeserializer;
import com.tvd12.ezyfox.codec.MsgPackSimpleSerializer;
import com.tvd12.ezyfox.codec.MsgPackStringSizeSerializer;
import com.tvd12.ezyfox.entity.EzyArray;
import com.tvd12.ezyfox.entity.EzyObject;
import com.tvd12.ezyfox.exception.EzyCodecException;
import com.tvd12.ezyfox.factory.EzyEntityFactory;
import com.tvd12.ezyfox.io.EzyBytes;
import com.tvd12.test.assertion.Asserts;
import org.testng.annotations.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.assertEquals;

public class MsgPackSimpleSerializerWriterTest {

    private static final long[] INT_VALUES = {
        0, 1, 127, 128, 255, 256, 65535, 65536, 4294967295L, 4294967296L, Long.MAX_VALUE,
        -1, -32, -33, -128, -129, -32768, -32769, Integer.MIN_VALUE, Integer.MIN_VALUE - 1L,
        Long.MIN_VALUE
    };

    private static final int[] SIZES = {
        0, 1, 15, 16, 31, 32, 255, 256, 65535, 65536
    };

    private final MsgPackSimpleSerializer serializer = new MsgPackSimpleSerializer();
    private final MsgPackSimpleDeserializer deserializer = new MsgPackSimpleDeserializer();

    @Test
    public void intTest() {
        for (long value : INT_VALUES) {
            assertEquals(
                serializer.serialize(value),
                MsgPackIntSerializer.getInstance().serialize(value)
            );
            if (value >= Integer.MIN_VALUE && value <= Integer.MAX_VALUE) {
                assertEquals(
                    serializer.serialize((int) value),
                    MsgPackIntSerializer.getInstance().serialize(value)
                );
            }
            if (value >= Short.MIN_VALUE && value <= Short.MAX_VALUE) {
                assertEquals(
                    serializer.serialize((short) value),
                    MsgPackIntSerializer.getInstance().serialize(value)
                );
            }
            if (value >= Byte.MIN_VALUE && value <= Byte.MAX_VALUE) {
                assertEquals(
                    serializer.serialize((byte) value),
                    MsgPackIntSerializer.getInstance().serialize(value)
                );
            }
        }
        assertEquals(
            serializer.serialize((char) 200),
            MsgPackIntSerializer.getInstance().serialize((byte) 200)
        );
    }

    @Test
    public void floatAndDoubleTest() {
        for (double value : new double[]{0D, -1.5D, Double.MAX_VALUE, Double.NaN}) {
            assertEquals(
                serializer.serialize(value),
                MsgPackDoubleSerializer.getInstance().serialize(value)
            );
        }
        for (float value : new float[]{0F, -1.5F, Float.MAX_VALUE, Float.NaN}) {
            assertEquals(
                serializer.serialize(value),
                MsgPackFloatSerializer.getInstance().serialize(value)
            );
        }
    }

    @Test
    public void stringTest() {
        for (int size : SIZES) {
            String ascii = newString('a', size);
            assertEquals(serializer.serialize(ascii), expectedString(ascii));
            String unicode = newString('ệ', size);
            assertEquals(serializer.serialize(unicode), expectedString(unicode));
            Asserts.assertEquals(deserializer.deserialize(serializer.serialize(unicode)), unicode);
        }
        String surrogate = "a\uD800b";
        assertEquals(serializer.serialize(surrogate), expectedString(surrogate));
    }

    @Test
    public void binTest() {
        for (int size : SIZES) {
            byte[] bin = new byte[size];
            Arrays.fill(bin, (byte) 7);
            byte[] expected = EzyBytes.merge(new byte[][]{
                MsgPackBinSizeSerializer.getInstance().serialize(size),
                bin
            });
            assertEquals(serializer.serialize(bin), expected);
            char[] chars = new char[size];
            Arrays.fill(chars, (char) 7);
            assertEquals(serializer.serialize(chars), expected);
        }
    }

    @Test
    public void arrayAndMapSizeTest() {
        for (int size : SIZES) {
            List<Object> list = new ArrayList<>(Collections.nCopies(size, 1));
            assertEquals(
                serializer.serialize(list),
                EzyBytes.merge(new byte[][]{
                    MsgPackArraySizeSerializer.getInstance().serialize(size),
                    newFilledBytes(size, 1)
                })
            );
            Map<Object, Object> map = new LinkedHashMap<>();
            for (int i = 0; i < size; ++i) {
                map.put(i, null);
            }
            byte[] body = serializeEntries(map);
            assertEquals(
                serializer.serialize(map),
                EzyBytes.merge(new byte[][]{
                    MsgPackMapSizeSerializer.getInstance().serialize(size),
                    body
                })
            );
        }
    }

    @Test
    public void nestedRoundTripTest() {
        EzyObject root = EzyEntityFactory.newObject();
        EzyObject current = root;
        for (int i = 0; i < 50; ++i) {
            EzyObject child = EzyEntityFactory.newObject();
            current.put("name", "level " + i);
            current.put("unicode", "cấp " + i);
            current.put("number", i * 1000L);
            current.put("double", i + 0.5D);
            current.put("bin", new byte[]{1, 2, (byte) i});
            current.put("ints", new int[]{i, -i, i * 100000});
            current.put("strings", new String[]{"x", null, "y"});
            EzyArray array = EzyEntityFactory.newArray();
            array.add(i, "item", null, true);
            current.put("array", array);
            current.put("child", child);
            current = child;
        }

        byte[] bytes = serializer.serialize(root);
        EzyObject actual = deserializer.deserialize(bytes);

        EzyObject level = actual;
        for (int i = 0; i < 50; ++i) {
            Asserts.assertEquals((String) level.get("name"), "level " + i);
            Asserts.assertEquals((String) level.get("unicode"), "cấp " + i);
            Asserts.assertEquals(((Number) level.get("number")).longValue(), i * 1000L);
            Asserts.assertEquals(((byte[]) level.get("bin"))[2], (byte) i);
            level = (EzyObject) level.get("child");
        }
    }

    @Test
    public void sizeChangedWhileSerializingTest() {
        List<Object> list = new AbstractList<Object>() {
            @Override
            public Object get(int index) {
                return index;
            }

            @Override
            public int size() {
                return 3;
            }

            @Override
            public java.util.Iterator<Object> iterator() {
                return Collections.<Object>singletonList(1).iterator();
            }
        };

        Throwable e = Asserts.assertThrows(() -> serializer.serialize(list));

        Asserts.assertEqualsType(e, EzyCodecException.class);
    }

    @Test
    public void customParserTest() {
        MsgPackSimpleSerializer custom = new MsgPackSimpleSerializer() {
            @Override
            protected void addParsers(
                Map<Class<?>, com.tvd12.ezyfox.function.EzyParser<Object, byte[]>> parsers
            ) {
                super.addParsers(parsers);
                parsers.put(Custom.class, value -> serialize("custom"));
            }
        };
        List<Object> list = Arrays.asList(new Custom(), 1);

        byte[] actual = custom.serialize(list);

        assertEquals(actual, serializer.serialize(Arrays.asList("custom", 1)));
    }

    @Test
    public void noParserOverrideTest() {
        // given
        MsgPackSimpleSerializer custom = new MsgPackSimpleSerializer() {
            @Override
            protected byte[] parseWithNoParser(Object value) {
                return serialize("none");
            }
        };
        List<Object> list = Arrays.asList(new Custom(), 1);

        // when
        byte[] actual = custom.serialize(list);

        // then
        assertEquals(actual, serializer.serialize(Arrays.asList("none", 1)));
    }

    @Test
    public void outputTooLargeTest() throws Exception {
        // given
        Class<?> outputClass = Class.forName(MsgPackSimpleSerializer.class.getName() + "$Output");
        Constructor<?> constructor = outputClass.getDeclaredConstructor(int.class);
        constructor.setAccessible(true);
        Object output = constructor.newInstance(0);
        Field countField = outputClass.getDeclaredField("count");
        countField.setAccessible(true);
        Method ensureCapacity = outputClass.getDeclaredMethod("ensureCapacity", int.class);
        ensureCapacity.setAccessible(true);

        // when
        countField.setInt(output, Integer.MAX_VALUE - 8);
        Throwable overMax = Asserts.assertThrows(() -> ensureCapacity.invoke(output, 1));
        countField.setInt(output, Integer.MAX_VALUE);
        Throwable overflow = Asserts.assertThrows(() -> ensureCapacity.invoke(output, 1));

        // then
        Asserts.assertEqualsType(overMax, InvocationTargetException.class);
        Asserts.assertEqualsType(overMax.getCause(), EzyCodecException.class);
        Asserts.assertEqualsType(overflow, InvocationTargetException.class);
        Asserts.assertEqualsType(overflow.getCause(), EzyCodecException.class);
    }

    private byte[] serializeEntries(Map<Object, Object> map) {
        byte[][] arrays = new byte[map.size() * 2][];
        int index = 0;
        for (Map.Entry<Object, Object> e : map.entrySet()) {
            arrays[index++] = serializer.serialize(e.getKey());
            arrays[index++] = serializer.serialize(e.getValue());
        }
        return EzyBytes.merge(arrays);
    }

    private static byte[] expectedString(String value) {
        byte[] bytes = value.getBytes(StandardCharsets.UTF_8);
        return EzyBytes.merge(new byte[][]{
            MsgPackStringSizeSerializer.getInstance().serialize(bytes.length),
            bytes
        });
    }

    private static String newString(char ch, int size) {
        char[] chars = new char[size];
        Arrays.fill(chars, ch);
        return new String(chars);
    }

    private static byte[] newFilledBytes(int size, int value) {
        byte[] bytes = new byte[size];
        Arrays.fill(bytes, (byte) value);
        return bytes;
    }

    private static class Custom {}
}
