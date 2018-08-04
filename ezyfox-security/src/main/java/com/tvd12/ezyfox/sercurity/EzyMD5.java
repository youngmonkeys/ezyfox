package com.tvd12.ezyfox.sercurity;

import org.apache.commons.codec.digest.Md5Crypt;

import com.tvd12.ezyfox.io.EzyStrings;

public final class EzyMD5 {

	private EzyMD5() {
	}
	
	public static String cryptUtf(String input) {
		return cryptUtf(input, "$1$ezyfox");
	}
	
	public static String cryptUtf(String input, String salt) {
		return Md5Crypt.md5Crypt(EzyStrings.getUtfBytes(input), salt);
	}
	
}
