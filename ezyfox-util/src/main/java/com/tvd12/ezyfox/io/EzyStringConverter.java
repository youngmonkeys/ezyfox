package com.tvd12.ezyfox.io;

import java.util.function.Function;
import java.util.function.IntFunction;

public final class EzyStringConverter {

    private EzyStringConverter() {}

    // ====================== primitive array =================
    public static boolean[] stringToPrimitiveBoolArray(String string) {
        String[] strs = string.split(",");
        boolean[] array = new boolean[strs.length];
        for (int i = 0; i < strs.length; ++i) {
            array[i] = Boolean.parseBoolean(strs[i].trim());
        }
        return array;
    }

    public static byte[] stringToPrimitiveByteArray(String string) {
        String[] strs = string.split(",");
        byte[] array = new byte[strs.length];
        for (int i = 0; i < strs.length; ++i) {
            array[i] = Byte.parseByte(strs[i].trim());
        }
        return array;
    }

    public static char[] stringToPrimitiveCharArray(String string) {
        return string.toCharArray();
    }

    public static double[] stringToPrimitiveDoubleArray(String string) {
        String[] strs = string.split(",");
        double[] array = new double[strs.length];
        for (int i = 0; i < strs.length; ++i) {
            array[i] = Double.parseDouble(strs[i].trim());
        }
        return array;
    }

    public static float[] stringToPrimitiveFloatArray(String string) {
        String[] strs = string.split(",");
        float[] array = new float[strs.length];
        for (int i = 0; i < strs.length; ++i) {
            array[i] = Float.parseFloat(strs[i].trim());
        }
        return array;
    }

    public static int[] stringToPrimitiveIntArray(String string) {
        String[] strs = string.split(",");
        int[] array = new int[strs.length];
        for (int i = 0; i < strs.length; ++i) {
            array[i] = Integer.parseInt(strs[i].trim());
        }
        return array;
    }

    public static long[] stringToPrimitiveLongArray(String string) {
        String[] strs = string.split(",");
        long[] array = new long[strs.length];
        for (int i = 0; i < strs.length; ++i) {
            array[i] = Long.parseLong(strs[i].trim());
        }
        return array;
    }

    public static short[] stringToPrimitiveShortArray(String string) {
        String[] strs = string.split(",");
        short[] array = new short[strs.length];
        for (int i = 0; i < strs.length; ++i) {
            array[i] = Short.parseShort(strs[i].trim());
        }
        return array;
    }

    // ====================== wrapper array =================
    public static Boolean[] stringToWrapperBoolArray(String string) {
        return stringToArray(string, Boolean::valueOf, Boolean[]::new);
    }

    public static Byte[] stringToWrapperByteArray(String string) {
        return stringToArray(string, Byte::valueOf, Byte[]::new);
    }

    public static Character[] stringToWrapperCharArray(String string) {
        Character[] array = new Character[string.length()];
        for (int i = 0; i < string.length(); ++i) {
            array[i] = string.charAt(i);
        }
        return array;
    }

    public static Double[] stringToWrapperDoubleArray(String string) {
        return stringToArray(string, Double::valueOf, Double[]::new);
    }

    public static Float[] stringToWrapperFloatArray(String string) {
        return stringToArray(string, Float::valueOf, Float[]::new);
    }

    public static Integer[] stringToWrapperIntArray(String string) {
        return stringToArray(string, Integer::valueOf, Integer[]::new);
    }

    public static Long[] stringToWrapperLongArray(String string) {
        return stringToArray(string, Long::valueOf, Long[]::new);
    }

    public static Short[] stringToWrapperShortArray(String string) {
        return stringToArray(string, Short::valueOf, Short[]::new);
    }

    // ====================== two-dimensions primitive array =================
    public static boolean[][] stringToPrimitiveBoolArrays(String string) {
        String[] strs = string.split(";");
        boolean[][] arrays = new boolean[strs.length][];
        for (int i = 0; i < strs.length; ++i) {
            arrays[i] = stringToPrimitiveBoolArray(strs[i]);
        }
        return arrays;
    }

    public static byte[][] stringToPrimitiveByteArrays(String string) {
        String[] strs = string.split(";");
        byte[][] arrays = new byte[strs.length][];
        for (int i = 0; i < strs.length; ++i) {
            arrays[i] = stringToPrimitiveByteArray(strs[i]);
        }
        return arrays;
    }

