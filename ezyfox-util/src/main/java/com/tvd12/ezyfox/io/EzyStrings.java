package com.tvd12.ezyfox.io;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.Collection;

public final class EzyStrings {
	
	public static final String NULL = "null";
	public static final String UTF_8 = "UTF-8";

	private EzyStrings() {
	}
	
	public static String newUtf(byte[] bytes) {
		return newString(bytes, UTF_8);
	}
	
	public static String newUtf(ByteBuffer buffer, int size) {
		return newString(buffer, size, UTF_8);
	}
	
	public static byte[] getUtfBytes(String str) {
		return getBytes(str, UTF_8);
	}
	
	public static String newString(byte[] bytes, String charset) {
		try {
			return new String(bytes, charset);
		} catch (UnsupportedEncodingException e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	public static byte[] getBytes(String str, String charset) {
		try {
			return str.getBytes(charset);
		} catch (UnsupportedEncodingException e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	public static String newString(ByteBuffer buffer, int size, String charset) {
		byte[] bytes = new byte[size];
		buffer.get(bytes);
		return newString(bytes, charset);
	}
	
	public static String getString(String[] array, int index, String def) {
		return array.length > index ? array[index] : def;
	}
	
	public static String quote(Object value) {
		return new StringBuilder()
				.append("\"")
				.append(value != null ? value.toString() : NULL)
				.append("\"")
				.toString();
	}
	
	public static String newString(char ch, int count) {
		StringBuilder builder = new StringBuilder();
		for(int i = 0 ; i < count ; ++i)
			builder.append(ch);
		return builder.toString();
	}
	
	@SuppressWarnings("rawtypes")
	public static String wrap(
			Collection collection,
			String open, 
			String close, 
			String separator, boolean noWrapIfNull) {
		if(collection == null) {
			if(noWrapIfNull)
				return NULL;
			return new StringBuilder()
					.append(open)
					.append(close)
					.toString();
		}
		int size = collection.size();
		StringBuilder builder = new StringBuilder(open);
		int index = 0;
		for(Object item : collection) {
			builder.append(item);
			if((++ index) < size)
				builder.append(separator);
		}
		return builder.append(close).toString();
		
	}
	
}
