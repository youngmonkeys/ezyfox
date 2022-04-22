package com.tvd12.ezyfox.io;

import java.util.ArrayList;
import java.util.List;

public final class EzyPrimitiveTypes {

    private EzyPrimitiveTypes() {
    }

    public static List<Boolean> toList(boolean[] array) {
        if (array == null)
            return null;
        List<Boolean> list = new ArrayList<>();
        for (int i = 0; i < array.length; ++i)
            list.add(array[i]);
        return list;
    }

    public static List<Byte> toList(byte[] array) {
        if (array == null)
            return null;
        List<Byte> list = new ArrayList<>();
        for (int i = 0; i < array.length; ++i)
            list.add(array[i]);
        return list;
    }

    public static List<Character> toList(char[] array) {
        if (array == null)
            return null;
        List<Character> list = new ArrayList<>();
        for (int i = 0; i < array.length; ++i)
            list.add(array[i]);
        return list;
    }

    public static List<Double> toList(double[] array) {
        if (array == null)
            return null;
        List<Double> list = new ArrayList<>();
        for (int i = 0; i < array.length; ++i)
            list.add(array[i]);
        return list;
    }

    public static List<Float> toList(float[] array) {
        if (array == null)
            return null;
        List<Float> list = new ArrayList<>();
        for (int i = 0; i < array.length; ++i)
            list.add(array[i]);
        return list;
    }

    public static List<Integer> toList(int[] array) {
        if (array == null)
            return null;
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < array.length; ++i)
            list.add(array[i]);
        return list;
    }

    public static List<Long> toList(long[] array) {
        if (array == null)
            return null;
        List<Long> list = new ArrayList<>();
        for (int i = 0; i < array.length; ++i)
            list.add(array[i]);
        return list;
    }

    public static List<Short> toList(short[] array) {
        if (array == null)
            return null;
        List<Short> list = new ArrayList<>();
        for (int i = 0; i < array.length; ++i)
            list.add(array[i]);
        return list;
    }

}
