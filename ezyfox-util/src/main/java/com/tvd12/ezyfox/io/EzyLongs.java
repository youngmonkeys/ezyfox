package com.tvd12.ezyfox.io;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public final class EzyLongs {

    private EzyLongs() {}

    public static long bin2long(int length) {
        return EzyMath.bin2long(length);
    }

    public static long bin2long(byte[] bytes) {
        return EzyMath.bin2long(bytes);
    }

    public static long bin2long(ByteBuffer buffer) {
        return bin2long(buffer, 8);
    }

    public static long bin2long(ByteBuffer buffer, int size) {
        if (size == 1) {
            return buffer.get();
        }
        if (buffer.order() == ByteOrder.BIG_ENDIAN) {
            if (size == 8) {
                return buffer.getLong();
            }
            if (size == 4) {
                return buffer.getInt();
            }
            if (size == 2) {
                return buffer.getShort();
            }
        }
        return readBigEndian(buffer, size, false);
    }

    public static long bin2ulong(byte[] bytes) {
        return EzyMath.bin2ulong(bytes);
    }

    public static long bin2ulong(ByteBuffer buffer) {
        return bin2ulong(buffer, 8);
    }

    public static long bin2ulong(ByteBuffer buffer, int size) {
        if (size == 1) {
            return buffer.get() & 0xFFL;
        }
        if (buffer.order() == ByteOrder.BIG_ENDIAN) {
            if (size == 8) {
                return buffer.getLong();
            }
            if (size == 4) {
                return buffer.getInt() & 0xFFFFFFFFL;
            }
            if (size == 2) {
                return buffer.getShort() & 0xFFFFL;
            }
        }
        return readBigEndian(buffer, size, true);
    }

    private static long readBigEndian(ByteBuffer buffer, int size, boolean unsigned) {
        if (size <= 0) {
            throw new IllegalArgumentException("size must be positive, but was: " + size);
        }
        long result = buffer.get();
        if (unsigned) {
            result &= 0xFFL;
        }
        for (int i = 1; i < size; ++i) {
            result = (result << 8) | (buffer.get() & 0xFFL);
        }
        return result;
    }
}
