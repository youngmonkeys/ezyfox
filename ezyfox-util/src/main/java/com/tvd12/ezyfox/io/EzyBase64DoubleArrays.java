package com.tvd12.ezyfox.io;

import java.util.Base64;

import static com.tvd12.ezyfox.io.EzyDoubleArrays.toByteArray;
import static com.tvd12.ezyfox.io.EzyDoubleArrays.toDoubleArray;


public final class EzyBase64DoubleArrays {

    private EzyBase64DoubleArrays() {}

    public static String encode(double[] doubleArray) {
        byte[] bytes = toByteArray(doubleArray);
        return Base64.getEncoder().encodeToString(bytes);
    }

    public static double[] decode(String base64Encoded) {
        byte[] bytes = Base64.getDecoder().decode(base64Encoded);
        return toDoubleArray(bytes);
    }
}
