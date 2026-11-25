package com.tvd12.ezyfox.testing.performance;

import com.tvd12.ezyfox.codec.EzyDeflateCompressor;
import com.tvd12.test.assertion.Asserts;
import org.testng.annotations.Test;

import java.io.ByteArrayOutputStream;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Function;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class DeflateCompressorPerformanceTest {

    @Test
    public void smallTextTest() throws Exception {
        compare("text 256B", newTextData(256), 20000);
    }

    @Test
    public void mediumTextTest() throws Exception {
        compare("text 16KB", newTextData(16 * 1024), 2000);
    }

    @Test
    public void largeTextTest() throws Exception {
        compare("text 1MB", newTextData(1024 * 1024), 30);
    }

    @Test
    public void mediumRandomTest() throws Exception {
        compare("random 16KB", newRandomData(16 * 1024), 2000);
    }

    @Test
    public void largeRandomTest() throws Exception {
        compare("random 1MB", newRandomData(1024 * 1024), 20);
    }

    @Test
    public void multiThreadsTest() throws Exception {
        // given
        byte[] data = newTextData(16 * 1024);
        byte[] compressed = EzyDeflateCompressor.compress(data);
        int threadCount = Math.max(2, Runtime.getRuntime().availableProcessors());
        int loop = 2000;
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);

        try {
            // when
            runInThreads(executor, threadCount, 100, data, compressed);
            long start = System.nanoTime();
            runInThreads(executor, threadCount, loop, data, compressed);
            long nanos = System.nanoTime() - start;

            // then
            System.out.printf(
                "%-18s | %d threads x %d ops: %10.3f ms, %10.0f ops/s%n",
                "text 16KB parallel",
                threadCount,
                loop,
                nanos / 1_000_000.0,
                threadCount * loop / (nanos / 1_000_000_000.0)
            );
        } finally {
            executor.shutdown();
        }
    }

    private static void runInThreads(
        ExecutorService executor,
        int threadCount,
        int loop,
        byte[] data,
        byte[] compressed
    ) throws Exception {
        List<Future<Boolean>> futures = new ArrayList<>();
        for (int i = 0; i < threadCount; ++i) {
            Callable<Boolean> task = () -> {
                for (int k = 0; k < loop; ++k) {
                    EzyDeflateCompressor.compress(data);
                    byte[] decompressed = EzyDeflateCompressor.decompress(compressed, 0);
                    if (decompressed.length != data.length) {
                        return false;
                    }
                }
                return true;
            };
            futures.add(executor.submit(task));
        }
        for (Future<Boolean> future : futures) {
            Asserts.assertTrue(future.get());
        }
    }

    private static void compare(String name, byte[] data, int loop) {
        byte[] compressed = EzyDeflateCompressor.compress(data);
        Asserts.assertTrue(Arrays.equals(LegacyCompressor.decompress(compressed), data));
        Asserts.assertTrue(Arrays.equals(EzyDeflateCompressor.decompress(compressed, 0), data));

        run(LegacyCompressor::compress, data, loop / 10 + 1);
        run(EzyDeflateCompressor::compress, data, loop / 10 + 1);
        Result legacyCompress = run(LegacyCompressor::compress, data, loop);
        Result currentCompress = run(EzyDeflateCompressor::compress, data, loop);

        run(LegacyCompressor::decompress, compressed, loop / 10 + 1);
        run(bytes -> EzyDeflateCompressor.decompress(bytes, 0), compressed, loop / 10 + 1);
        Result legacyDecompress = run(LegacyCompressor::decompress, compressed, loop);
        Result currentDecompress = run(
            bytes -> EzyDeflateCompressor.decompress(bytes, 0),
            compressed,
            loop
        );

        System.out.printf(
            "%-12s | ratio %5.1f%% | compress   legacy: %9.4f ms/op %10s/op"
                + ", current: %9.4f ms/op %10s/op, x%.2f%n",
            name,
            compressed.length * 100.0 / data.length,
            legacyCompress.millisPerOp(loop),
            formatBytes(legacyCompress.allocatedBytes / loop),
            currentCompress.millisPerOp(loop),
            formatBytes(currentCompress.allocatedBytes / loop),
            (double) legacyCompress.nanos / Math.max(1, currentCompress.nanos)
        );
        System.out.printf(
            "%-12s |              | decompress legacy: %9.4f ms/op %10s/op"
                + ", current: %9.4f ms/op %10s/op, x%.2f%n",
            name,
            legacyDecompress.millisPerOp(loop),
            formatBytes(legacyDecompress.allocatedBytes / loop),
            currentDecompress.millisPerOp(loop),
            formatBytes(currentDecompress.allocatedBytes / loop),
            (double) legacyDecompress.nanos / Math.max(1, currentDecompress.nanos)
        );
    }

    private static Result run(Function<byte[], byte[]> function, byte[] input, int loop) {
        long allocatedBefore = allocatedBytes();
        long start = System.nanoTime();
        long total = 0;
        for (int i = 0; i < loop; ++i) {
            total += function.apply(input).length;
        }
        long nanos = System.nanoTime() - start;
        long allocated = allocatedBytes() - allocatedBefore;
        Asserts.assertTrue(total > 0);
        return new Result(nanos, allocated);
    }

    private static byte[] newTextData(int length) {
        StringBuilder builder = new StringBuilder();
        Random random = new Random(1);
        String[] words = {"ezyfox", "server", "message", "player", "room", "score", "{\"id\":", "\"name\":"};
        while (builder.length() < length) {
            builder.append(words[random.nextInt(words.length)])
                .append(random.nextInt(1000))
                .append(' ');
        }
        return builder.substring(0, length).getBytes(StandardCharsets.UTF_8);
    }

    private static byte[] newRandomData(int length) {
        byte[] data = new byte[length];
        new Random(1).nextBytes(data);
        return data;
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

    private static class Result {
        private final long nanos;
        private final long allocatedBytes;

        Result(long nanos, long allocatedBytes) {
            this.nanos = nanos;
            this.allocatedBytes = allocatedBytes;
        }

        double millisPerOp(int loop) {
            return nanos / 1_000_000.0 / loop;
        }
    }

    private static final class LegacyCompressor {

        private LegacyCompressor() {}

        static byte[] compress(byte[] data) {
            Deflater deflater = new Deflater(Deflater.DEFAULT_COMPRESSION, true);
            try {
                deflater.setInput(data);
                deflater.finish();
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                while (!deflater.finished()) {
                    int count = deflater.deflate(buffer);
                    out.write(buffer, 0, count);
                }
                return out.toByteArray();
            } finally {
                deflater.end();
            }
        }

        static byte[] decompress(byte[] data) {
            Inflater inflater = new Inflater(true);
            try {
                inflater.setInput(data);
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                while (!inflater.finished()) {
                    int count = inflater.inflate(buffer);
                    if (count == 0 && inflater.needsInput()) {
                        throw new IllegalStateException("truncated data");
                    }
                    out.write(buffer, 0, count);
                }
                return out.toByteArray();
            } catch (Exception e) {
                throw new IllegalStateException(e);
            } finally {
                inflater.end();
            }
        }
    }
}
