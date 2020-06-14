package com.tvd12.ezyfox.sercurity;

import java.security.MessageDigest;

import com.tvd12.ezyfox.io.EzyPrints;
import com.tvd12.ezyfox.io.EzyStrings;

public final class EzyMD5 {
	
	private static final String ALGORITHM = "MD5";
	private static final String DEFAULT_SALT = "$1$ezyfox";

	private EzyMD5() {}
	
	public static String cryptUtf(String input) {
		String answer = cryptUtf(input, DEFAULT_SALT);
		return answer;
	}
	
	public static String cryptUtf(String input, String salt) {
		String hex = EzyPrints.printHex(cryptUtfToBytes(input, salt));
		return hex;
	}
	
	public static String cryptUtfToLowercase(String input) {
		String answer = cryptUtfToLowercase(input, DEFAULT_SALT);
		return answer;
	}
	
	public static String cryptUtfToLowercase(String input, String salt) {
		String hex = EzyPrints.printHexLowercase(cryptUtfToBytes(input, salt));
		return hex;
	}
	
	public static byte[] cryptUtfToBytes(String input, String salt) {
		MessageDigest md = EzyMessageDigests.getAlgorithm(ALGORITHM);
		String message = salt + input;
		byte[] bytes = EzyStrings.getUtfBytes(message);
		md.update(bytes);
		byte[] digest = md.digest();
		return digest;
	}
	
}
