package com.tvd12.ezyfox.codec.testing.performance;

import com.tvd12.ezyfox.codec.EzyMessageDeserializer;
import com.tvd12.ezyfox.codec.EzyMessageSerializer;
import com.tvd12.ezyfox.codec.MsgPackSimpleDeserializer;
import com.tvd12.ezyfox.codec.MsgPackSimpleSerializer;
import com.tvd12.ezyfox.codec.MsgPackToByteBufferSerializer;
import com.tvd12.ezyfox.entity.EzyArray;
import com.tvd12.ezyfox.entity.EzyObject;
import com.tvd12.ezyfox.factory.EzyEntityFactory;
import com.tvd12.test.assertion.Asserts;
import org.testng.annotations.Test;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.Random;
import java.util.function.Supplier;

public class MsgPackSerializePerformanceTest {

    private final EzyMessageSerializer serializer = new MsgPackSimpleSerializer();
    private final EzyMessageSerializer byteBufferSerializer = new MsgPackToByteBufferSerializer();
    private final EzyMessageDeserializer deserializer = new MsgPackSimpleDeserializer();

    @Test
    public void smallObjectTest() {
        compare("small object", newSmallObject(1), 20000);
    }

    @Test
    public void objectListTest() {
        EzyArray array = EzyEntityFactory.newArray();
        for (int i = 0; i < 1000; ++i) {
            array.add(newSmallObject(i));
        }
        compare("1000 objects", array, 50);
    }

    @Test
    public void numberListTest() {
        Random random = new Random(1);
        EzyArray array = EzyEntityFactory.newArray();
        for (int i = 0; i < 10000; ++i) {
            array.add(random.nextInt(), random.nextLong(), random.nextInt(256) - 128, random.nextDouble());
        }
        compare("40000 numbers", array, 30);
    }

    @Test
    public void asciiStringsTest() {
        EzyObject object = EzyEntityFactory.newObject();
        for (int i = 0; i < 200; ++i) {
            object.put("key" + i, newString('a', i * 10));
        }
        compare("ascii strings", object, 300);
    }

    @Test
    public void unicodeStringsTest() {
        EzyObject object = EzyEntityFactory.newObject();
        for (int i = 0; i < 200; ++i) {
            object.put("khóa" + i, newString('ệ', i * 10));
        }
        compare("unicode strings", object, 300);
    }

    @Test
    public void largeBinaryTest() {
        byte[] bin = new byte[256 * 1024];
        new Random(1).nextBytes(bin);
        EzyObject object = EzyEntityFactory.newObject();
        object.put("name", "file.bin");
        object.put("data", bin);
        compare("binary 256KB", object, 300);
    }

    @Test
    public void nestedObjectTest() {
        EzyObject root = EzyEntityFactory.newObject();
        EzyObject current = root;
        for (int i = 0; i < 100; ++i) {
            EzyObject child = EzyEntityFactory.newObject();
            current.put("level", i);
            current.put("name", "level " + i);
            current.put("child", child);
            current = child;
        }
        compare("nested 100 levels", root, 3000);
    }

    private void compare(String name, Object value, int loop) {
        byte[] bytes = serializer.serialize(value);
        Asserts.assertEquals(serializer.serialize(deserializer.deserialize(bytes)).length, bytes.length);
        run(() -> serializer.serialize(value), loop / 10 + 1);
        run(() -> byteBufferSerializer.serialize(value), loop / 10 + 1);
        run(() -> deserializer.deserialize(bytes), loop / 10 + 1);
        Result current = run(() -> serializer.serialize(value), loop);
        Result byteBuffer = run(() -> byteBufferSerializer.serialize(value), loop);
        Result deserialize = run(() -> deserializer.deserialize(bytes), loop);

        System.out.printf(
            "%-18s | %10s | serialize: %9.4f ms/op %10s/op | byte buffer: %9.4f ms/op %10s/op"
                + " | deserialize: %9.4f ms/op %10s/op%n",
            name,
            formatBytes(bytes.length),
            current.millisPerOp(loop),
            formatBytes(current.allocatedBytes / loop),
            byteBuffer.millisPerOp(loop),
            formatBytes(byteBuffer.allocatedBytes / loop),
            deserialize.millisPerOp(loop),
            formatBytes(deserialize.allocatedBytes / loop)
        );
    }

    private static Result run(Supplier<Object> supplier, int loop) {
        long allocatedBefore = allocatedBytes();
        long start = System.nanoTime();
        int count = 0;
        for (int i = 0; i < loop; ++i) {
            if (supplier.get() != null) {
                ++count;
            }
        }
        long nanos = System.nanoTime() - start;
        long allocated = allocatedBytes() - allocatedBefore;
        Asserts.assertEquals(count, loop);
        return new Result(nanos, allocated);
    }

    private static EzyObject newSmallObject(int index) {
        EzyObject params = EzyEntityFactory.newObject();
        params.put("roomId", 1000 + index);
        params.put("x", index * 1.5D);
        params.put("y", index * 2.5F);
        params.put("alive", index % 2 == 0);
        params.put("items", new int[] {1, 2, 3, index});
        EzyObject object = EzyEntityFactory.newObject();
        object.put("cmd", "move");
        object.put("id", (long) index);
        object.put("player", "player " + index);
        object.put("params", params);
        return object;
    }

    private static String newString(char ch, int size) {
        StringBuilder builder = new StringBuilder(size);
        for (int i = 0; i < size; ++i) {
            builder.append(ch);
        }
        return builder.toString();
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
}
