package com.tvd12.ezyfox.sercurity;

import org.apache.commons.codec.digest.Md5Crypt;

import com.tvd12.ezyfox.io.EzyStrings;

public final class EzyMD5 {

	private EzyMD5() {
	}
	
	public static String cryptUtf(String input) {
		String answer = cryptUtf(input, "$1$ezyfox");
		return answer;
	}
	
	public static String cryptUtf(String input, String salt) {
		String answer = Md5Crypt.md5Crypt(EzyStrings.getUtfBytes(input), salt);
		return answer;
	}
	
}
