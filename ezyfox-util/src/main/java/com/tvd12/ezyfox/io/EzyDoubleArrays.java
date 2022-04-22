package com.tvd12.ezyfox.io;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;

public final class EzyDoubleArrays {

    private EzyDoubleArrays() {
    }

    public static byte[] toByteArray(double[] doubleArray) {
        ByteBuffer buf = ByteBuffer.allocate((Double.SIZE / Byte.SIZE) * doubleArray.length);
        buf.asDoubleBuffer().put(doubleArray);
        byte[] array = buf.array();
        return array;
    }

    public static double[] toDoubleArray(byte[] bytes) {
        return toDoubleArray(bytes, 0);
    }

    public static double[] toDoubleArray(byte[] bytes, int offset) {
        return toDoubleArray(bytes, offset, bytes.length - offset);
    }

    public static double[] toDoubleArray(byte[] bytes, int offset, int length) {
        DoubleBuffer buf = ByteBuffer.wrap(bytes, offset, length).asDoubleBuffer();
        double[] doubleArray = new double[buf.limit()];
        buf.get(doubleArray);
        return doubleArray;
    }
}