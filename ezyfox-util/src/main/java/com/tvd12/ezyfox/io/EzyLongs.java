package com.tvd12.ezyfox.io;

import java.nio.ByteBuffer;

public final class EzyLongs {

    private EzyLongs() {
    }

    public static long bin2long(int length) {
        return EzyMath.bin2long(length);
    }

    public static long bin2long(byte[] bytes) {
        return EzyMath.bin2long(bytes);
    }

    public static long bin2ulong(byte[] bytes) {
        return EzyMath.bin2ulong(bytes);
    }

    public static long bin2long(ByteBuffer buffer) {
        return bin2long(buffer, 8);
    }

    public static long bin2ulong(ByteBuffer buffer) {
        return bin2ulong(buffer, 8);
    }

    public static long bin2long(ByteBuffer buffer, int size) {
        if (size == 8) {
            return buffer.getLong();
        }
        if (size == 4) {
            return buffer.getInt();
        }
        if (size == 2) {
            return buffer.getShort();
        }
        if (size == 1) {
            return buffer.get();
        }
        return bin2long(EzyBytes.copy(buffer, size));
    }

    public static long bin2ulong(ByteBuffer buffer, int size) {
        return bin2ulong(EzyBytes.copy(buffer, size));
    }
}
