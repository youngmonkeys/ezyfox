package com.tvd12.ezyfox.sercurity;

import com.tvd12.ezyfox.io.EzyPrints;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

@SuppressWarnings("AbbreviationAsWordInName")
public final class EzySHA256 {

    public static final String ALGORITHM = "SHA-256";

    private EzySHA256() {}

    public static String cryptUtf(String originalString) {
        byte[] bytes = originalString.getBytes(StandardCharsets.UTF_8);
        return cryptUtf(bytes);
    }

    public static String cryptUtf(byte[] bytes) {
        byte[] encodedHash = cryptUtfToBytes(bytes);
        return EzyPrints.printHex(encodedHash);
    }

    public static String cryptUtfToLowercase(String originalString) {
        byte[] bytes = originalString.getBytes(StandardCharsets.UTF_8);
        return cryptUtfToLowercase(bytes);
    }

    public static String cryptUtfToLowercase(byte[] bytes) {
        byte[] encodedHash = cryptUtfToBytes(bytes);
        return EzyPrints.printHexLowercase(encodedHash);
    }

    public static byte[] cryptUtfToBytes(String originalString) {
        byte[] bytes = originalString.getBytes(StandardCharsets.UTF_8);
        return cryptUtfToBytes(bytes);
    }

    public static byte[] cryptUtfToBytes(byte[] bytes) {
        MessageDigest digest = EzyMessageDigests.getAlgorithm(ALGORITHM);
        return digest.digest(bytes);
    }
}
