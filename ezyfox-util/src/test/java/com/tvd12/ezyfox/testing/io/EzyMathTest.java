package com.tvd12.ezyfox.testing.io;

import com.tvd12.ezyfox.io.EzyLongs;
import com.tvd12.ezyfox.io.EzyMath;
import com.tvd12.test.assertion.Asserts;
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
                ByteBuffer.allocate(4).putInt(-100).array()),
            -100);

        assertEquals(EzyMath.bin2long(
                ByteBuffer.allocate(8).putLong(-100000).array()),
            -100000L);
        byte[] xor = new byte[]{(byte) 255, (byte) 255, (byte) 255};
        EzyMath.xor(xor);
        assertEquals(xor, new byte[]{0, 0, 0});
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

    @Test
    public void bin2longEmptyBytes() {
        // given
        byte[] bytes = new byte[0];

        // when
        Throwable e1 = Asserts.assertThrows(() -> EzyMath.bin2long(bytes));
        Throwable e2 = Asserts.assertThrows(() -> EzyMath.bin2ulong(bytes));
        Throwable e3 = Asserts.assertThrows(() -> EzyMath.bin2int(bytes));
        Throwable e4 = Asserts.assertThrows(() -> EzyMath.bin2uint(bytes));

        // then
        Asserts.assertEqualsType(e1, IllegalArgumentException.class);
        Asserts.assertEqualsType(e2, IllegalArgumentException.class);
        Asserts.assertEqualsType(e3, IllegalArgumentException.class);
        Asserts.assertEqualsType(e4, IllegalArgumentException.class);
    }

    @Test
    public void bin2longMoreThan8BytesKeepsLow64Bits() {
        // given
        byte[] bytes = new byte[]{0x7f, (byte) 0xff, (byte) 0xff, (byte) 0xff,
            (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xfe};

        // when
        long actualSigned = EzyMath.bin2long(bytes);
        long actualUnsigned = EzyMath.bin2ulong(bytes);

        // then
        Asserts.assertEquals(actualSigned, -2L);
        Asserts.assertEquals(actualUnsigned, -2L);
    }

    @Test
    public void bin2intMoreThan4BytesKeepsLow32Bits() {
        // given
        byte[] bytes = new byte[]{1, 0, 0, 0, 7};

        // when
        int actual = EzyMath.bin2int(bytes);

        // then
        Asserts.assertEquals(actual, 7);
    }

    @Test
    public void bin2longSignExtendsShortInput() {
        // given
        byte[] bytes = new byte[]{(byte) 0xff, (byte) 0xfe};

        // when
        long actualSigned = EzyMath.bin2long(bytes);
        long actualUnsigned = EzyMath.bin2ulong(bytes);

        // then
        Asserts.assertEquals(actualSigned, -2L);
        Asserts.assertEquals(actualUnsigned, 65534L);
    }

    @Override
    public Class<?> getTestClass() {
        return EzyMath.class;
    }
}
