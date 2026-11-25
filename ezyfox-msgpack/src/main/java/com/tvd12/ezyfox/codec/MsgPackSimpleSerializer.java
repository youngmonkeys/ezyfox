package com.tvd12.ezyfox.codec;

import com.tvd12.ezyfox.entity.EzyArray;
import com.tvd12.ezyfox.entity.EzyArrayList;
import com.tvd12.ezyfox.entity.EzyHashMap;
import com.tvd12.ezyfox.entity.EzyObject;
import com.tvd12.ezyfox.exception.EzyCodecException;
import com.tvd12.ezyfox.function.EzyParser;
import com.tvd12.ezyfox.io.EzyCastToByte;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.Map.Entry;
import java.util.function.Consumer;

import static com.tvd12.ezyfox.codec.MsgPackConstant.*;

@SuppressWarnings({"MethodCount", "OverloadMethodsDeclarationOrder"})
public class MsgPackSimpleSerializer
    extends EzyAbstractToBytesSerializer
    implements EzyCastToByte {

    private static final int INITIAL_CAPACITY = 256;

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
    public byte[] serialize(Object value) {
        Output out = new Output(INITIAL_CAPACITY);
        writeValue(value, out);
        return out.toByteArray();
    }

    @Override
    protected void addParsers(Map<Class<?>, EzyParser<Object, byte[]>> parsers) {
        parsers.put(Boolean.class, writer((v, o) -> writeBoolean((Boolean) v, o)));
        parsers.put(Byte.class, writer((v, o) -> writeInt(((Byte) v).intValue(), o)));
        parsers.put(Character.class, writer((v, o) -> writeChar((Character) v, o)));
        parsers.put(Double.class, writer((v, o) -> writeDouble((Double) v, o)));
        parsers.put(Float.class, writer((v, o) -> writeFloat((Float) v, o)));
        parsers.put(Integer.class, writer((v, o) -> writeInt(((Number) v).longValue(), o)));
        parsers.put(Long.class, writer((v, o) -> writeInt(((Number) v).longValue(), o)));
        parsers.put(Short.class, writer((v, o) -> writeInt(((Short) v).intValue(), o)));
        parsers.put(String.class, writer((v, o) -> writeString((String) v, o)));

        parsers.put(boolean[].class, writer((v, o) -> writeBooleans((boolean[]) v, o)));
        parsers.put(byte[].class, writer((v, o) -> writeBin((byte[]) v, o)));
        parsers.put(char[].class, writer((v, o) -> writeChars((char[]) v, o)));
        parsers.put(double[].class, writer((v, o) -> writeDoubles((double[]) v, o)));
        parsers.put(float[].class, writer((v, o) -> writeFloats((float[]) v, o)));
        parsers.put(int[].class, writer((v, o) -> writeInts((int[]) v, o)));
        parsers.put(long[].class, writer((v, o) -> writeLongs((long[]) v, o)));
        parsers.put(short[].class, writer((v, o) -> writeShorts((short[]) v, o)));
        parsers.put(String[].class, writer((v, o) -> writeValues((Object[]) v, o)));

        parsers.put(Byte[].class, writer((v, o) -> writeBytes((Byte[]) v, o)));
        parsers.put(Boolean[].class, writer((v, o) -> writeValues((Object[]) v, o)));
        parsers.put(Character[].class, writer((v, o) -> writeChars((Character[]) v, o)));
        parsers.put(Double[].class, writer((v, o) -> writeValues((Object[]) v, o)));
        parsers.put(Float[].class, writer((v, o) -> writeValues((Object[]) v, o)));
        parsers.put(Integer[].class, writer((v, o) -> writeValues((Object[]) v, o)));
        parsers.put(Long[].class, writer((v, o) -> writeValues((Object[]) v, o)));
        parsers.put(Short[].class, writer((v, o) -> writeValues((Object[]) v, o)));

        EzyParser<Object, byte[]> mapWriter = writer((v, o) -> writeMap((Map<?, ?>) v, o));
        EzyParser<Object, byte[]> objectWriter = writer((v, o) -> writeObject((EzyObject) v, o));
        EzyParser<Object, byte[]> arrayWriter = writer((v, o) -> writeArray((EzyArray) v, o));
        EzyParser<Object, byte[]> collectionWriter =
            writer((v, o) -> writeCollection((Collection<?>) v, o));
        parsers.put(Map.class, mapWriter);
        parsers.put(AbstractMap.class, mapWriter);
        parsers.put(HashMap.class, mapWriter);
        parsers.put(EzyObject.class, objectWriter);
        parsers.put(EzyHashMap.class, objectWriter);
        parsers.put(EzyArray.class, arrayWriter);
        parsers.put(EzyArrayList.class, arrayWriter);
        parsers.put(Collection.class, collectionWriter);
        parsers.put(AbstractCollection.class, collectionWriter);
        parsers.put(Set.class, collectionWriter);
        parsers.put(AbstractSet.class, collectionWriter);
        parsers.put(List.class, collectionWriter);
        parsers.put(AbstractList.class, collectionWriter);
        parsers.put(HashSet.class, collectionWriter);
        parsers.put(ArrayList.class, collectionWriter);

        EzyParser<Object, byte[]> toStringWriter =
            writer((v, o) -> writeString(v.toString(), o));
        parsers.put(BigInteger.class, toStringWriter);
        parsers.put(BigDecimal.class, writer((v, o) -> writeString(((BigDecimal) v).toPlainString(), o)));
        parsers.put(UUID.class, toStringWriter);
    }

    // ============= primitive array ==============
    protected byte[] parsePrimitiveBooleans(Object array) {
        return parseBooleans((boolean[]) array);
    }

    protected byte[] parseBooleans(boolean[] array) {
        return toBytes(out -> writeBooleans(array, out));
    }

    protected byte[] parsePrimitiveChars(Object array) {
        return parseChars((char[]) array);
    }

    protected byte[] parseChars(char[] array) {
        return toBytes(out -> writeChars(array, out));
    }

    protected byte[] parsePrimitiveDoubles(Object array) {
        return parseDoubles((double[]) array);
    }

    protected byte[] parseDoubles(double[] array) {
        return toBytes(out -> writeDoubles(array, out));
    }

    protected byte[] parsePrimitiveFloats(Object array) {
        return parseFloats((float[]) array);
    }

    protected byte[] parseFloats(float[] array) {
        return toBytes(out -> writeFloats(array, out));
    }

    protected byte[] parsePrimitiveInts(Object array) {
        return parseInts((int[]) array);
    }

    protected byte[] parseInts(int[] array) {
        return toBytes(out -> writeInts(array, out));
    }

    protected byte[] parsePrimitiveLongs(Object array) {
        return parseLongs((long[]) array);
    }

    protected byte[] parseLongs(long[] array) {
        return toBytes(out -> writeLongs(array, out));
    }

    protected byte[] parsePrimitiveShorts(Object array) {
        return parseShorts((short[]) array);
    }

    protected byte[] parseShorts(short[] array) {
        return toBytes(out -> writeShorts(array, out));
    }

    protected byte[] parseStrings(Object array) {
        return parseStrings((String[]) array);
    }

    protected byte[] parseStrings(String[] array) {
        return toBytes(out -> writeValues(array, out));
    }

    // ============== wrapper array ===============
    protected byte[] parseWrapperBooleans(Object array) {
        return parseBooleans((Boolean[]) array);
    }

    protected byte[] parseBooleans(Boolean[] array) {
        return toBytes(out -> writeValues(array, out));
    }

    protected byte[] parseWrapperBytes(Object array) {
        return parseBytes((Byte[]) array);
    }

    protected byte[] parseBytes(Byte[] array) {
        return toBytes(out -> writeBytes(array, out));
    }

    protected byte[] parseWrapperChars(Object array) {
        return parseChars((Character[]) array);
    }

    protected byte[] parseChars(Character[] array) {
        return toBytes(out -> writeChars(array, out));
    }

    protected byte[] parseWrapperDoubles(Object array) {
        return parseDoubles((Double[]) array);
    }

    protected byte[] parseDoubles(Double[] array) {
        return toBytes(out -> writeValues(array, out));
    }

    protected byte[] parseWrapperFloats(Object array) {
        return parseFloats((Float[]) array);
    }

    protected byte[] parseFloats(Float[] array) {
        return toBytes(out -> writeValues(array, out));
    }

    protected byte[] parseWrapperInts(Object array) {
        return parseInts((Integer[]) array);
    }

    protected byte[] parseInts(Integer[] array) {
        return toBytes(out -> writeValues(array, out));
    }

    protected byte[] parseWrapperLongs(Object array) {
        return parseLongs((Long[]) array);
    }

    protected byte[] parseLongs(Long[] array) {
        return toBytes(out -> writeValues(array, out));
    }

    protected byte[] parseWrapperShorts(Object array) {
        return parseShorts((Short[]) array);
    }

    protected byte[] parseShorts(Short[] array) {
        return toBytes(out -> writeValues(array, out));
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

    @SuppressWarnings({"rawtypes"})
    protected byte[] parseMap(Map map) {
        return toBytes(out -> writeMap(map, out));
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
        return toBytes(out -> writeBin(bin, out));
    }

    protected byte[] parseBinSize(int size) {
        return binSizeSerializer.serialize(size);
    }

    protected byte[] parseString(String string) {
        return toBytes(out -> writeString(string, out));
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
        return toBytes(out -> writeIterator(iterator, size, out));
    }

    protected byte[] parseArraySize(int size) {
        return arraySizeSerializer.serialize(size);
    }

    protected byte[] parseEntries(Set<Entry<Object, Object>> entries) {
        return toBytes(out -> writeEntries(entries, entries.size(), out));
    }

    protected byte[] parseMapSize(int size) {
        return mapSizeSerializer.serialize(size);
    }

    protected byte[] parseBigDecimalToString(Object value) {
        return parseString(((BigDecimal) value).toPlainString());
    }

    protected byte[] parseValueToString(Object value) {
        return parseString(value.toString());
    }

    // ============ writer ============
    private EzyParser<Object, byte[]> writer(ValueWriter writer) {
        return new WriterParser(writer);
    }

    private byte[] toBytes(Consumer<Output> writer) {
        Output out = new Output(INITIAL_CAPACITY);
        writer.accept(out);
        return out.toByteArray();
    }

    private void writeValue(Object value, Output out) {
        if (value == null) {
            out.write(0xc0);
            return;
        }
        EzyParser<Object, byte[]> parser = getParser(value.getClass());
        if (parser instanceof WriterParser) {
            ((WriterParser) parser).writer.write(value, out);
        } else if (parser != null) {
            out.write(parseWithParser(parser, value));
        } else {
            out.write(parseWithNoParser(value));
        }
    }

    private void writeBoolean(boolean value, Output out) {
        out.write(value ? 0xc3 : 0xc2);
    }

    private void writeChar(char value, Output out) {
        writeInt((byte) value, out);
    }

    private void writeInt(long value, Output out) {
        if (value >= 0) {
            if (value <= MAX_POSITIVE_FIXINT) {
                out.write((int) value);
            } else if (value <= MAX_UINT8) {
                out.write(0xcc);
                out.write((int) value);
            } else if (value <= MAX_UINT16) {
                out.write(0xcd);
                out.writeShort((int) value);
            } else if (value <= MAX_UINT32) {
                out.write(0xce);
                out.writeInt((int) value);
            } else {
                out.write(0xcf);
                out.writeLong(value);
            }
        } else {
            if (value >= MIN_NEGATIVE_FIXINT) {
                out.write((int) value);
            } else if (value >= MIN_INT8) {
                out.write(0xd0);
                out.write((int) value);
            } else if (value >= MIN_INT16) {
                out.write(0xd1);
                out.writeShort((int) value);
            } else if (value >= MIN_INT32) {
                out.write(0xd2);
                out.writeInt((int) value);
            } else {
                out.write(0xd3);
                out.writeLong(value);
            }
        }
    }

    private void writeFloat(float value, Output out) {
        out.write(0xca);
        out.writeInt(Float.floatToIntBits(value));
    }

    private void writeDouble(double value, Output out) {
        out.write(0xcb);
        out.writeLong(Double.doubleToLongBits(value));
    }

    private void writeString(String value, Output out) {
        int length = value.length();
        if (isAscii(value, length)) {
            writeStringSize(length, out);
            out.writeAscii(value, length);
        } else {
            byte[] bytes = value.getBytes(StandardCharsets.UTF_8);
            writeStringSize(bytes.length, out);
            out.write(bytes);
        }
    }

    private static boolean isAscii(String value, int length) {
        for (int i = 0; i < length; ++i) {
            if (value.charAt(i) >= 0x80) {
                return false;
            }
        }
        return true;
    }

    private void writeStringSize(int size, Output out) {
        if (size <= MAX_FIXSTR_SIZE) {
            out.write(0xa0 | size);
        } else if (size <= MAX_STR8_SIZE) {
            out.write(0xd9);
            out.write(size);
        } else if (size <= MAX_STR16_SIZE) {
            out.write(0xda);
            out.writeShort(size);
        } else {
            out.write(0xdb);
            out.writeInt(size);
        }
    }

    private void writeBinSize(int size, Output out) {
        if (size <= MAX_BIN8_SIZE) {
            out.write(0xc4);
            out.write(size);
        } else if (size <= MAX_BIN16_SIZE) {
            out.write(0xc5);
            out.writeShort(size);
        } else {
            out.write(0xc6);
            out.writeInt(size);
        }
    }

    private void writeArraySize(int size, Output out) {
        if (size <= MAX_FIXARRAY_SIZE) {
            out.write(0x90 | size);
        } else if (size <= MAX_ARRAY16_SIZE) {
            out.write(0xdc);
            out.writeShort(size);
        } else {
            out.write(0xdd);
            out.writeInt(size);
        }
    }

    private void writeMapSize(int size, Output out) {
        if (size <= MAX_FIXMAP_SIZE) {
            out.write(0x80 | size);
        } else if (size <= MAX_MAP16_SIZE) {
            out.write(0xde);
            out.writeShort(size);
        } else {
            out.write(0xdf);
            out.writeInt(size);
        }
    }

    private void writeBin(byte[] value, Output out) {
        writeBinSize(value.length, out);
        out.write(value);
    }

    private void writeChars(char[] value, Output out) {
        writeBinSize(value.length, out);
        out.ensureCapacity(value.length);
        for (char ch : value) {
            out.write(ch);
        }
    }

    private void writeChars(Character[] value, Output out) {
        writeBinSize(value.length, out);
        out.ensureCapacity(value.length);
        for (Character ch : value) {
            out.write(ch);
        }
    }

    private void writeBytes(Byte[] value, Output out) {
        writeBinSize(value.length, out);
        out.ensureCapacity(value.length);
        for (Byte b : value) {
            out.write(b);
        }
    }

    private void writeBooleans(boolean[] value, Output out) {
        writeArraySize(value.length, out);
        for (boolean item : value) {
            writeBoolean(item, out);
        }
    }

    private void writeDoubles(double[] value, Output out) {
        writeArraySize(value.length, out);
        for (double item : value) {
            writeDouble(item, out);
        }
    }

    private void writeFloats(float[] value, Output out) {
        writeArraySize(value.length, out);
        for (float item : value) {
            writeFloat(item, out);
        }
    }

    private void writeInts(int[] value, Output out) {
        writeArraySize(value.length, out);
        for (int item : value) {
            writeInt(item, out);
        }
    }

    private void writeLongs(long[] value, Output out) {
        writeArraySize(value.length, out);
        for (long item : value) {
            writeInt(item, out);
        }
    }

    private void writeShorts(short[] value, Output out) {
        writeArraySize(value.length, out);
        for (short item : value) {
            writeInt(item, out);
        }
    }

    private void writeValues(Object[] value, Output out) {
        writeArraySize(value.length, out);
        for (Object item : value) {
            writeValue(item, out);
        }
    }

    private void writeMap(Map<?, ?> map, Output out) {
        writeEntries(map.entrySet(), map.size(), out);
    }

    private void writeObject(EzyObject object, Output out) {
        Set<Entry<Object, Object>> entries = object.entrySet();
        writeEntries(entries, entries.size(), out);
    }

    private void writeEntries(
        Collection<? extends Entry<?, ?>> entries,
        int size,
        Output out
    ) {
        writeMapSize(size, out);
        int count = 0;
        for (Entry<?, ?> entry : entries) {
            writeValue(entry.getKey(), out);
            writeValue(entry.getValue(), out);
            ++count;
        }
        checkSize(size, count);
    }

    private void writeArray(EzyArray array, Output out) {
        writeIterator(array.iterator(), array.size(), out);
    }

    private void writeCollection(Collection<?> collection, Output out) {
        writeIterator(collection.iterator(), collection.size(), out);
    }

    private void writeIterator(Iterator<?> iterator, int size, Output out) {
        writeArraySize(size, out);
        int count = 0;
        while (iterator.hasNext()) {
            writeValue(iterator.next(), out);
            ++count;
        }
        checkSize(size, count);
    }

    private static void checkSize(int expected, int actual) {
        if (expected != actual) {
            throw new EzyCodecException(
                "size changed while serializing, expected: " + expected + ", actual: " + actual
            );
        }
    }

    @FunctionalInterface
    private interface ValueWriter {
        void write(Object value, Output out);
    }

    private final class WriterParser implements EzyParser<Object, byte[]> {

        private final ValueWriter writer;

        private WriterParser(ValueWriter writer) {
            this.writer = writer;
        }

        @Override
        public byte[] parse(Object value) {
            return toBytes(out -> writer.write(value, out));
        }
    }

    private static final class Output {

        private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;

        private byte[] buffer;
        private int count;

        private Output(int capacity) {
            this.buffer = new byte[capacity];
        }

        private void ensureCapacity(int length) {
            int required = count + length;
            if (required < 0 || required > MAX_ARRAY_SIZE) {
                throw new EzyCodecException("serialized data is too large");
            }
            if (required > buffer.length) {
                long newCapacity = Math.max((long) buffer.length << 1, required);
                buffer = Arrays.copyOf(buffer, (int) Math.min(newCapacity, MAX_ARRAY_SIZE));
            }
        }

        private void write(int value) {
            ensureCapacity(1);
            buffer[count++] = (byte) value;
        }

        private void write(byte[] value) {
            ensureCapacity(value.length);
            System.arraycopy(value, 0, buffer, count, value.length);
            count += value.length;
        }

        private void writeShort(int value) {
            ensureCapacity(2);
            buffer[count++] = (byte) (value >>> 8);
            buffer[count++] = (byte) value;
        }

        private void writeInt(int value) {
            ensureCapacity(4);
            buffer[count++] = (byte) (value >>> 24);
            buffer[count++] = (byte) (value >>> 16);
            buffer[count++] = (byte) (value >>> 8);
            buffer[count++] = (byte) value;
        }

        private void writeLong(long value) {
            ensureCapacity(8);
            for (int shift = 56; shift >= 0; shift -= 8) {
                buffer[count++] = (byte) (value >>> shift);
            }
        }

        private void writeAscii(String value, int length) {
            ensureCapacity(length);
            for (int i = 0; i < length; ++i) {
                buffer[count++] = (byte) value.charAt(i);
            }
        }

        private byte[] toByteArray() {
            return count == buffer.length
                ? buffer
                : Arrays.copyOf(buffer, count);
        }
    }
}
