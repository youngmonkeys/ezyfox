package com.tvd12.ezyfox.codec;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.AbstractCollection;
import java.util.AbstractList;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import com.tvd12.ezyfox.codec.EzyAbstractToBytesSerializer;
import com.tvd12.ezyfox.entity.EzyArray;
import com.tvd12.ezyfox.entity.EzyArrayList;
import com.tvd12.ezyfox.entity.EzyHashMap;
import com.tvd12.ezyfox.entity.EzyObject;
import com.tvd12.ezyfox.function.EzyParser;
import com.tvd12.ezyfox.io.EzyBytes;
import com.tvd12.ezyfox.io.EzyCastToByte;
import com.tvd12.ezyfox.io.EzyDataConverter;
import com.tvd12.ezyfox.io.EzyStrings;
import com.tvd12.ezyfox.util.EzyBoolsIterator;
import com.tvd12.ezyfox.util.EzyDoublesIterator;
import com.tvd12.ezyfox.util.EzyFloatsIterator;
import com.tvd12.ezyfox.util.EzyIntsIterator;
import com.tvd12.ezyfox.util.EzyLongsIterator;
import com.tvd12.ezyfox.util.EzyShortsIterator;
import com.tvd12.ezyfox.util.EzyStringsIterator;
import com.tvd12.ezyfox.util.EzyWrapperIterator;

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
		parsers.put(Boolean.class, value -> parseBoolean(value));
		parsers.put(Byte.class, value -> parseByte(value));
		parsers.put(Character.class, value -> parseChar(value));
		parsers.put(Double.class, value -> parseDouble(value));
		parsers.put(Float.class, value -> parseFloat(value));
		parsers.put(Integer.class, value -> parseInt(value));
		parsers.put(Long.class, value -> parseInt(value));
		parsers.put(Short.class, value -> parseShort(value));
		parsers.put(String.class, value -> parseString(value));
		
		parsers.put(boolean[].class, value -> parsePrimitiveBooleans(value));
		parsers.put(byte[].class, value -> parseBin(value));
		parsers.put(char[].class, value -> parsePrimitiveChars(value));
		parsers.put(double[].class, value -> parsePrimitiveDoubles(value));
		parsers.put(float[].class, value -> parsePrimitiveFloats(value));
		parsers.put(int[].class, value -> parsePrimitiveInts(value));
		parsers.put(long[].class, value -> parsePrimitiveLongs(value));
		parsers.put(short[].class, value -> parsePrimitiveShorts(value));
		parsers.put(String[].class, value -> parseStrings(value));
		
		parsers.put(Byte[].class, value -> parseWrapperBytes(value));
		parsers.put(Boolean[].class, value -> parseWrapperBooleans(value));
		parsers.put(Character[].class, value -> parseWrapperChars(value));
		parsers.put(Double[].class, value -> parseWrapperDoubles(value));
		parsers.put(Float[].class, value -> parseWrapperFloats(value));
		parsers.put(Integer[].class, value -> parseWrapperInts(value));
		parsers.put(Long[].class, value -> parseWrapperLongs(value));
		parsers.put(Short[].class, value -> parseWrapperShorts(value));
		
		parsers.put(Map.class, value -> parseMap(value));
		parsers.put(AbstractMap.class, value -> parseMap(value));
		parsers.put(HashMap.class, value -> parseMap(value));
		parsers.put(EzyObject.class, value -> parseObject(value));
		parsers.put(EzyHashMap.class, value -> parseObject(value));
		parsers.put(EzyArray.class, value -> parseArray(value));
		parsers.put(EzyArrayList.class, value -> parseArray(value));
		parsers.put(Collection.class, value -> parseCollection(value));
		parsers.put(AbstractCollection.class, value -> parseCollection(value));
		parsers.put(Set.class, value -> parseCollection(value));
		parsers.put(AbstractSet.class, value -> parseCollection(value));
		parsers.put(List.class, value -> parseCollection(value));
		parsers.put(AbstractList.class, value -> parseCollection(value));
		parsers.put(HashSet.class, value -> parseCollection(value));
		parsers.put(ArrayList.class, value -> parseCollection(value));
		
		parsers.put(BigInteger.class, value -> parseValueToString(value));
		parsers.put(BigDecimal.class, value -> parseValueToString(value));
		parsers.put(UUID.class, value -> parseValueToString(value));
	}
	
	//
	protected byte[] parsePrimitiveBooleans(Object array) {
		return parseBooleans((boolean[])array);
	}
	
	protected byte[] parsePrimitiveChars(Object array) {
		return parseChars((char[])array);
	}
	
	protected byte[] parsePrimitiveDoubles(Object array) {
		return parseDoubles((double[])array);
	}
	
	protected byte[] parsePrimitiveFloats(Object array) {
		return parseFloats((float[])array);
	}
	
	protected byte[] parsePrimitiveInts(Object array) {
		return parseInts((int[])array);
	}
	
	protected byte[] parsePrimitiveLongs(Object array) {
		return parseLongs((long[])array);
	}
	
	protected byte[] parsePrimitiveShorts(Object array) {
		return parseShorts((short[])array);
	}
	
	protected byte[] parseStrings(Object array) {
		return parseStrings((String[])array);
	}
	//
	
	protected byte[] parseBooleans(boolean[] array) {
		return parseArray(EzyBoolsIterator.wrap(array), array.length);
	}
	
	protected byte[] parseChars(char[] array) {
		return parseBin(EzyDataConverter.charArrayToByteArray(array));
	}
	
	protected byte[] parseDoubles(double[] array) {
		return parseArray(EzyDoublesIterator.wrap(array), array.length);
	}
	
	protected byte[] parseFloats(float[] array) {
		return parseArray(EzyFloatsIterator.wrap(array), array.length);
	}
	
	protected byte[] parseInts(int[] array) {
		return parseArray(EzyIntsIterator.wrap(array), array.length);
	}
	
	protected byte[] parseLongs(long[] array) {
		return parseArray(EzyLongsIterator.wrap(array), array.length);
	}
	
	protected byte[] parseShorts(short[] array) {
		return parseArray(EzyShortsIterator.wrap(array), array.length);
	}
	
	protected byte[] parseStrings(String[] array) {
		return parseArray(EzyStringsIterator.wrap(array), array.length);
	}
	
	//=============
	protected byte[] parseWrapperBooleans(Object array) {
		return parseBooleans((Boolean[])array);
	}
	
	protected byte[] parseWrapperBytes(Object array) {
		return parseBytes((Byte[])array);
	}
	
	protected byte[] parseWrapperChars(Object array) {
		return parseChars((Character[])array);
	}
	
	protected byte[] parseWrapperDoubles(Object array) {
		return parseDoubles((Double[])array);
	}
	
	protected byte[] parseWrapperFloats(Object array) {
		return parseFloats((Float[])array);
	}
	
	protected byte[] parseWrapperInts(Object array) {
		return parseInts((Integer[])array);
	}
	
	protected byte[] parseWrapperLongs(Object array) {
		return parseLongs((Long[])array);
	}
	
	protected byte[] parseWrapperShorts(Object array) {
		return parseShorts((Short[])array);
	}
	//
	//=============
	
	//
	protected byte[] parseBooleans(Boolean[] array) {
		return parseArray(EzyWrapperIterator.wrap(array), array.length);
	}
	
	protected byte[] parseBytes(Byte[] array) {
		return parseBin(EzyDataConverter.toPrimitiveByteArray(array));
	}
	
	protected byte[] parseChars(Character[] array) {
		return parseBin(EzyDataConverter.charWrapperArrayToPrimitiveByteArray(array));
	}
	
	protected byte[] parseDoubles(Double[] array) {
		return parseArray(EzyWrapperIterator.wrap(array), array.length);
	}
	
	protected byte[] parseFloats(Float[] array) {
		return parseArray(EzyWrapperIterator.wrap(array), array.length);
	}
	
	protected byte[] parseInts(Integer[] array) {
		return parseArray(EzyWrapperIterator.wrap(array), array.length);
	}
	
	protected byte[] parseLongs(Long[] array) {
		return parseArray(EzyWrapperIterator.wrap(array), array.length);
	}
	
	protected byte[] parseShorts(Short[] array) {
		return parseArray(EzyWrapperIterator.wrap(array), array.length);
	}
	//
	
	protected byte[] parseBoolean(Object value) {
		return parseBoolean((Boolean)value);
	}
	
	protected byte[] parseBoolean(Boolean value) {
		return value ? parseTrue() : parseFalse();
	}
	
	protected byte[] parseFalse() {
		return new byte[] {cast(0xc2)};
	}
	
	protected byte[] parseTrue() {
		return new byte[] {cast(0xc3)};
	}
	
	protected byte[] parseByte(Object value) {
		return parseByte((Byte)value);
	}
	
	protected byte[] parseByte(Byte value) {
		return parseInt(value.intValue());
	}
	
	protected byte[] parseChar(Object value) {
		return parseChar((Character)value);
	}
	
	protected byte[] parseChar(Character value) {
		return parseByte((byte)value.charValue());
	}
	
	protected byte[] parseDouble(Object value) {
		return parseDouble((Double)value);
	}
	
	protected byte[] parseDouble(Double value) {
		return doubleSerializer.serialize(value);
	}
	
	protected byte[] parseFloat(Object value) {
		return parseFloat((Float)value);
	}
	
	protected byte[] parseFloat(Float value) {
		return floatSerializer.serialize(value);
	}
	
	protected byte[] parseInt(Object value) {
		return intSerializer.serialize(((Number)value).longValue());
	}
	
	protected byte[] parseShort(Object value) {
		return parseShort((Short)value);
	}
	
	protected byte[] parseShort(Short value) {
		return parseInt(value.intValue());
	}
	
	protected byte[] parseString(Object string) {
		return parseString((String)string);
	}
	
	@SuppressWarnings({ "rawtypes" })
	protected byte[] parseMap(Object map) {
		return parseMap((Map)map);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected byte[] parseMap(Map map) {
		return parseEntries(map.entrySet());
	}
	
	protected byte[] parseObject(Object obj) {
		return parseEntries(((EzyObject)obj).entrySet());
	}
	
	@SuppressWarnings({ "rawtypes" })
	protected byte[] parseCollection(Object coll) {
		return parseCollection((Collection)coll);
	}
	
	protected byte[] parseArray(Object array) {
		return parseArray((EzyArray)array);
	}
	
	@SuppressWarnings({ "rawtypes" })
	protected byte[] parseCollection(Collection coll) {
		return parseIterable(coll, coll.size());
	}
	
	protected byte[] parseArray(EzyArray array) {
		return parseArray(array.iterator(), array.size());
	}
	
	protected byte[] parseNil() {
		return new byte[] {cast(0xc0)};
	}
	
	protected byte[] parseBin(Object bin) {
		return parseBin((byte[])bin);
	}
	
	protected byte[] parseBin(byte[] bin) {
		byte[][] bytess = new byte[2][];
		bytess[0] = parseBinSize(bin.length);
		bytess[1] = bin;
		return EzyBytes.merge(bytess);
	}
	
	protected byte[] parseBinSize(int size) {
		return binSizeSerializer.serialize(size);
	}
	
	protected byte[] parseString(String string) {
		byte[][] bytess = new byte[2][];
		bytess[1] = EzyStrings.getUtfBytes(string);
		bytess[0] = parseStringSize(bytess[1].length);
		return EzyBytes.merge(bytess);
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
		byte[][] bytess = new byte[size + 1][];
		bytess[0] = parseArraySize(size);
		while(iterator.hasNext())
			bytess[index ++] = serialize(iterator.next());
		return EzyBytes.merge(bytess);
	}
	
	protected byte[] parseArraySize(int size) {
		return arraySizeSerializer.serialize(size);
	}
	
	protected byte[] parseEntries(Set<Entry<Object, Object>> entries) {
		int index = 1;
		int size = entries.size();
		byte[][] bytess = new byte[size * 2 + 1][];
		bytess[0] = parseMapSize(size);
		for(Entry<Object, Object> e : entries) {
			bytess[index++] = serialize(e.getKey());
			bytess[index++] = serialize(e.getValue());
		}
		return EzyBytes.merge(bytess);
	}
	
	protected byte[] parseMapSize(int size) {
		return mapSizeSerializer.serialize(size);
	}
	
	protected byte[] parseValueToString(Object value) {
		return parseString(value.toString());
	}
}