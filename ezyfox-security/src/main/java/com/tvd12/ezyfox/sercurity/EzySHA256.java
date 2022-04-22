package com.tvd12.ezyfox.sercurity;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

import com.tvd12.ezyfox.io.EzyPrints;

public final class EzySHA256 {

    public static final String ALGORITHM = "SHA-256";
    
    private EzySHA256() {}

    public static String cryptUtf(String originalString) {
        byte[] bytes = originalString.getBytes(StandardCharsets.UTF_8);
        return cryptUtf(bytes);
    }
    
    public static String cryptUtf(byte[] bytes) {
        byte[] encodedhash = cryptUtfToBytes(bytes);
        String hex = EzyPrints.printHex(encodedhash);
        return hex;
    }
    
    public static String cryptUtfToLowercase(String originalString) {
        byte[] bytes = originalString.getBytes(StandardCharsets.UTF_8);
        return cryptUtfToLowercase(bytes);
    }
    
    public static String cryptUtfToLowercase(byte[] bytes) {
        byte[] encodedhash = cryptUtfToBytes(bytes);
        String hex = EzyPrints.printHexLowercase(encodedhash);
        return hex;
    }
    
    public static byte[] cryptUtfToBytes(String originalString) {
        byte[] bytes = originalString.getBytes(StandardCharsets.UTF_8);
        return cryptUtfToBytes(bytes);
    }
    
    public static byte[] cryptUtfToBytes(byte[] bytes) {
        MessageDigest digest = EzyMessageDigests.getAlgorithm(ALGORITHM);
        byte[] encodedhash = digest.digest(bytes);
        return encodedhash;
    }
}