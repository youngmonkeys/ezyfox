package com.tvd12.ezyfox.testing.io;

import static org.testng.Assert.assertEquals;

import java.nio.ByteBuffer;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.io.EzyBytes;
import com.tvd12.test.assertion.Asserts;
import com.tvd12.test.base.BaseTest;
import com.tvd12.test.performance.Performance;

public class EzyBytesTest extends BaseTest {

    @Test
    public void test() {
        assertEquals(EzyBytes.copy(ByteBuffer.wrap(new byte[] {1, 2, 3, 4, 5, 6}), 3),
                new byte[] {1, 2, 3});
        assertEquals(EzyBytes.getBytes(1, 255 + 1, 2),
                new byte[] {1, 1, 0});
        assertEquals(EzyBytes.getBytes((byte)1, 100.5D),
                EzyBytes.merge((byte)1, EzyBytes.getBytes(ByteBuffer.allocate(8).putDouble(100.5D))));
        assertEquals(EzyBytes.getBytes((byte)1, 100.5F),
                EzyBytes.merge((byte)1, EzyBytes.getBytes(ByteBuffer.allocate(4).putFloat(100.5F))));
        assertEquals(EzyBytes.getBytes(100L),
                EzyBytes.getBytes(ByteBuffer.allocate(8).putLong(100L)));
        assertEquals(EzyBytes.getBytes(100),
                EzyBytes.getBytes(ByteBuffer.allocate(4).putInt(100)));
        assertEquals(EzyBytes.getBytes((short)100),
                EzyBytes.getBytes(ByteBuffer.allocate(2).putShort((short)100)));
    }

    @Test
    public void test1() {
        assert EzyBytes.totalBytes(new byte[][] {{1,2},{1,2,3}}) == 5;
        assertEquals(EzyBytes.merge(new byte[][] {{1,2},{1,2,3}}), new byte[] {1,2,1,2,3});
    }

    @Test
    public void test2() {
        byte[] a = new byte[] {1, 2, 3};
        byte[] b = EzyBytes.copy(a, 1, 2);
        assertEquals(b, new byte[] {2, 3});
    }

    @Test
    public void mergePerformaceTest() {
        byte[][] bytess = new byte[5][100];
        for(int i = 0 ; i < bytess.length ; ++i) {
            for(int k = 0 ; k < bytess[i].length ; ++k) {
                bytess[i][k] = 1;
            }
        }
        long time1 = Performance.create()
                .test(() -> {
                    EzyBytes.merge(bytess);
                })
                .getTime();
        System.out.println("mergePerformaceTest.time1 = " + time1);
    }

    @Test
    public void positiveNumberToBytesTest() {
        Asserts.assertEquals(new byte[] {100}, EzyBytes.numberToBytes(100));
        Asserts.assertEquals(new byte[] {Byte.MAX_VALUE}, EzyBytes.numberToBytes(Byte.MAX_VALUE));
        Asserts.assertThat(ByteBuffer.allocate(2).putShort((short)12345).array())
            .isEqualsTo(EzyBytes.numberToBytes(12345));
        Asserts.assertThat(ByteBuffer.allocate(2).putShort(Short.MAX_VALUE).array())
            .isEqualsTo(EzyBytes.numberToBytes(Short.MAX_VALUE));
        Asserts.assertThat(ByteBuffer.allocate(4).putInt(12345678).array())
            .isEqualsTo(EzyBytes.numberToBytes(12345678));
        Asserts.assertThat(ByteBuffer.allocate(4).putInt(Integer.MAX_VALUE).array())
            .isEqualsTo(EzyBytes.numberToBytes(Integer.MAX_VALUE));
        Asserts.assertThat(ByteBuffer.allocate(8).putLong((long)Integer.MAX_VALUE + 1L).array())
            .isEqualsTo(EzyBytes.numberToBytes((long)Integer.MAX_VALUE + 1L));
        Asserts.assertThat(ByteBuffer.allocate(8).putLong(Long.MAX_VALUE).array())
            .isEqualsTo(EzyBytes.numberToBytes(Long.MAX_VALUE));
    }

    @Test
    public void negativeNumberToBytesTest() {
        Asserts.assertEquals(new byte[] {-100}, EzyBytes.numberToBytes(-100));
        Asserts.assertEquals(new byte[] {Byte.MIN_VALUE}, EzyBytes.numberToBytes(Byte.MIN_VALUE));
        Asserts.assertThat(ByteBuffer.allocate(2).putShort((short)(-12345)).array())
            .isEqualsTo(EzyBytes.numberToBytes(-12345));
        Asserts.assertThat(ByteBuffer.allocate(2).putShort(Short.MIN_VALUE).array())
            .isEqualsTo(EzyBytes.numberToBytes(Short.MIN_VALUE));
        Asserts.assertThat(ByteBuffer.allocate(4).putInt(-12345678).array())
            .isEqualsTo(EzyBytes.numberToBytes(-12345678));
        Asserts.assertThat(ByteBuffer.allocate(4).putInt(Integer.MIN_VALUE).array())
            .isEqualsTo(EzyBytes.numberToBytes(Integer.MIN_VALUE));
        Asserts.assertThat(ByteBuffer.allocate(8).putLong((long)Integer.MIN_VALUE - 1L).array())
            .isEqualsTo(EzyBytes.numberToBytes((long)Integer.MIN_VALUE - 1L));
        Asserts.assertThat(ByteBuffer.allocate(8).putLong(Long.MIN_VALUE).array())
            .isEqualsTo(EzyBytes.numberToBytes(Long.MIN_VALUE));
    }

    @Override
    public Class<?> getTestClass() {
        return EzyBytes.class;
    }
}
