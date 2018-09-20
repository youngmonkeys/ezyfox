package com.tvd12.ezyfox.sercurity;

import static com.tvd12.ezyfox.io.EzyDoubleArrays.toByteArray;
import static com.tvd12.ezyfox.io.EzyDoubleArrays.toDoubleArray;

import org.apache.commons.codec.binary.Base64;

import com.tvd12.ezyfox.io.EzyStrings;

public final class EzyBase64 {

	private EzyBase64() {
	}
	
	public static byte[] encode(byte[] input) {
		byte[] answer = Base64.encodeBase64(input);
		return answer;
	}
	
	public static byte[] decode(byte[] input) {
		byte[] answer = Base64.decodeBase64(input);
		return answer;
	}
	
	public static byte[] decode(String input) {
		byte[] answer = Base64.decodeBase64(input);
		return answer;
	}
	
	public static byte[] encode(String input) {
		byte[] answer = Base64.encodeBase64(EzyStrings.getUtfBytes(input));
		return answer;
	}
	
	public static String encodeUtf(String input) {
		String answer = Base64.encodeBase64String(EzyStrings.getUtfBytes(input));
		return answer;
	}
	
	public static String decodeUtf(String input) {
		String answer = EzyStrings.newUtf(Base64.decodeBase64(input));
		return answer;
	}
	
	public static String encode2utf(byte[] input) {
		String answer = EzyStrings.newUtf(encode(input));
		return answer;
	}
	
	public static String decode2utf(byte[] input) {
		String answer = EzyStrings.newUtf(decode(input));
		return answer;
	}
	
	public static String encode(double[] doubleArray) {
		byte[] bytes = toByteArray(doubleArray);
		String answer = encode2utf(bytes);
		return answer;
	}

	public static double[] decode2doubles(String base64Encoded) {
		byte[] bytes = decode(base64Encoded);
		double[] answer = toDoubleArray(bytes);
		return answer;
	}
	
}
