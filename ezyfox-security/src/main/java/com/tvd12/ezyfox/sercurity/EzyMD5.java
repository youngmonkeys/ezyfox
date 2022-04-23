package com.tvd12.ezyfox.sercurity;

import com.tvd12.ezyfox.io.EzyPrints;

import java.security.MessageDigest;

@SuppressWarnings("AbbreviationAsWordInName")
public final class EzyMD5 {

    private static final String ALGORITHM = "MD5";
    private static final String DEFAULT_SALT = "$1$ezyfox";

    private EzyMD5() {}

    public static String cryptUtf(String input) {
        return cryptUtf(input.getBytes());
    }

    public static String cryptUtf(byte[] input) {
        return cryptUtf(input, DEFAULT_SALT);
    }

    public static String cryptUtf(String input, String salt) {
        return cryptUtf(input.getBytes(), salt);
    }

    public static String cryptUtf(byte[] input, String salt) {
        return EzyPrints.printHex(cryptUtfToBytes(input, salt));
    }

    public static String cryptUtfToLowercase(String input) {
        return cryptUtfToLowercase(input.getBytes());
    }

    public static String cryptUtfToLowercase(byte[] input) {
        return cryptUtfToLowercase(input, DEFAULT_SALT);
    }

    public static String cryptUtfToLowercase(String input, String salt) {
        return cryptUtfToLowercase(input.getBytes(), salt);
    }

    public static String cryptUtfToLowercase(byte[] input, String salt) {
        return EzyPrints.printHexLowercase(cryptUtfToBytes(input, salt));
    }

    public static byte[] cryptUtfToBytes(String input, String salt) {
        return cryptUtfToBytes(input.getBytes(), salt);
    }

    public static byte[] cryptUtfToBytes(byte[] input, String salt) {
        MessageDigest md = EzyMessageDigests.getAlgorithm(ALGORITHM);
        byte[] saltBytes = salt.getBytes();
        byte[] bytes = new byte[input.length + saltBytes.length];
        System.arraycopy(input, 0, bytes, 0, input.length);
        System.arraycopy(saltBytes, 0, bytes, input.length, saltBytes.length);
        md.update(bytes);
        return md.digest();
    }
}
