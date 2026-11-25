package com.tvd12.ezyfox.testing.io;

import com.tvd12.ezyfox.io.EzyLongs;
import com.tvd12.test.assertion.Asserts;
import com.tvd12.test.base.BaseTest;
import org.testng.annotations.Test;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import static org.testng.Assert.assertEquals;

public class EzyLongsTest extends BaseTest {

    @Test
    public void test() {
        assertEquals(EzyLongs.bin2long(63), Long.MAX_VALUE);
        assertEquals(EzyLongs.bin2long(
            ByteBuffer.allocate(8).putLong(-100L).array()), -100L);
        assertEquals(EzyLongs.bin2ulong(
            ByteBuffer.allocate(8).putLong(100L).array()), 100L);
        ByteBuffer buffer1 = ByteBuffer.allocate(8).putLong(-1000L);
        buffer1.flip();
        assertEquals(EzyLongs.bin2long(buffer1), -1000L);

        ByteBuffer buffer2 = ByteBuffer.allocate(8).putLong(1000L);
        buffer2.flip();
        assertEquals(EzyLongs.bin2ulong(buffer2), 1000L);
    }

    @Test
    public void bytesToLongTestWith7Bytes() {
        // given
        ByteBuffer buffer = ByteBuffer.wrap(new byte[]{0, 0, 0, 0, 0, 0, 100});

        // when
        long actual = EzyLongs.bin2long(buffer, 7);

        // then
        Asserts.assertEquals(100L, actual);
    }

    @Test
    public void bytesToLongTestWith4Bytes() {
        // given
        ByteBuffer buffer = ByteBuffer.wrap(new byte[]{0, 0, 0, 100});

        // when
        long actual = EzyLongs.bin2long(buffer, 4);

        // then
        Asserts.assertEquals(100L, actual);
    }

    @Test
    public void bytesToLongTestWith2Bytes() {
        // given
        ByteBuffer buffer = ByteBuffer.wrap(new byte[]{0, 100});

        // when
        long actual = EzyLongs.bin2long(buffer, 2);

        // then
        Asserts.assertEquals(100L, actual);
    }

    @Test
    public void bytesToLongTestWithByte() {
        // given
        ByteBuffer buffer = ByteBuffer.wrap(new byte[]{100});

        // when
        long actual = EzyLongs.bin2long(buffer, 1);

        // then
        Asserts.assertEquals(100L, actual);
    }

    @Test
    public void bin2longLittleEndianBufferReadsBigEndian() {
        // given
        ByteBuffer buffer8 = ByteBuffer.wrap(new byte[]{0, 0, 0, 0, 0, 0, 1, 0})
            .order(ByteOrder.LITTLE_ENDIAN);
        ByteBuffer buffer4 = ByteBuffer.wrap(new byte[]{0, 0, 1, 0})
            .order(ByteOrder.LITTLE_ENDIAN);
        ByteBuffer buffer2 = ByteBuffer.wrap(new byte[]{(byte) 0xff, (byte) 0xfe})
            .order(ByteOrder.LITTLE_ENDIAN);

        // when
        long actual8 = EzyLongs.bin2long(buffer8, 8);
        long actual4 = EzyLongs.bin2long(buffer4, 4);
        long actual2 = EzyLongs.bin2long(buffer2, 2);

        // then
        Asserts.assertEquals(actual8, 256L);
        Asserts.assertEquals(actual4, 256L);
        Asserts.assertEquals(actual2, -2L);
        Asserts.assertEquals(buffer8.remaining(), 0);
        Asserts.assertEquals(buffer4.remaining(), 0);
        Asserts.assertEquals(buffer2.remaining(), 0);
    }

    @Test
    public void bin2longNegativeWith3Bytes() {
        // given
        ByteBuffer buffer = ByteBuffer.wrap(new byte[]{(byte) 0xff, (byte) 0xff, (byte) 0xfe});

        // when
        long actual = EzyLongs.bin2long(buffer, 3);

        // then
        Asserts.assertEquals(actual, -2L);
    }

    @Test
    public void bin2ulongBigAndLittleEndianSameResult() {
        // given
        byte[] bytes4 = new byte[]{(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xfe};
        byte[] bytes2 = new byte[]{(byte) 0xff, (byte) 0xfe};
        byte[] bytes1 = new byte[]{(byte) 0xfe};

        // when
        // then
        for (ByteOrder order : new ByteOrder[]{ByteOrder.BIG_ENDIAN, ByteOrder.LITTLE_ENDIAN}) {
            Asserts.assertEquals(
                EzyLongs.bin2ulong(ByteBuffer.wrap(bytes4).order(order), 4),
                4294967294L
            );
            Asserts.assertEquals(
                EzyLongs.bin2ulong(ByteBuffer.wrap(bytes4).order(order), 3),
                16777215L
            );
            Asserts.assertEquals(
                EzyLongs.bin2ulong(ByteBuffer.wrap(bytes2).order(order), 2),
                65534L
            );
            Asserts.assertEquals(
                EzyLongs.bin2ulong(ByteBuffer.wrap(bytes1).order(order), 1),
                254L
            );
        }
    }

    @Test
    public void bin2ulongWith8Bytes() {
        // given
        ByteBuffer buffer = ByteBuffer.wrap(new byte[]{0, 0, 0, 0, (byte) 0x80, 0, 0, 0})
            .order(ByteOrder.LITTLE_ENDIAN);

        // when
        long actual = EzyLongs.bin2ulong(buffer);

        // then
        Asserts.assertEquals(actual, 2147483648L);
    }

    @Test
    public void bin2longWithZeroSize() {
        // given
        ByteBuffer buffer = ByteBuffer.wrap(new byte[]{1});

        // when
        Throwable e1 = Asserts.assertThrows(() -> EzyLongs.bin2long(buffer, 0));
        Throwable e2 = Asserts.assertThrows(() -> EzyLongs.bin2ulong(buffer, 0));

        // then
        Asserts.assertEqualsType(e1, IllegalArgumentException.class);
        Asserts.assertEqualsType(e2, IllegalArgumentException.class);
        Asserts.assertEquals(buffer.position(), 0);
    }

    @Override
    public Class<?> getTestClass() {
        return EzyLongs.class;
    }
}
