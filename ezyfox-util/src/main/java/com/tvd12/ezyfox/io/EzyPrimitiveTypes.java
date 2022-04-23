package com.tvd12.ezyfox.io;

import java.util.ArrayList;
import java.util.List;

public final class EzyPrimitiveTypes {

    private EzyPrimitiveTypes() {
    }

    public static List<Boolean> toList(boolean[] array) {
        if (array == null) {
            return null;
        }
        List<Boolean> list = new ArrayList<>();
        for (boolean b : array) {
            list.add(b);
        }
        return list;
    }

    public static List<Byte> toList(byte[] array) {
        if (array == null) {
            return null;
        }
        List<Byte> list = new ArrayList<>();
        for (byte b : array) {
            list.add(b);
        }
        return list;
    }

    public static List<Character> toList(char[] array) {
        if (array == null) {
            return null;
        }
        List<Character> list = new ArrayList<>();
        for (char c : array) {
            list.add(c);
        }
        return list;
    }

    public static List<Double> toList(double[] array) {
        if (array == null) {
            return null;
        }
        List<Double> list = new ArrayList<>();
        for (double v : array) {
            list.add(v);
        }
        return list;
    }

    public static List<Float> toList(float[] array) {
        if (array == null) {
            return null;
        }
        List<Float> list = new ArrayList<>();
        for (float v : array) {
            list.add(v);
        }
        return list;
    }

    public static List<Integer> toList(int[] array) {
        if (array == null) {
            return null;
        }
        List<Integer> list = new ArrayList<>();
        for (int j : array) {
            list.add(j);
        }
        return list;
    }

    public static List<Long> toList(long[] array) {
        if (array == null) {
            return null;
        }
        List<Long> list = new ArrayList<>();
        for (long l : array) {
            list.add(l);
        }
        return list;
    }

    public static List<Short> toList(short[] array) {
        if (array == null) {
            return null;
        }
        List<Short> list = new ArrayList<>();
        for (short value : array) {
            list.add(value);
        }
        return list;
    }
}
