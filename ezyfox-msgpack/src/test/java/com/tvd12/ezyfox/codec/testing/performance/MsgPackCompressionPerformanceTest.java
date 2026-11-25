package com.tvd12.ezyfox.codec.testing.performance;

import com.tvd12.ezyfox.codec.EzyByteToObjectDecoder;
import com.tvd12.ezyfox.codec.EzyMessage;
import com.tvd12.ezyfox.codec.EzyObjectToByteEncoder;
import com.tvd12.ezyfox.codec.MsgPackCodecCreator;
import com.tvd12.ezyfox.entity.EzyArray;
import com.tvd12.ezyfox.entity.EzyObject;
import com.tvd12.ezyfox.factory.EzyEntityFactory;
import com.tvd12.ezyfox.security.EzyAesCrypt;
import com.tvd12.test.assertion.Asserts;
import org.testng.annotations.Test;

import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class MsgPackCompressionPerformanceTest {

    private static final int MAX_SIZE = 16 * 1024 * 1024;

    @Test
    public void smallMessageTest() throws Exception {
        compare("small message", newObjects(1), 5000, false);
    }

    @Test
    public void mediumMessageTest() throws Exception {
        compare("100 objects", newObjects(100), 500, false);
    }

    @Test
    public void largeMessageTest() throws Exception {
        compare("5000 objects", newObjects(5000), 10, false);
    }

    @Test
    public void randomBinaryMessageTest() throws Exception {
        byte[] bin = new byte[64 * 1024];
        new Random(1).nextBytes(bin);
        EzyArray array = EzyEntityFactory.newArray();
        array.add("bin", bin);
        compare("random 64KB", array, 200, false);
    }

    @Test
    public void mediumMessageWithEncryptionTest() throws Exception {
        compare("100 objects + aes", newObjects(100), 500, true);
    }

    private static void compare(
        String name,
        Object data,
        int loop,
        boolean encryption
    ) throws Exception {
        MsgPackCodecCreator codecCreator = new MsgPackCodecCreator(encryption);
        EzyObjectToByteEncoder encoder = codecCreator.newEncoder();
        EzyByteToObjectDecoder decoder = codecCreator.newDecoder(MAX_SIZE);
        byte[] key = encryption ? EzyAesCrypt.randomKey() : null;

        byte[] plainBytes = encode(encoder, data, key, false);
        byte[] compressedBytes = encode(encoder, data, key, true);
        Asserts.assertNotNull(decode(decoder, plainBytes, key));
        Asserts.assertNotNull(decode(decoder, compressedBytes, key));

        for (int i = 0; i < loop / 10 + 1; ++i) {
            decode(decoder, encode(encoder, data, key, false), key);
            decode(decoder, encode(encoder, data, key, true), key);
        }

        long plainEncode = measureEncode(encoder, data, key, false, loop);
        long compressedEncode = measureEncode(encoder, data, key, true, loop);
        long plainDecode = measureDecode(decoder, plainBytes, key, loop);
        long compressedDecode = measureDecode(decoder, compressedBytes, key, loop);

        System.out.printf(
            "%-18s | size: %10s -> %10s (%5.1f%%) | encode: %9.4f -> %9.4f ms/op"
                + " | decode: %9.4f -> %9.4f ms/op%n",
            name,
            formatBytes(plainBytes.length),
            formatBytes(compressedBytes.length),
            compressedBytes.length * 100.0 / plainBytes.length,
            plainEncode / 1_000_000.0 / loop,
            compressedEncode / 1_000_000.0 / loop,
            plainDecode / 1_000_000.0 / loop,
            compressedDecode / 1_000_000.0 / loop
        );
    }

    private static long measureEncode(
        EzyObjectToByteEncoder encoder,
        Object data,
        byte[] key,
        boolean compressed,
        int loop
    ) throws Exception {
        long total = 0;
        long start = System.nanoTime();
        for (int i = 0; i < loop; ++i) {
            total += encode(encoder, data, key, compressed).length;
        }
        long nanos = System.nanoTime() - start;
        Asserts.assertTrue(total > 0);
        return nanos;
    }

    private static long measureDecode(
        EzyByteToObjectDecoder decoder,
        byte[] bytes,
        byte[] key,
        int loop
    ) throws Exception {
        int count = 0;
        long start = System.nanoTime();
        for (int i = 0; i < loop; ++i) {
            if (decode(decoder, bytes, key) != null) {
                ++count;
            }
        }
        long nanos = System.nanoTime() - start;
        Asserts.assertEquals(count, loop);
        return nanos;
    }

    private static byte[] encode(
        EzyObjectToByteEncoder encoder,
        Object data,
        byte[] key,
        boolean compressed
    ) throws Exception {
        byte[] content = encoder.toMessageContent(data);
        if (compressed) {
            content = encoder.compressMessageContent(content);
        }
        return encoder.encryptMessageContent(content, key, compressed);
    }

    private static Object decode(
        EzyByteToObjectDecoder decoder,
        byte[] bytes,
        byte[] key
    ) throws Exception {
        Queue<EzyMessage> queue = new LinkedList<>();
        decoder.decode(ByteBuffer.wrap(bytes), queue);
        EzyMessage message = queue.poll();
        return key != null
            ? decoder.decode(message, key)
            : decoder.decode(message);
    }

    private static EzyArray newObjects(int count) {
        EzyArray array = EzyEntityFactory.newArray();
        for (int i = 0; i < count; ++i) {
            EzyObject object = EzyEntityFactory.newObject();
            object.put("id", (long) i);
            object.put("name", "player " + i);
            object.put("level", i % 100);
            object.put("score", i * 10.5D);
            object.put("status", i % 3 == 0 ? "online" : "offline");
            array.add(object);
        }
        return array;
    }

    private static String formatBytes(long bytes) {
        if (bytes >= 1024L * 1024L) {
            return String.format("%.1f MB", bytes / (1024.0 * 1024.0));
        }
        if (bytes >= 1024L) {
            return String.format("%.1f KB", bytes / 1024.0);
        }
        return bytes + " B";
    }
}
