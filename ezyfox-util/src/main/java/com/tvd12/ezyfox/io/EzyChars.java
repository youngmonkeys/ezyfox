package com.tvd12.ezyfox.io;

public final class EzyChars {

    private EzyChars() {}

    public static boolean isUpperCase(char ch) {
        return ch >= 'A' && ch <= 'Z';
    }

    public static boolean isLowerCase(char ch) {
        return ch < 'A' || ch > 'Z';
    }
}