    public static char[][] stringToPrimitiveCharArrays(String string) {
        String[] strs = string.split(";");
        char[][] arrays = new char[strs.length][];
        for (int i = 0; i < strs.length; ++i) {
            arrays[i] = stringToPrimitiveCharArray(strs[i]);
        }
        return arrays;
    }

    public static double[][] stringToPrimitiveDoubleArrays(String string) {
        String[] strs = string.split(";");
        double[][] arrays = new double[strs.length][];
        for (int i = 0; i < strs.length; ++i) {
            arrays[i] = stringToPrimitiveDoubleArray(strs[i]);
        }
        return arrays;
    }

    public static float[][] stringToPrimitiveFloatArrays(String string) {
        String[] strs = string.split(";");
        float[][] arrays = new float[strs.length][];
        for (int i = 0; i < strs.length; ++i) {
            arrays[i] = stringToPrimitiveFloatArray(strs[i]);
        }
        return arrays;
    }

    public static int[][] stringToPrimitiveIntArrays(String string) {
        String[] strs = string.split(";");
        int[][] arrays = new int[strs.length][];
        for (int i = 0; i < strs.length; ++i) {
            arrays[i] = stringToPrimitiveIntArray(strs[i]);
        }
        return arrays;
    }

    public static long[][] stringToPrimitiveLongArrays(String string) {
        String[] strs = string.split(";");
        long[][] arrays = new long[strs.length][];
        for (int i = 0; i < strs.length; ++i) {
            arrays[i] = stringToPrimitiveLongArray(strs[i]);
        }
        return arrays;
    }

    public static short[][] stringToPrimitiveShortArrays(String string) {
        String[] strs = string.split(";");
        short[][] arrays = new short[strs.length][];
        for (int i = 0; i < strs.length; ++i) {
            arrays[i] = stringToPrimitiveShortArray(strs[i]);
        }
        return arrays;
    }

    // ====================== two-dimensions wrapper array =================
    public static Boolean[][] stringToWrapperBoolArrays(String string) {
        return stringToArrays(string, Boolean::valueOf, Boolean[][]::new, Boolean[]::new);
    }

    public static Byte[][] stringToWrapperByteArrays(String string) {
        return stringToArrays(string, Byte::valueOf, Byte[][]::new, Byte[]::new);
    }

    public static Character[][] stringToWrapperCharArrays(String string) {
        String[] strs = string.split(";");
        Character[][] arrays = new Character[strs.length][];
        for (int i = 0; i < strs.length; ++i) {
            arrays[i] = stringToWrapperCharArray(strs[i]);
        }
        return arrays;
    }

    public static Double[][] stringToWrapperDoubleArrays(String string) {
        return stringToArrays(string, Double::valueOf, Double[][]::new, Double[]::new);
    }

    public static Float[][] stringToWrapperFloatArrays(String string) {
        return stringToArrays(string, Float::valueOf, Float[][]::new, Float[]::new);
    }

    public static Integer[][] stringToWrapperIntArrays(String string) {
        return stringToArrays(string, Integer::valueOf, Integer[][]::new, Integer[]::new);
    }

    public static Long[][] stringToWrapperLongArrays(String string) {
        return stringToArrays(string, Long::valueOf, Long[][]::new, Long[]::new);
    }

    public static Short[][] stringToWrapperShortArrays(String string) {
        return stringToArrays(string, Short::valueOf, Short[][]::new, Short[]::new);
    }

    public static String[][] stringToStringArrays(String string) {
        return stringToArrays(string, i -> i, String[][]::new, String[]::new);
    }

    public static <T> T[] stringToArray(
        String string, Function<String, T> converter, IntFunction<T[]> newer) {
        String[] strs = string.split(",");
        T[] array = newer.apply(strs.length);
        for (int i = 0; i < strs.length; ++i) {
            array[i] = converter.apply(strs[i]);
        }
        return array;
    }

    public static <T> T[][] stringToArrays(
        String string, Function<String, T> converter, IntFunction<T[][]> newer1, IntFunction<T[]> newer2) {
        String[] strs = string.split(";");
        T[][] arrays = newer1.apply(strs.length);
        for (int i = 0; i < strs.length; ++i) {
            arrays[i] = stringToArray(strs[i], converter, newer2);
        }
        return arrays;
    }

    public static char stringToChar(String string) {
        if (string.length() == 1) {
            return string.charAt(0);
        }
        throw new IllegalArgumentException("can't convert string " + string + " to char value");
    }
}
