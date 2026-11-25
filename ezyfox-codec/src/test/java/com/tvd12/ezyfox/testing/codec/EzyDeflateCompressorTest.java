package com.tvd12.ezyfox.testing.codec;

import com.tvd12.ezyfox.codec.EzyDeflateCompressor;
import com.tvd12.ezyfox.exception.EzyCodecException;
import com.tvd12.ezyfox.exception.EzyMaxRequestSizeException;
import com.tvd12.test.assertion.Asserts;
import org.testng.annotations.Test;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Random;

public class EzyDeflateCompressorTest {

    @Test
    public void compressAndDecompressTest() {
        // given
        byte[] data = newRepeatedData(10000);

        // when
        byte[] compressed = EzyDeflateCompressor.compress(data);
        byte[] decompressed = EzyDeflateCompressor.decompress(compressed, 0);

        // then
        Asserts.assertTrue(compressed.length < data.length);
        Asserts.assertTrue(Arrays.equals(data, decompressed));
    }

    @Test
    public void compressAndDecompressRandomDataTest() {
        // given
        byte[] data = new byte[5000];
        new Random().nextBytes(data);

        // when
        byte[] compressed = EzyDeflateCompressor.compress(data, 9);
        byte[] decompressed = EzyDeflateCompressor.decompress(compressed, 5000);

        // then
        Asserts.assertTrue(Arrays.equals(data, decompressed));
    }

    @Test
    public void compressAndDecompressEmptyDataTest() {
        // given
        byte[] data = new byte[0];

        // when
        byte[] compressed = EzyDeflateCompressor.compress(data);
        byte[] decompressed = EzyDeflateCompressor.decompress(compressed, 10);

        // then
        Asserts.assertEquals(decompressed.length, 0);
    }

    @Test
    public void decompressOverMaxSizeTest() {
        // given
        byte[] compressed = EzyDeflateCompressor.compress(newRepeatedData(100000));

        // when
        Throwable e = Asserts.assertThrows(() ->
            EzyDeflateCompressor.decompress(compressed, 1024)
        );

        // then
        Asserts.assertEqualsType(e, EzyMaxRequestSizeException.class);
    }

    @Test
    public void decompressInvalidDataTest() {
        // given
        byte[] invalid = new byte[] {(byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF};

        // when
        Throwable e = Asserts.assertThrows(() ->
            EzyDeflateCompressor.decompress(invalid, 1024)
        );

        // then
        Asserts.assertEqualsType(e, EzyCodecException.class);
    }

    @Test
    public void decompressTruncatedDataTest() {
        // given
        byte[] compressed = EzyDeflateCompressor.compress(newRepeatedData(10000));
        byte[] truncated = Arrays.copyOf(compressed, compressed.length / 2);

        // when
        Throwable e = Asserts.assertThrows(() ->
            EzyDeflateCompressor.decompress(truncated, 0)
        );

        // then
        Asserts.assertEqualsType(e, EzyCodecException.class);
    }

    @Test
    public void reuseAfterFailureTest() {
        // given
        byte[] data = newRepeatedData(10000);
        byte[] compressed = EzyDeflateCompressor.compress(data);
        byte[] truncated = Arrays.copyOf(compressed, compressed.length / 2);
        Asserts.assertThrows(() -> EzyDeflateCompressor.decompress(truncated, 0));
        Asserts.assertThrows(() -> EzyDeflateCompressor.decompress(compressed, 1024));

        // when
        byte[] decompressed = EzyDeflateCompressor.decompress(compressed, 0);

        // then
        Asserts.assertTrue(Arrays.equals(data, decompressed));
    }

    @Test
    public void compressWithDifferentLevelsTest() {
        // given
        byte[] data = newRepeatedData(20000);

        // when
        byte[] compressed9 = EzyDeflateCompressor.compress(data, 9);
        byte[] compressed0 = EzyDeflateCompressor.compress(data, 0);
        byte[] compressedDefault = EzyDeflateCompressor.compress(data);

        // then
        Asserts.assertTrue(compressed0.length > data.length);
        Asserts.assertTrue(compressed9.length < data.length);
        Asserts.assertTrue(Arrays.equals(data, EzyDeflateCompressor.decompress(compressed9, 0)));
        Asserts.assertTrue(Arrays.equals(data, EzyDeflateCompressor.decompress(compressed0, 0)));
        Asserts.assertTrue(Arrays.equals(data, EzyDeflateCompressor.decompress(compressedDefault, 0)));
    }

    @Test
    public void decompressExactlyMaxSizeTest() {
        // given
        byte[] data = newRepeatedData(5000);
        byte[] compressed = EzyDeflateCompressor.compress(data);

        // when
        byte[] decompressed = EzyDeflateCompressor.decompress(compressed, data.length);

        // then
        Asserts.assertTrue(Arrays.equals(data, decompressed));
    }

    @Test
    public void compressRandomDataTest() {
        // given
        byte[] data = new byte[50000];
        new Random(1).nextBytes(data);

        // when
        byte[] compressed = EzyDeflateCompressor.compress(data);
        byte[] decompressed = EzyDeflateCompressor.decompress(compressed, 0);

        // then
        Asserts.assertTrue(Arrays.equals(data, decompressed));
    }

    private static byte[] newRepeatedData(int length) {
        StringBuilder builder = new StringBuilder();
        while (builder.length() < length) {
            builder.append("hello world ezyfox compression ");
        }
        return builder.substring(0, length).getBytes(StandardCharsets.UTF_8);
    }
}
