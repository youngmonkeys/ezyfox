package com.tvd12.ezyfox.util;

public final class EzyArrayUtil {

    private EzyArrayUtil() {}
    
    public static boolean isEmpty(boolean[] array) {
        return array == null || array.length == 0;
    }
    
    public static boolean isEmpty(byte[] array) {
        return array == null || array.length == 0;
    }
    
    public static boolean isEmpty(char[] array) {
        return array == null || array.length == 0;
    }
    
    public static boolean isEmpty(double[] array) {
        return array == null || array.length == 0;
    }
    
    public static boolean isEmpty(float[] array) {
        return array == null || array.length == 0;
    }
    
    public static boolean isEmpty(int[] array) {
        return array == null || array.length == 0;
    }
    
    public static boolean isEmpty(long[] array) {
        return array == null || array.length == 0;
    }
    
    public static boolean isEmpty(short[] array) {
        return array == null || array.length == 0;
    }
    
    public static <T> boolean isEmpty(T[] array) {
        return array == null || array.length == 0;
    }
    
    public static boolean isNotEmpty(boolean[] array) {
        return !isEmpty(array);
    }
    
    public static boolean isNotEmpty(byte[] array) {
        return !isEmpty(array);
    }
    
    public static boolean isNotEmpty(char[] array) {
        return !isEmpty(array);
    }
    
    public static boolean isNotEmpty(double[] array) {
        return !isEmpty(array);
    }
    
    public static boolean isNotEmpty(float[] array) {
        return !isEmpty(array);
    }
    
    public static boolean isNotEmpty(int[] array) {
        return !isEmpty(array);
    }
    
    public static boolean isNotEmpty(long[] array) {
        return !isEmpty(array);
    }
    
    public static boolean isNotEmpty(short[] array) {
        return !isEmpty(array);
    }
    
    public static <T> boolean isNotEmpty(T[] array) {
        return !isEmpty(array);
    }
}
