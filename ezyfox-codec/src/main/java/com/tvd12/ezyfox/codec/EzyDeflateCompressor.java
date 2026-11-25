package com.tvd12.ezyfox.codec;

import com.tvd12.ezyfox.exception.EzyCodecException;
import com.tvd12.ezyfox.exception.EzyMaxRequestSizeException;

import java.io.ByteArrayOutputStream;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public final class EzyDeflateCompressor {

    private static final int BUFFER_SIZE = 1024;

    private EzyDeflateCompressor() {}

    public static byte[] compress(byte[] data) {
        return compress(data, Deflater.DEFAULT_COMPRESSION);
    }

    public static byte[] compress(byte[] data, int level) {
        Deflater deflater = new Deflater(level, true);
        try {
            deflater.setInput(data);
            deflater.finish();
            ByteArrayOutputStream output = new ByteArrayOutputStream(
                Math.max(BUFFER_SIZE, data.length / 2)
            );
            byte[] buffer = new byte[BUFFER_SIZE];
            while (!deflater.finished()) {
                int count = deflater.deflate(buffer);
                output.write(buffer, 0, count);
            }
            return output.toByteArray();
        } finally {
            deflater.end();
        }
    }

    public static byte[] decompress(byte[] data, int maxSize) {
        Inflater inflater = new Inflater(true);
        try {
            inflater.setInput(data);
            ByteArrayOutputStream output = new ByteArrayOutputStream(
                Math.max(BUFFER_SIZE, data.length * 2)
            );
            byte[] buffer = new byte[BUFFER_SIZE];
            int totalSize = 0;
            while (!inflater.finished()) {
                int count = inflater.inflate(buffer);
                if (count == 0
                    && !inflater.finished()
                    && (inflater.needsInput() || inflater.needsDictionary())
                ) {
                    throw new EzyCodecException("invalid or truncated compressed data");
                }
                totalSize += count;
                if (maxSize > 0 && totalSize > maxSize) {
                    throw new EzyMaxRequestSizeException(totalSize, maxSize);
                }
                output.write(buffer, 0, count);
            }
            return output.toByteArray();
        } catch (DataFormatException e) {
            throw new EzyCodecException("invalid compressed data", e);
        } finally {
            inflater.end();
        }
    }
}
