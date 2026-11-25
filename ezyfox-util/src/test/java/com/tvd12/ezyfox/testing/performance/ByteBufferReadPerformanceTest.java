package com.tvd12.ezyfox.testing.performance;

import com.tvd12.ezyfox.io.EzyBytes;
import com.tvd12.ezyfox.io.EzyLongs;
import com.tvd12.ezyfox.io.EzyStrings;
import com.tvd12.test.assertion.Asserts;
import org.testng.annotations.Test;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.function.ToLongFunction;

public class ByteBufferReadPerformanceTest {

    private static final int NUMBER_COUNT = 100000;
    private static final int LOOP = 100;

    @Test
    public void bin2ulongTest() {
        for (int size : new int[] {1, 2, 3, 4, 8}) {
            ByteBuffer buffer = newNumberBuffer(size, false);
            compare(
                "bin2ulong size " + size,
                buffer,
                b -> sumUlongs(b, size, false),
                b -> sumUlongs(b, size, true)
            );
        }
    }

    @Test
    public void bin2longTest() {
        for (int size : new int[] {2, 4, 8}) {
            ByteBuffer buffer = newNumberBuffer(size, false);
            compare(
                "bin2long size " + size,
                buffer,
                b -> sumLongs(b, size, false),
                b -> sumLongs(b, size, true)
            );
        }
    }

    @Test
    public void bin2ulongDirectBufferTest() {
        ByteBuffer buffer = newNumberBuffer(4, true);
        compare(
            "bin2ulong direct 4",
            buffer,
            b -> sumUlongs(b, 4, false),
            b -> sumUlongs(b, 4, true)
        );
    }

    @Test
    public void newUtfAsciiTest() {
        compareStrings("newUtf ascii 32B", newStringBuffer("player_name_", 32, false));
        compareStrings("newUtf ascii 1KB", newStringBuffer("player_name_", 1024, false));
    }

    @Test
    public void newUtfUnicodeTest() {
        compareStrings("newUtf unicode 32B", newStringBuffer("người chơi ", 32, false));
        compareStrings("newUtf unicode 1KB", newStringBuffer("người chơi ", 1024, false));
    }

    @Test
    public void newUtfDirectBufferTest() {
        compareStrings("newUtf direct 1KB", newStringBuffer("player_name_", 1024, true));
    }

    private static void compareStrings(String name, StringBuffers buffers) {
        compare(
            name,
            buffers.buffer,
            b -> sumStringLengths(b, buffers.size, false),
            b -> sumStringLengths(b, buffers.size, true)
        );
    }

    private static void compare(
        String name,
        ByteBuffer buffer,
        ToLongFunction<ByteBuffer> legacy,
        ToLongFunction<ByteBuffer> current
    ) {
        Asserts.assertEquals(current.applyAsLong(buffer.duplicate()), legacy.applyAsLong(buffer.duplicate()));

        run(legacy, buffer, LOOP / 10);
        run(current, buffer, LOOP / 10);
        Result legacyResult = run(legacy, buffer, LOOP);
        Result currentResult = run(current, buffer, LOOP);

        System.out.printf(
            "%-20s | legacy: %9.3f ms/op %10s/op | current: %9.3f ms/op %10s/op | x%.2f%n",
            name,
            legacyResult.millisPerOp(),
            formatBytes(legacyResult.allocatedBytes / LOOP),
            currentResult.millisPerOp(),
            formatBytes(currentResult.allocatedBytes / LOOP),
            (double) legacyResult.nanos / Math.max(1, currentResult.nanos)
        );
    }

    private static Result run(ToLongFunction<ByteBuffer> function, ByteBuffer buffer, int loop) {
        long allocatedBefore = allocatedBytes();
        long start = System.nanoTime();
        long total = 0;
        for (int i = 0; i < loop; ++i) {
            total += function.applyAsLong(buffer.duplicate());
        }
        long nanos = System.nanoTime() - start;
        long allocated = allocatedBytes() - allocatedBefore;
        Asserts.assertTrue(total != Long.MIN_VALUE);
        return new Result(nanos / Math.max(1, loop), allocated);
    }

    private static long sumUlongs(ByteBuffer buffer, int size, boolean current) {
        long sum = 0;
        while (buffer.hasRemaining()) {
            sum += current
                ? EzyLongs.bin2ulong(buffer, size)
                : EzyLongs.bin2ulong(EzyBytes.copy(buffer, size));
        }
        return sum;
    }

    private static long sumLongs(ByteBuffer buffer, int size, boolean current) {
        long sum = 0;
        while (buffer.hasRemaining()) {
            sum += current
                ? EzyLongs.bin2long(buffer, size)
                : EzyLongs.bin2long(EzyBytes.copy(buffer, size));
        }
        return sum;
    }

    private static long sumStringLengths(ByteBuffer buffer, int size, boolean current) {
        long sum = 0;
        while (buffer.hasRemaining()) {
            String value;
            if (current) {
                value = EzyStrings.newUtf(buffer, size);
            } else {
                byte[] bytes = new byte[size];
                buffer.get(bytes);
                value = EzyStrings.newString(bytes, EzyStrings.UTF_8);
            }
            sum += value.length() + value.charAt(value.length() - 1);
        }
        return sum;
    }

    private static ByteBuffer newNumberBuffer(int size, boolean direct) {
        byte[] bytes = new byte[size * NUMBER_COUNT];
        new Random(1).nextBytes(bytes);
        return wrap(bytes, direct);
    }

    private static StringBuffers newStringBuffer(String text, int minSize, boolean direct) {
        StringBuilder builder = new StringBuilder();
        while (builder.toString().getBytes(StandardCharsets.UTF_8).length < minSize) {
            builder.append(text);
        }
        byte[] one = builder.toString().getBytes(StandardCharsets.UTF_8);
        int count = Math.max(1, 1024 * 1024 / one.length);
        byte[] bytes = new byte[one.length * count];
        for (int i = 0; i < count; ++i) {
            System.arraycopy(one, 0, bytes, i * one.length, one.length);
        }
        return new StringBuffers(wrap(bytes, direct), one.length);
    }

    private static ByteBuffer wrap(byte[] bytes, boolean direct) {
        if (!direct) {
            return ByteBuffer.wrap(bytes);
        }
        ByteBuffer buffer = ByteBuffer.allocateDirect(bytes.length);
        buffer.put(bytes);
        buffer.flip();
        return buffer;
    }

    private static long allocatedBytes() {
        ThreadMXBean bean = ManagementFactory.getThreadMXBean();
        if (bean instanceof com.sun.management.ThreadMXBean) {
            return ((com.sun.management.ThreadMXBean) bean)
                .getThreadAllocatedBytes(Thread.currentThread().getId());
        }
        return 0;
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

    private static class StringBuffers {
        private final ByteBuffer buffer;
        private final int size;

        StringBuffers(ByteBuffer buffer, int size) {
            this.buffer = buffer;
            this.size = size;
        }
    }

    private static class Result {
        private final long nanos;
        private final long allocatedBytes;

        Result(long nanos, long allocatedBytes) {
            this.nanos = nanos;
            this.allocatedBytes = allocatedBytes;
        }

        double millisPerOp() {
            return nanos / 1_000_000.0;
        }
    }
}
