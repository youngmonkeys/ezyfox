package com.tvd12.ezyfox.sercurity;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

import com.tvd12.ezyfox.io.EzyPrints;

public final class EzySHA256 {

	private static final String ALGORITHM = "SHA-256";
	
	private EzySHA256() {
	}

	public static String cryptUtf(String originalString) {
		byte[] encodedhash = cryptUtfToBytes(originalString);
		String hex = EzyPrints.printHex(encodedhash);
		return hex;
	}
	
	public static String cryptUtfToLowercase(String originalString) {
		byte[] encodedhash = cryptUtfToBytes(originalString);
		String hex = EzyPrints.printHexLowercase(encodedhash);
		return hex;
	}
	
	public static byte[] cryptUtfToBytes(String originalString) {
		MessageDigest digest = EzyMessageDigests.getAlgorithm(ALGORITHM);
		byte[] bytes = originalString.getBytes(StandardCharsets.UTF_8);
		byte[] encodedhash = digest.digest(bytes);
		return encodedhash;
	}
	

}
