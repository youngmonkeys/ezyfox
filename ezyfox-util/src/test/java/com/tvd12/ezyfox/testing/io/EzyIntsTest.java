package com.tvd12.ezyfox.testing.io;

import com.tvd12.ezyfox.io.EzyInts;
import com.tvd12.test.assertion.Asserts;
import com.tvd12.test.base.BaseTest;
import org.testng.annotations.Test;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import static org.testng.Assert.assertEquals;

public class EzyIntsTest extends BaseTest {

    @Test
    public void test() {
        assertEquals(EzyInts.bin2int(31), Integer.MAX_VALUE);
        assertEquals(EzyInts.bin2int(
            ByteBuffer.allocate(4).putInt(-100).array()), -100);
        assertEquals(EzyInts.bin2uint(
            ByteBuffer.allocate(4).putInt(100).array()), 100);
        ByteBuffer buffer1 = ByteBuffer.allocate(4).putInt(-1000);
        buffer1.flip();
        assertEquals(EzyInts.bin2int(buffer1), -1000);

        ByteBuffer buffer2 = ByteBuffer.allocate(4).putInt(1000);
        buffer2.flip();
        assertEquals(EzyInts.bin2uint(buffer2), 1000);
    }

    @Test
    public void bytesToIntTestWith3Bytes() {
        // given
        ByteBuffer buffer = ByteBuffer.wrap(new byte[]{0, 0, 100});

        // when
        int actual = EzyInts.bin2int(buffer, 3);

        // then
        Asserts.assertEquals(100, actual);
    }

    @Test
    public void bytesToLongTestWith4Bytes() {
        // given
        ByteBuffer buffer = ByteBuffer.wrap(new byte[]{0, 100});

        // when
        int actual = EzyInts.bin2int(buffer, 2);

        // then
        Asserts.assertEquals(100, actual);
    }

    @Test
    public void bytesToIntTestWithByte() {
        // given
        ByteBuffer buffer = ByteBuffer.wrap(new byte[]{100});

        // when
        int actual = EzyInts.bin2int(buffer, 1);

        // then
        Asserts.assertEquals(100, actual);
    }

    @Test
    public void bin2intLittleEndianBufferReadsBigEndian() {
        // given
        ByteBuffer buffer4 = ByteBuffer.wrap(new byte[]{0, 0, 1, 0})
            .order(ByteOrder.LITTLE_ENDIAN);
        ByteBuffer buffer3 = ByteBuffer.wrap(new byte[]{0, 1, 0})
            .order(ByteOrder.LITTLE_ENDIAN);
        ByteBuffer buffer2 = ByteBuffer.wrap(new byte[]{(byte) 0xff, (byte) 0xfe})
            .order(ByteOrder.LITTLE_ENDIAN);

        // when
        int actual4 = EzyInts.bin2int(buffer4, 4);
        int actual3 = EzyInts.bin2int(buffer3, 3);
        int actual2 = EzyInts.bin2int(buffer2, 2);

        // then
        Asserts.assertEquals(actual4, 256);
        Asserts.assertEquals(actual3, 256);
        Asserts.assertEquals(actual2, -2);
    }

    @Test
    public void bin2uintBigAndLittleEndianSameResult() {
        // given
        byte[] bytes = new byte[]{(byte) 0xff, (byte) 0xfe};

        // when
        int actualBig = EzyInts.bin2uint(ByteBuffer.wrap(bytes), 2);
        int actualLittle = EzyInts.bin2uint(
            ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN),
            2
        );

        // then
        Asserts.assertEquals(actualBig, 65534);
        Asserts.assertEquals(actualLittle, 65534);
    }

    @Override
    public Class<?> getTestClass() {
        return EzyInts.class;
    }
}
