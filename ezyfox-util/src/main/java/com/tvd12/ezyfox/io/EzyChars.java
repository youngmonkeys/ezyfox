package com.tvd12.ezyfox.io;

public final class EzyChars {

    private EzyChars() {
    }

    public static boolean isUpperCase(char ch) {
        boolean answer = ch >= 'A' && ch <= 'Z';
        return answer;
    }

    public static boolean isLowerCase(char ch) {
        boolean answer = ch < 'A' || ch > 'Z';
        return answer;
    }
}
