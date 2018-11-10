package com.tvd12.ezyfox.io;

import java.util.Arrays;

public final class EzyPrints {

	private final static char[] HEX_ARRAY = new char[] {
			'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
	};
	
	private EzyPrints() {
	}
	
	public static String print(Object object) {
		if(object == null)
			return "null";
		if(object instanceof boolean[])
			return Arrays.toString((boolean[])object);
		if(object instanceof byte[])
			return Arrays.toString((byte[])object);
		if(object instanceof char[])
			return Arrays.toString((char[])object);
		if(object instanceof double[])
			return Arrays.toString((double[])object);
		if(object instanceof float[])
			return Arrays.toString((float[])object);
		if(object instanceof int[])
			return Arrays.toString((int[])object);
		if(object instanceof long[])
			return Arrays.toString((long[])object);
		if(object instanceof short[])
			return Arrays.toString((short[])object);
		if(object instanceof Object[])
			return Arrays.toString((Object[])object);
		return object.toString();
	}
	
	public static String printBits(byte[] bytes) {
		StringBuilder builder = new StringBuilder();
		for(int i = 0 ; i < bytes.length ; i++) 
			builder.append(printBits(bytes[i]));
		return builder.toString();
	}
	
	public static String printBits(byte value) {
		String str = insertBegin(Integer.toBinaryString(value & 0xff), "0", 8);
		return str.substring(str.length() - 8);
	}
	
	public static String insertBegin(String str, String ch, int maxlen) {
		StringBuilder builder = new StringBuilder(str);
		while(builder.length() < maxlen)
			builder.insert(0, ch);
		return builder.toString();
	}
	
	public static String printHex(byte[] bytes) {
		char[] hexChars = new char[bytes.length * 2];
		for (int i = 0; i < bytes.length; i++) {
			int v = bytes[i] & 0xFF;
			hexChars[i * 2] = HEX_ARRAY[v >>> 4];
			hexChars[i * 2 + 1] = HEX_ARRAY[v & 0x0F];
		}
		String answer = new String(hexChars);
		return answer;
	}
	
}
