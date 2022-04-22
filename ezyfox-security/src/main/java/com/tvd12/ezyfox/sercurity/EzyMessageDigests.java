package com.tvd12.ezyfox.sercurity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class EzyMessageDigests {

    private EzyMessageDigests() {
    }

    public static MessageDigest getAlgorithm(String algorithm) {
        try {
            return MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException("has no algorithm: " + algorithm);
        }
    }
}