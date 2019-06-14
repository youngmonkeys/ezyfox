package com.tvd12.ezyfox.io;

import static com.tvd12.ezyfox.io.EzyDoubleArrays.toByteArray;
import static com.tvd12.ezyfox.io.EzyDoubleArrays.toDoubleArray;

import java.util.Base64;


public final class EzyBase64DoubleArrays {

	private EzyBase64DoubleArrays() {
	}
	
	public static String encode(double[] doubleArray) {
		byte[] bytes = toByteArray(doubleArray);
		String str = Base64.getEncoder().encodeToString(bytes);
		return str;
	}

	public static double[] decode(String base64Encoded) {
		byte[] bytes = Base64.getDecoder().decode(base64Encoded);
		double[] array = toDoubleArray(bytes);
		return array;
	}

}
