package com.tvd12.ezyfox.codec;

import com.tvd12.ezyfox.entity.EzyArray;
import com.tvd12.ezyfox.entity.EzyArrayList;
import com.tvd12.ezyfox.entity.EzyHashMap;
import com.tvd12.ezyfox.entity.EzyObject;
import com.tvd12.ezyfox.function.EzyParser;
import com.tvd12.ezyfox.io.EzyBytes;
import com.tvd12.ezyfox.io.EzyCastToByte;
import com.tvd12.ezyfox.io.EzyDataConverter;
import com.tvd12.ezyfox.io.EzyStrings;
import com.tvd12.ezyfox.util.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.Map.Entry;

@SuppressWarnings({"MethodCount", "OverloadMethodsDeclarationOrder"})
public class MsgPackSimpleSerializer
    extends EzyAbstractToBytesSerializer
    implements EzyCastToByte {

    protected final MsgPackIntSerializer intSerializer;
    protected final MsgPackFloatSerializer floatSerializer;
    protected final MsgPackDoubleSerializer doubleSerializer;
    protected final MsgPackBinSizeSerializer binSizeSerializer;
    protected final MsgPackMapSizeSerializer mapSizeSerializer;
    protected final MsgPackArraySizeSerializer arraySizeSerializer;
    protected final MsgPackStringSizeSerializer stringSizeSerializer;

    public MsgPackSimpleSerializer() {
        this.intSerializer = MsgPackIntSerializer.getInstance();
        this.floatSerializer = MsgPackFloatSerializer.getInstance();
        this.doubleSerializer = MsgPackDoubleSerializer.getInstance();
        this.binSizeSerializer = MsgPackBinSizeSerializer.getInstance();
        this.mapSizeSerializer = MsgPackMapSizeSerializer.getInstance();
        this.arraySizeSerializer = MsgPackArraySizeSerializer.getInstance();
        this.stringSizeSerializer = MsgPackStringSizeSerializer.getInstance();
    }

    @Override
    protected void addParsers(Map<Class<?>, EzyParser<Object, byte[]>> parsers) {
        parsers.put(Boolean.class, this::parseBoolean);
        parsers.put(Byte.class, this::parseByte);
        parsers.put(Character.class, this::parseChar);
        parsers.put(Double.class, this::parseDouble);
        parsers.put(Float.class, this::parseFloat);
        parsers.put(Integer.class, this::parseInt);
        parsers.put(Long.class, this::parseInt);
        parsers.put(Short.class, this::parseShort);
        parsers.put(String.class, this::parseString);

        parsers.put(boolean[].class, this::parsePrimitiveBooleans);
        parsers.put(byte[].class, this::parseBin);
        parsers.put(char[].class, this::parsePrimitiveChars);
        parsers.put(double[].class, this::parsePrimitiveDoubles);
        parsers.put(float[].class, this::parsePrimitiveFloats);
        parsers.put(int[].class, this::parsePrimitiveInts);
        parsers.put(long[].class, this::parsePrimitiveLongs);
        parsers.put(short[].class, this::parsePrimitiveShorts);
        parsers.put(String[].class, this::parseStrings);

        parsers.put(Byte[].class, this::parseWrapperBytes);
        parsers.put(Boolean[].class, this::parseWrapperBooleans);
        parsers.put(Character[].class, this::parseWrapperChars);
        parsers.put(Double[].class, this::parseWrapperDoubles);
        parsers.put(Float[].class, this::parseWrapperFloats);
        parsers.put(Integer[].class, this::parseWrapperInts);
        parsers.put(Long[].class, this::parseWrapperLongs);
        parsers.put(Short[].class, this::parseWrapperShorts);

        parsers.put(Map.class, this::parseMap);
        parsers.put(AbstractMap.class, this::parseMap);
        parsers.put(HashMap.class, this::parseMap);
        parsers.put(EzyObject.class, this::parseObject);
        parsers.put(EzyHashMap.class, this::parseObject);
        parsers.put(EzyArray.class, this::parseArray);
        parsers.put(EzyArrayList.class, this::parseArray);
        parsers.put(Collection.class, this::parseCollection);
        parsers.put(AbstractCollection.class, this::parseCollection);
        parsers.put(Set.class, this::parseCollection);
        parsers.put(AbstractSet.class, this::parseCollection);
        parsers.put(List.class, this::parseCollection);
        parsers.put(AbstractList.class, this::parseCollection);
        parsers.put(HashSet.class, this::parseCollection);
        parsers.put(ArrayList.class, this::parseCollection);

        parsers.put(BigInteger.class, this::parseValueToString);
        parsers.put(BigDecimal.class, this::parseValueToString);
        parsers.put(UUID.class, this::parseValueToString);
    }

    // ============= primitive array ==============
    protected byte[] parsePrimitiveBooleans(Object array) {
        return parseBooleans((boolean[]) array);
    }

    protected byte[] parseBooleans(boolean[] array) {
        return parseArray(EzyBoolsIterator.wrap(array), array.length);
    }

    protected byte[] parsePrimitiveChars(Object array) {
        return parseChars((char[]) array);
    }

    protected byte[] parseChars(char[] array) {
        return parseBin(EzyDataConverter.charArrayToByteArray(array));
    }

    protected byte[] parsePrimitiveDoubles(Object array) {
        return parseDoubles((double[]) array);
    }

    protected byte[] parseDoubles(double[] array) {
        return parseArray(EzyDoublesIterator.wrap(array), array.length);
    }

    protected byte[] parsePrimitiveFloats(Object array) {
        return parseFloats((float[]) array);
    }

    protected byte[] parseFloats(float[] array) {
        return parseArray(EzyFloatsIterator.wrap(array), array.length);
    }

    protected byte[] parsePrimitiveInts(Object array) {
        return parseInts((int[]) array);
    }

    protected byte[] parseInts(int[] array) {
        return parseArray(EzyIntsIterator.wrap(array), array.length);
    }

    protected byte[] parsePrimitiveLongs(Object array) {
        return parseLongs((long[]) array);
    }

    protected byte[] parseLongs(long[] array) {
        return parseArray(EzyLongsIterator.wrap(array), array.length);
    }

    protected byte[] parsePrimitiveShorts(Object array) {
        return parseShorts((short[]) array);
    }

    protected byte[] parseShorts(short[] array) {
        return parseArray(EzyShortsIterator.wrap(array), array.length);
    }

    protected byte[] parseStrings(Object array) {
        return parseStrings((String[]) array);
    }

    protected byte[] parseStrings(String[] array) {
        return parseArray(EzyStringsIterator.wrap(array), array.length);
    }

    // ============== wrapper array ===============
    protected byte[] parseWrapperBooleans(Object array) {
        return parseBooleans((Boolean[]) array);
    }

    protected byte[] parseBooleans(Boolean[] array) {
        return parseArray(EzyWrapperIterator.wrap(array), array.length);
    }

    protected byte[] parseWrapperBytes(Object array) {
        return parseBytes((Byte[]) array);
    }

    protected byte[] parseBytes(Byte[] array) {
        return parseBin(EzyDataConverter.toPrimitiveByteArray(array));
    }

    protected byte[] parseWrapperChars(Object array) {
        return parseChars((Character[]) array);
    }

    protected byte[] parseChars(Character[] array) {
        return parseBin(EzyDataConverter.charWrapperArrayToPrimitiveByteArray(array));
    }

    protected byte[] parseWrapperDoubles(Object array) {
        return parseDoubles((Double[]) array);
    }

    protected byte[] parseDoubles(Double[] array) {
        return parseArray(EzyWrapperIterator.wrap(array), array.length);
    }

    protected byte[] parseWrapperFloats(Object array) {
        return parseFloats((Float[]) array);
    }

    protected byte[] parseFloats(Float[] array) {
        return parseArray(EzyWrapperIterator.wrap(array), array.length);
    }

    protected byte[] parseWrapperInts(Object array) {
        return parseInts((Integer[]) array);
    }

    protected byte[] parseInts(Integer[] array) {
        return parseArray(EzyWrapperIterator.wrap(array), array.length);
    }

    protected byte[] parseWrapperLongs(Object array) {
        return parseLongs((Long[]) array);
    }

    protected byte[] parseLongs(Long[] array) {
        return parseArray(EzyWrapperIterator.wrap(array), array.length);
    }

    protected byte[] parseWrapperShorts(Object array) {
        return parseShorts((Short[]) array);
    }

    protected byte[] parseShorts(Short[] array) {
        return parseArray(EzyWrapperIterator.wrap(array), array.length);
    }

    // ============= single value ==============
    protected byte[] parseBoolean(Object value) {
        return parseBoolean((Boolean) value);
    }

    protected byte[] parseBoolean(Boolean value) {
        return value ? parseTrue() : parseFalse();
    }

    protected byte[] parseFalse() {
        return new byte[]{cast(0xc2)};
    }

    protected byte[] parseTrue() {
        return new byte[]{cast(0xc3)};
    }

    protected byte[] parseByte(Object value) {
        return parseByte((Byte) value);
    }

    protected byte[] parseByte(Byte value) {
        return parseInt(value.intValue());
    }

    protected byte[] parseChar(Object value) {
        return parseChar((Character) value);
    }

    protected byte[] parseChar(Character value) {
        return parseByte((byte) value.charValue());
    }

    protected byte[] parseDouble(Object value) {
        return parseDouble((Double) value);
    }

    protected byte[] parseDouble(Double value) {
        return doubleSerializer.serialize(value);
    }

    protected byte[] parseFloat(Object value) {
        return parseFloat((Float) value);
    }

    protected byte[] parseFloat(Float value) {
        return floatSerializer.serialize(value);
    }

    protected byte[] parseInt(Object value) {
        return intSerializer.serialize(((Number) value).longValue());
    }

    protected byte[] parseShort(Object value) {
        return parseShort((Short) value);
    }

    protected byte[] parseShort(Short value) {
        return parseInt(value.intValue());
    }

    protected byte[] parseString(Object string) {
        return parseString((String) string);
    }

    // ============ collection ============
    @SuppressWarnings({"rawtypes"})
    protected byte[] parseMap(Object map) {
        return parseMap((Map) map);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    protected byte[] parseMap(Map map) {
        return parseEntries(map.entrySet());
    }

    protected byte[] parseObject(Object obj) {
        return parseEntries(((EzyObject) obj).entrySet());
    }

    @SuppressWarnings({"rawtypes"})
    protected byte[] parseCollection(Object coll) {
        return parseCollection((Collection) coll);
    }

    protected byte[] parseArray(Object array) {
        return parseArray((EzyArray) array);
    }

    @SuppressWarnings({"rawtypes"})
    protected byte[] parseCollection(Collection coll) {
        return parseIterable(coll, coll.size());
    }

    protected byte[] parseArray(EzyArray array) {
        return parseArray(array.iterator(), array.size());
    }

    protected byte[] parseNil() {
        return new byte[]{cast(0xc0)};
    }

    protected byte[] parseBin(Object bin) {
        return parseBin((byte[]) bin);
    }

    protected byte[] parseBin(byte[] bin) {
        byte[][] byteArrays = new byte[2][];
        byteArrays[0] = parseBinSize(bin.length);
        byteArrays[1] = bin;
        return EzyBytes.merge(byteArrays);
    }

    protected byte[] parseBinSize(int size) {
        return binSizeSerializer.serialize(size);
    }

    protected byte[] parseString(String string) {
        byte[][] byteArrays = new byte[2][];
        byteArrays[1] = EzyStrings.getUtfBytes(string);
        byteArrays[0] = parseStringSize(byteArrays[1].length);
        return EzyBytes.merge(byteArrays);
    }

    protected byte[] parseStringSize(int size) {
        return stringSizeSerializer.serialize(size);
    }

    @SuppressWarnings("rawtypes")
    protected byte[] parseIterable(Iterable iterable, int size) {
        return parseArray(iterable.iterator(), size);
    }

    @SuppressWarnings("rawtypes")
    protected byte[] parseArray(Iterator iterator, int size) {
        int index = 1;
        byte[][] byteArrays = new byte[size + 1][];
        byteArrays[0] = parseArraySize(size);
        while (iterator.hasNext()) {
            byteArrays[index++] = serialize(iterator.next());
        }
        return EzyBytes.merge(byteArrays);
    }

    protected byte[] parseArraySize(int size) {
        return arraySizeSerializer.serialize(size);
    }

    protected byte[] parseEntries(Set<Entry<Object, Object>> entries) {
        int index = 1;
        int size = entries.size();
        byte[][] byteArrays = new byte[size * 2 + 1][];
        byteArrays[0] = parseMapSize(size);
        for (Entry<Object, Object> e : entries) {
            byteArrays[index++] = serialize(e.getKey());
            byteArrays[index++] = serialize(e.getValue());
        }
        return EzyBytes.merge(byteArrays);
    }

    protected byte[] parseMapSize(int size) {
        return mapSizeSerializer.serialize(size);
    }

    protected byte[] parseValueToString(Object value) {
        return parseString(value.toString());
    }
}
