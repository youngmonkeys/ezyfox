package com.tvd12.ezyfox.codec.testing;

import com.tvd12.ezyfox.codec.MsgPackSimpleSerializer;
import com.tvd12.ezyfox.entity.EzyArray;
import com.tvd12.ezyfox.entity.EzyObject;
import com.tvd12.ezyfox.exception.EzyCodecException;
import com.tvd12.ezyfox.factory.EzyEntityFactory;
import com.tvd12.ezyfox.function.EzyParser;
import com.tvd12.test.assertion.Asserts;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

public class MsgPackSimpleSerializerParseMethodsTest {

    @Test
    public void primitiveArrayParseMethodsTest() {
        new ExSerializer().verifyPrimitiveArrays();
    }

    @Test
    public void wrapperArrayParseMethodsTest() {
        new ExSerializer().verifyWrapperArrays();
    }

    @Test
    public void singleValueParseMethodsTest() {
        new ExSerializer().verifySingleValues();
    }

    @Test
    public void collectionParseMethodsTest() {
        new ExSerializer().verifyCollections();
    }

    @Test
    public void sizeParseMethodsTest() {
        new ExSerializer().verifySizes();
    }

    @Test
    public void writerParserParseTest() {
        new ExSerializer().verifyWriterParser();
    }

    @Test
    public void customParserTest() {
        // given
        ExSerializer serializer = new ExSerializer();
        CustomValue value = new CustomValue();

        // when
        byte[] actual = serializer.serialize(value);
        byte[] actualInArray = serializer.serialize(Arrays.asList(value, 1));

        // then
        Asserts.assertEquals(actual, serializer.serialize("custom"));
        Asserts.assertEquals(actualInArray, serializer.serialize(Arrays.asList("custom", 1)));
    }

    @Test
    public void noParserTest() {
        // given
        ExSerializer serializer = new ExSerializer();
        List<Object> list = Arrays.asList(1, new Object());

        // when
        Throwable e = Asserts.assertThrows(() -> serializer.serialize(list));

        // then
        Asserts.assertEqualsType(e, EzyCodecException.class);
    }

    private static class CustomValue {}

    private static class ExSerializer extends MsgPackSimpleSerializer {

        @Override
        protected void addParsers(Map<Class<?>, EzyParser<Object, byte[]>> parsers) {
            super.addParsers(parsers);
            parsers.put(CustomValue.class, value -> serialize("custom"));
        }

        private void verifyPrimitiveArrays() {
            boolean[] booleans = new boolean[]{true, false};
            char[] chars = new char[]{'a', 'b'};
            double[] doubles = new double[]{1.1D, 2.2D};
            float[] floats = new float[]{1.1F, 2.2F};
            int[] ints = new int[]{1, 300, -70000};
            long[] longs = new long[]{1L, Long.MAX_VALUE};
            short[] shorts = new short[]{1, -200};
            String[] strings = new String[]{"a", "việt"};

            Asserts.assertEquals(parsePrimitiveBooleans(booleans), serialize(booleans));
            Asserts.assertEquals(parseBooleans(booleans), serialize(booleans));
            Asserts.assertEquals(parsePrimitiveChars(chars), serialize(chars));
            Asserts.assertEquals(parseChars(chars), serialize(chars));
            Asserts.assertEquals(parsePrimitiveDoubles(doubles), serialize(doubles));
            Asserts.assertEquals(parseDoubles(doubles), serialize(doubles));
            Asserts.assertEquals(parsePrimitiveFloats(floats), serialize(floats));
            Asserts.assertEquals(parseFloats(floats), serialize(floats));
            Asserts.assertEquals(parsePrimitiveInts(ints), serialize(ints));
            Asserts.assertEquals(parseInts(ints), serialize(ints));
            Asserts.assertEquals(parsePrimitiveLongs(longs), serialize(longs));
            Asserts.assertEquals(parseLongs(longs), serialize(longs));
            Asserts.assertEquals(parsePrimitiveShorts(shorts), serialize(shorts));
            Asserts.assertEquals(parseShorts(shorts), serialize(shorts));
            Asserts.assertEquals(parseStrings((Object) strings), serialize(strings));
            Asserts.assertEquals(parseStrings(strings), serialize(strings));
        }

        private void verifyWrapperArrays() {
            Boolean[] booleans = new Boolean[]{true, false};
            Byte[] bytes = new Byte[]{1, -1};
            Character[] chars = new Character[]{'a', 'b'};
            Double[] doubles = new Double[]{1.1D, 2.2D};
            Float[] floats = new Float[]{1.1F, 2.2F};
            Integer[] ints = new Integer[]{1, 300, -70000};
            Long[] longs = new Long[]{1L, Long.MIN_VALUE};
            Short[] shorts = new Short[]{1, -200};

            Asserts.assertEquals(parseWrapperBooleans(booleans), serialize(booleans));
            Asserts.assertEquals(parseBooleans(booleans), serialize(booleans));
            Asserts.assertEquals(parseWrapperBytes(bytes), serialize(bytes));
            Asserts.assertEquals(parseBytes(bytes), serialize(bytes));
            Asserts.assertEquals(parseWrapperChars(chars), serialize(chars));
            Asserts.assertEquals(parseChars(chars), serialize(chars));
            Asserts.assertEquals(parseWrapperDoubles(doubles), serialize(doubles));
            Asserts.assertEquals(parseDoubles(doubles), serialize(doubles));
            Asserts.assertEquals(parseWrapperFloats(floats), serialize(floats));
            Asserts.assertEquals(parseFloats(floats), serialize(floats));
            Asserts.assertEquals(parseWrapperInts(ints), serialize(ints));
            Asserts.assertEquals(parseInts(ints), serialize(ints));
            Asserts.assertEquals(parseWrapperLongs(longs), serialize(longs));
            Asserts.assertEquals(parseLongs(longs), serialize(longs));
            Asserts.assertEquals(parseWrapperShorts(shorts), serialize(shorts));
            Asserts.assertEquals(parseShorts(shorts), serialize(shorts));
        }

