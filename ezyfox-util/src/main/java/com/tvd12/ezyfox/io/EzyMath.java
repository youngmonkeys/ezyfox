package com.tvd12.ezyfox.io;

public final class EzyMath {

    private EzyMath() {}

    public static int bin2uint(byte[] bytes) {
        return (int) bin2ulong(bytes);
    }

    public static int bin2int(int length) {
        return (int) bin2long(length);
    }

    public static int bin2int(byte[] bytes) {
        return (int) bin2long(bytes);
    }

    public static long bin2ulong(byte[] bytes) {
        return bin2long(bytes, true);
    }

    public static long bin2long(int length) {
        long result = 0;
        for (int i = 0; i < length; ++i) {
            result |= 1L << i;
        }
        return result;
    }

    public static long bin2long(byte[] bytes) {
        return bin2long(bytes, false);
    }

    private static long bin2long(byte[] bytes, boolean unsigned) {
        if (bytes.length == 0) {
            throw new IllegalArgumentException("bytes must not be empty");
        }
        long result = bytes[0];
        if (unsigned) {
            result &= 0xFFL;
        }
        for (int i = 1; i < bytes.length; ++i) {
            result = (result << 8) | (bytes[i] & 0xFFL);
        }
        return result;
    }

    public static void xor(byte[] bytes) {
        for (int i = 0; i < bytes.length; ++i) {
            bytes[i] ^= 0xff;
        }
    }
}
