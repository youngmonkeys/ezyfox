package com.tvd12.ezyfox.testing.io;

import com.tvd12.ezyfox.io.EzyBytes;
import com.tvd12.ezyfox.io.EzyLongs;
import com.tvd12.ezyfox.io.EzyMath;
import com.tvd12.test.base.BaseTest;
import com.tvd12.test.performance.Performance;
import org.testng.annotations.Test;

import java.nio.ByteBuffer;

import static org.testng.Assert.assertEquals;

public class EzyMathTest extends BaseTest {

    @Test
    public void test() {
        assertEquals(EzyMath.bin2uint(
                new byte[]{1, 2, 3, 4}),
            ByteBuffer.wrap(new byte[]{1, 2, 3, 4}).getInt());
        assertEquals(EzyMath.bin2int(8), 255);

        assertEquals(EzyMath.bin2ulong(
                new byte[]{1, 2, 3, 4, 5, 6, 7, 8}),
            ByteBuffer.wrap(new byte[]{1, 2, 3, 4, 5, 6, 7, 8}).getLong());
        assertEquals(EzyMath.bin2long(40), 1099511627775L);

        assertEquals(EzyMath.bin2int(
                getBytes(ByteBuffer.allocate(4).putInt(-100))),
            -100);

        assertEquals(EzyMath.bin2long(
                getBytes(ByteBuffer.allocate(8).putLong(-100000))),
            -100000L);
        byte[] xor = new byte[]{(byte) 255, (byte) 255, (byte) 255};
        EzyMath.xor(xor);
        assertEquals(xor, new byte[]{0, 0, 0});
    }

    protected byte[] getBytes(ByteBuffer buffer) {
        return EzyBytes.getBytes(buffer);
    }

    @Test
    public void performanceForLongTest() {
        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.putLong(Long.MAX_VALUE);
        buffer.flip();
        byte[] bytes = new byte[8];
        buffer.get(bytes);
        assert EzyMath.bin2long(bytes) == Long.MAX_VALUE;
        long time1 = Performance.create()
            .test(() -> {
                EzyMath.bin2long(bytes);
            })
            .getTime();
        long time2 = Performance.create()
            .test(() -> {
                ByteBuffer buf = ByteBuffer.wrap(bytes);
                buf.getLong();
            })
            .getTime();
        System.out.println("time1: " + time1 + ", time2: " + time2);
    }

    @Test
    public void performanceForIntTest() {
        ByteBuffer buffer = ByteBuffer.allocate(4);
        buffer.putInt(Integer.MAX_VALUE);
        buffer.flip();
        byte[] bytes = new byte[4];
        buffer.get(bytes);
        assert EzyMath.bin2long(bytes) == Integer.MAX_VALUE;
        long time1 = Performance.create()
            .test(() -> {
                EzyMath.bin2long(bytes);
            })
            .getTime();
        long time2 = Performance.create()
            .test(() -> {
                ByteBuffer buf = ByteBuffer.wrap(bytes);
                buf.getInt();
            })
            .getTime();
        System.out.println("time1: " + time1 + ", time2: " + time2);
    }

    @Test
    public void performanceForBufferTest() {
        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.putLong(Long.MAX_VALUE);
        buffer.flip();
        assert EzyLongs.bin2long(buffer) == Long.MAX_VALUE;
        long time1 = Performance.create()
            .test(() -> {
                buffer.flip();
                EzyLongs.bin2long(buffer);
            })
            .getTime();
        long time2 = Performance.create()
            .test(() -> {
                buffer.flip();
                buffer.getLong();
            })
            .getTime();
        System.out.println("time1: " + time1 + ", time2: " + time2);
    }

    @Override
    public Class<?> getTestClass() {
        return EzyMath.class;
    }
}
