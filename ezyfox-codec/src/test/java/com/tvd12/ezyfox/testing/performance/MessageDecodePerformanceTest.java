package com.tvd12.ezyfox.testing.performance;

import com.tvd12.ezyfox.codec.EzyByteBufferMessageReader;
import com.tvd12.ezyfox.codec.EzyByteToObjectDecoder;
import com.tvd12.ezyfox.codec.EzyDecodeHandlers;
import com.tvd12.ezyfox.codec.EzyDefaultDecodeHandlers;
import com.tvd12.ezyfox.codec.EzyMessage;
import com.tvd12.ezyfox.codec.EzySimpleMessageDataDecoder;
import com.tvd12.test.assertion.Asserts;
import org.testng.annotations.Test;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

public class MessageDecodePerformanceTest {

    private static final int MAX_SIZE = 64 * 1024 * 1024;
    private static final int CHUNK_SIZE = 8 * 1024;

    @Test
    public void smallMessagesInOneChunkTest() throws Exception {
        compare("1000 x 256B, 1 chunk", 1000, 256, Integer.MAX_VALUE, 200);
    }

    @Test
    public void smallMessagesByChunksTest() throws Exception {
        compare("1000 x 256B, 8KB chunks", 1000, 256, CHUNK_SIZE, 200);
    }

    @Test
    public void mediumMessageByChunksTest() throws Exception {
        compare("1 x 64KB, 8KB chunks", 1, 64 * 1024, CHUNK_SIZE, 2000);
    }

    @Test
    public void largeMessageByChunksTest() throws Exception {
        compare("1 x 1MB, 8KB chunks", 1, 1024 * 1024, CHUNK_SIZE, 20);
    }

    @Test
    public void hugeMessageByChunksTest() throws Exception {
        compare("1 x 4MB, 8KB chunks", 1, 4 * 1024 * 1024, CHUNK_SIZE, 3);
    }

    private void compare(
        String name,
        int messageCount,
        int messageSize,
        int chunkSize,
        int loop
    ) throws Exception {
        List<byte[]> chunks = newChunks(messageCount, messageSize, chunkSize);

        run(MessageDecodePerformanceTest::newLegacyDecoder, chunks, messageCount, messageSize, 1);
        run(MessageDecodePerformanceTest::newDecoder, chunks, messageCount, messageSize, 1);

        Result legacy = run(
            MessageDecodePerformanceTest::newLegacyDecoder,
            chunks,
            messageCount,
            messageSize,
            loop
        );
        Result current = run(
            MessageDecodePerformanceTest::newDecoder,
            chunks,
            messageCount,
            messageSize,
            loop
        );

        System.out.printf(
            "%-26s | legacy: %10.3f ms/op, %12s/op | current: %10.3f ms/op, %12s/op | x%.1f faster%n",
            name,
            legacy.millisPerOp(loop),
            formatBytes(legacy.allocatedBytes / loop),
            current.millisPerOp(loop),
            formatBytes(current.allocatedBytes / loop),
            (double) legacy.nanos / Math.max(1, current.nanos)
        );
    }

    private static Result run(
        Supplier<EzySimpleMessageDataDecoder> decoderSupplier,
        List<byte[]> chunks,
        int messageCount,
        int messageSize,
        int loop
    ) throws Exception {
        long allocatedBefore = allocatedBytes();
        long start = System.nanoTime();
        for (int i = 0; i < loop; ++i) {
            EzySimpleMessageDataDecoder decoder = decoderSupplier.get();
            AtomicInteger count = new AtomicInteger();
            for (byte[] chunk : chunks) {
                decoder.decode(chunk, message -> {
                    if (message.getSize() == messageSize) {
                        count.incrementAndGet();
                    }
                });
            }
            Asserts.assertEquals(count.get(), messageCount);
        }
        long nanos = System.nanoTime() - start;
        long allocated = allocatedBytes() - allocatedBefore;
        return new Result(nanos, allocated);
    }

    private static List<byte[]> newChunks(
        int messageCount,
        int messageSize,
        int chunkSize
    ) {
        int frameSize = 5 + messageSize;
        ByteBuffer buffer = ByteBuffer.allocate(frameSize * messageCount);
        byte[] body = new byte[messageSize];
        for (int i = 0; i < messageSize; ++i) {
            body[i] = (byte) i;
        }
        for (int i = 0; i < messageCount; ++i) {
            buffer.put((byte) 1);
            buffer.putInt(messageSize);
            buffer.put(body);
        }
        byte[] bytes = buffer.array();
        List<byte[]> chunks = new ArrayList<>();
        for (int offset = 0; offset < bytes.length; offset += chunkSize) {
            int end = (int) Math.min((long) offset + chunkSize, bytes.length);
            chunks.add(Arrays.copyOfRange(bytes, offset, end));
        }
        return chunks;
    }

    private static EzySimpleMessageDataDecoder newDecoder() {
        return new EzySimpleMessageDataDecoder(
            new ByteToObjectDecoder(
                EzyDefaultDecodeHandlers.builder()
                    .maxSize(MAX_SIZE)
                    .build()
            )
        );
    }

    private static EzySimpleMessageDataDecoder newLegacyDecoder() {
        return new EzySimpleMessageDataDecoder(
            new ByteToObjectDecoder(
                new LegacyDecodeHandlersBuilder()
                    .maxSize(MAX_SIZE)
                    .build()
            )
        );
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

    private static class ByteToObjectDecoder implements EzyByteToObjectDecoder {

        private final EzyDecodeHandlers handlers;

        ByteToObjectDecoder(EzyDecodeHandlers handlers) {
            this.handlers = handlers;
        }

        @Override
        public Object decode(EzyMessage message) {
            return message;
        }

        @Override
        public void decode(ByteBuffer bytes, Queue<EzyMessage> queue) {
            handlers.handle(bytes, queue);
        }

        @Override
        public void reset() {
            handlers.reset();
        }
    }

    private static class LegacyDecodeHandlersBuilder extends EzyDefaultDecodeHandlers.Builder {

        LegacyDecodeHandlersBuilder() {
            this.messageReader = new LegacyMessageReader();
        }
    }

    private static class LegacyMessageReader extends EzyByteBufferMessageReader {

        @Override
        public boolean readContent(ByteBuffer buffer) {
            int remaining = remaining(buffer);
            if (remaining < size) {
                return false;
            }
            if (isRawBytes()) {
                int offset = getHeaderLength() + sizeBytes.length;
                this.content = new byte[size + offset];
                this.content[0] = headerByte;
                System.arraycopy(sizeBytes, 0, content, 1, sizeBytes.length);
                readMessageContent(buffer, content, offset, size);
            } else {
                this.content = new byte[size];
                readMessageContent(buffer, content, 0, size);
            }
            return true;
        }
    }
}
