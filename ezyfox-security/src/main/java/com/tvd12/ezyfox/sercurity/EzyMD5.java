package com.tvd12.ezyfox.sercurity;

import java.security.MessageDigest;

import com.tvd12.ezyfox.io.EzyPrints;

public final class EzyMD5 {

    private static final String ALGORITHM = "MD5";
    private static final String DEFAULT_SALT = "$1$ezyfox";

    private EzyMD5() {}

    public static String cryptUtf(String input) {
        return cryptUtf(input.getBytes());
    }

    public static String cryptUtf(byte[] input) {
        String answer = cryptUtf(input, DEFAULT_SALT);
        return answer;
    }

    public static String cryptUtf(String input, String salt) {
        return cryptUtf(input.getBytes(), salt);
    }

    public static String cryptUtf(byte[] input, String salt) {
        String hex = EzyPrints.printHex(cryptUtfToBytes(input, salt));
        return hex;
    }

    public static String cryptUtfToLowercase(String input) {
        return cryptUtfToLowercase(input.getBytes());
    }

    public static String cryptUtfToLowercase(byte[] input) {
        String answer = cryptUtfToLowercase(input, DEFAULT_SALT);
        return answer;
    }

    public static String cryptUtfToLowercase(String input, String salt) {
        return cryptUtfToLowercase(input.getBytes(), salt);
    }

    public static String cryptUtfToLowercase(byte[] input, String salt) {
        String hex = EzyPrints.printHexLowercase(cryptUtfToBytes(input, salt));
        return hex;
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
        byte[] digest = md.digest();
        return digest;
    }

}