        private void verifySingleValues() {
            Asserts.assertEquals(parseBoolean((Object) Boolean.TRUE), serialize(true));
            Asserts.assertEquals(parseBoolean(Boolean.TRUE), serialize(true));
            Asserts.assertEquals(parseBoolean(Boolean.FALSE), serialize(false));
            Asserts.assertEquals(parseTrue(), new byte[]{(byte) 0xc3});
            Asserts.assertEquals(parseFalse(), new byte[]{(byte) 0xc2});
            Asserts.assertEquals(parseByte((Object) (byte) -100), serialize((byte) -100));
            Asserts.assertEquals(parseByte(Byte.valueOf((byte) 100)), serialize((byte) 100));
            Asserts.assertEquals(parseChar((Object) 'a'), serialize('a'));
            Asserts.assertEquals(parseChar(Character.valueOf('b')), serialize('b'));
            Asserts.assertEquals(parseDouble((Object) 1.5D), serialize(1.5D));
            Asserts.assertEquals(parseDouble(Double.valueOf(2.5D)), serialize(2.5D));
            Asserts.assertEquals(parseFloat((Object) 1.5F), serialize(1.5F));
            Asserts.assertEquals(parseFloat(Float.valueOf(2.5F)), serialize(2.5F));
            Asserts.assertEquals(parseInt(70000), serialize(70000));
            Asserts.assertEquals(parseInt(Long.MAX_VALUE), serialize(Long.MAX_VALUE));
            Asserts.assertEquals(parseShort((Object) (short) -300), serialize((short) -300));
            Asserts.assertEquals(parseShort(Short.valueOf((short) 300)), serialize((short) 300));
            Asserts.assertEquals(parseString((Object) "việt"), serialize("việt"));
            Asserts.assertEquals(parseString("abc"), serialize("abc"));
            Asserts.assertEquals(parseNil(), serialize(null));
            Asserts.assertEquals(parseBigDecimalToString(new BigDecimal("1E+3")), serialize("1000"));
            Asserts.assertEquals(parseValueToString(BigInteger.TEN), serialize("10"));
            UUID uuid = UUID.randomUUID();
            Asserts.assertEquals(parseValueToString(uuid), serialize(uuid));
        }

        @SuppressWarnings({"unchecked", "rawtypes"})
        private void verifyCollections() {
            Map<Object, Object> map = new LinkedHashMap<>();
            map.put("a", 1);
            map.put("b", Arrays.asList(1, 2));
            EzyObject object = EzyEntityFactory.newObjectBuilder()
                .append("a", 1)
                .append("b", "c")
                .build();
            EzyArray array = EzyEntityFactory.newArrayBuilder()
                .append(1, "a", true)
                .build();
            List<Object> list = Arrays.asList(1, "a", null);
            byte[] bin = new byte[]{1, 2, 3};

            Asserts.assertEquals(parseMap((Object) map), serialize(map));
            Asserts.assertEquals(parseMap(map), serialize(map));
            Asserts.assertEquals(parseObject(object), serialize(object));
            Asserts.assertEquals(
                parseEntries((Set<Entry<Object, Object>>) (Set) object.entrySet()),
                serialize(object)
            );
            Asserts.assertEquals(parseArray((Object) array), serialize(array));
            Asserts.assertEquals(parseArray(array), serialize(array));
            Asserts.assertEquals(parseCollection((Object) list), serialize(list));
            Asserts.assertEquals(parseCollection(list), serialize(list));
            Asserts.assertEquals(parseIterable(list, list.size()), serialize(list));
            Asserts.assertEquals(parseArray(list.iterator(), list.size()), serialize(list));
            Asserts.assertEquals(parseBin((Object) bin), serialize(bin));
            Asserts.assertEquals(parseBin(bin), serialize(bin));
        }

        private void verifySizes() {
            Asserts.assertEquals(parseArraySize(3), new byte[]{(byte) 0x93});
            Asserts.assertEquals(parseMapSize(2), new byte[]{(byte) 0x82});
            Asserts.assertEquals(parseStringSize(3), new byte[]{(byte) 0xa3});
            Asserts.assertEquals(parseBinSize(3), new byte[]{(byte) 0xc4, 3});
        }

        private void verifyWriterParser() {
            Asserts.assertEquals(getParser(String.class).parse("abc"), serialize("abc"));
            Asserts.assertEquals(getParser(Integer.class).parse(300), serialize(300));
        }
    }
}
