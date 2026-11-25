package com.tvd12.ezyfox.codec;

import com.tvd12.ezyfox.exception.EzyCodecException;
import com.tvd12.ezyfox.exception.EzyMaxRequestSizeException;

import java.util.Arrays;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public final class EzyDeflateCompressor {

    private static final byte[] EMPTY_BYTES = new byte[0];
    private static final int MIN_BUFFER_SIZE = 64;
    private static final int MIN_DECOMPRESS_BUFFER_SIZE = 1024;
    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;
    private static final ThreadLocal<Deflater> DEFLATERS =
        ThreadLocal.withInitial(() -> new Deflater(Deflater.DEFAULT_COMPRESSION, true));
    private static final ThreadLocal<Inflater> INFLATERS =
        ThreadLocal.withInitial(() -> new Inflater(true));

    private EzyDeflateCompressor() {}

    public static byte[] compress(byte[] data) {
        return compress(data, Deflater.DEFAULT_COMPRESSION);
    }

    public static byte[] compress(byte[] data, int level) {
        Deflater deflater = DEFLATERS.get();
        try {
            deflater.setLevel(level);
            deflater.setInput(data);
            deflater.finish();
            byte[] output = new byte[Math.max(MIN_BUFFER_SIZE, data.length / 2)];
            int length = 0;
            while (!deflater.finished()) {
                if (length == output.length) {
                    output = grow(output, MAX_ARRAY_SIZE);
                }
                length += deflater.deflate(output, length, output.length - length);
            }
            return length == output.length
                ? output
                : Arrays.copyOf(output, length);
        } finally {
            deflater.reset();
            deflater.setInput(EMPTY_BYTES);
        }
    }

    public static byte[] decompress(byte[] data, int maxSize) {
        Inflater inflater = INFLATERS.get();
        try {
            inflater.setInput(data);
            int maxCapacity = maxSize > 0
                ? (int) Math.min((long) maxSize + 1, MAX_ARRAY_SIZE)
                : MAX_ARRAY_SIZE;
            int initialCapacity = (int) Math.min(
                Math.max(MIN_DECOMPRESS_BUFFER_SIZE, (long) data.length * 4),
                maxCapacity
            );
            byte[] output = new byte[initialCapacity];
            int length = 0;
            while (!inflater.finished()) {
                if (length == output.length) {
                    output = grow(output, maxCapacity);
                }
                int count = inflater.inflate(output, length, output.length - length);
                if (count == 0 && !inflater.finished()) {
                    throw new EzyCodecException("invalid or truncated compressed data");
                }
                length += count;
                if (maxSize > 0 && length > maxSize) {
                    throw new EzyMaxRequestSizeException(length, maxSize);
                }
            }
            return length == output.length
                ? output
                : Arrays.copyOf(output, length);
        } catch (DataFormatException e) {
            throw new EzyCodecException("invalid compressed data", e);
        } finally {
            inflater.reset();
            inflater.setInput(EMPTY_BYTES);
        }
    }

    private static byte[] grow(byte[] array, int maxCapacity) {
        if (array.length >= maxCapacity) {
            throw new EzyCodecException("data size exceeds max capacity: " + maxCapacity);
        }
        int newCapacity = (int) Math.min((long) array.length << 1, maxCapacity);
        return Arrays.copyOf(array, newCapacity);
    }
}
