package com.tvd12.ezyfox.sercurity;

import java.security.MessageDigest;

import com.tvd12.ezyfox.io.EzyPrints;
import com.tvd12.ezyfox.io.EzyStrings;

public final class EzyMD5 {

	private EzyMD5() {
	}
	
	public static String cryptUtf(String input) {
		String answer = cryptUtf(input, "$1$ezyfox");
		return answer;
	}
	
	public static String cryptUtf(String input, String salt) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			String message = salt + input;
			byte[] bytes = EzyStrings.getUtfBytes(message);
			md.update(bytes);
			byte[] digest = md.digest();
			String hex = EzyPrints.printHex(digest);
			return hex;
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}
	
}
