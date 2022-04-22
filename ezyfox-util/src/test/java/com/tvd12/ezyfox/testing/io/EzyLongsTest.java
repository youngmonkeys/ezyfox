package com.tvd12.ezyfox.testing.io;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.io.EzyBytes;
import com.tvd12.ezyfox.io.EzyLongs;
import com.tvd12.test.assertion.Asserts;
import com.tvd12.test.base.BaseTest;
import static org.testng.Assert.*;

import java.nio.ByteBuffer;

public class EzyLongsTest extends BaseTest {

    @Test
    public void test() {
        assertEquals(EzyLongs.bin2long(63), Long.MAX_VALUE);
        assertEquals(EzyLongs.bin2long(
                EzyBytes.getBytes(ByteBuffer.allocate(8).putLong(-100L))), -100L);
        assertEquals(EzyLongs.bin2ulong(
                EzyBytes.getBytes(ByteBuffer.allocate(8).putLong(100L))), 100L);
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
        ByteBuffer buffer = ByteBuffer.wrap(new byte[] {0, 0, 0, 0, 0, 0, 100});

        // when
        long actual = EzyLongs.bin2long(buffer, 7);

        // then
        Asserts.assertEquals(100L, actual);
    }

    @Test
    public void bytesToLongTestWith4Bytes() {
        // given
        ByteBuffer buffer = ByteBuffer.wrap(new byte[] {0, 0, 0, 100});

        // when
        long actual = EzyLongs.bin2long(buffer, 4);

        // then
        Asserts.assertEquals(100L, actual);
    }

    @Test
    public void bytesToLongTestWith2Bytes() {
        // given
        ByteBuffer buffer = ByteBuffer.wrap(new byte[] {0, 100});

        // when
        long actual = EzyLongs.bin2long(buffer, 2);

        // then
        Asserts.assertEquals(100L, actual);
    }

    @Test
    public void bytesToLongTestWithByte() {
        // given
        ByteBuffer buffer = ByteBuffer.wrap(new byte[] {100});

        // when
        long actual = EzyLongs.bin2long(buffer, 1);

        // then
        Asserts.assertEquals(100L, actual);
    }

    @Override
    public Class<?> getTestClass() {
        return EzyLongs.class;
    }

}
