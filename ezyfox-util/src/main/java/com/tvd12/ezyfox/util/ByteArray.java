package com.tvd12.ezyfox.util;

import lombok.Getter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ByteArray implements Serializable {
    private static final long serialVersionUID = 7607209931402134720L;

    @Getter
    protected final byte[] bytes;

    public ByteArray(String bytes) {
        this(bytes.getBytes());
    }

    public ByteArray(byte[] bytes) {
        if (bytes == null) {
            throw new NullPointerException("bytes can not be null");
        }
        this.bytes = bytes;
    }

    public static ByteArray wrap(String bytes) {
        return new ByteArray(bytes);
    }

    public static ByteArray wrap(byte[] bytes) {
        return new ByteArray(bytes);
    }

    public static List<ByteArray> wrap(byte[][] byteArrays) {
        List<ByteArray> list = new ArrayList<>(byteArrays.length);
        for (byte[] bytes : byteArrays) {
            list.add(wrap(bytes));
        }
        return list;
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (other == this) {
            return true;
        }
        if (!other.getClass().equals(this.getClass())) {
            return false;
        }
        ByteArray t = (ByteArray) other;
        return Arrays.equals(this.bytes, t.bytes);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(bytes);
    }

    @Override
    public String toString() {
        return new String(bytes);
    }
}
