package com.tvd12.ezyfox.testing.io;

import com.tvd12.ezyfox.io.EzyByteBuffers;
import com.tvd12.ezyfox.io.EzyBytes;
import com.tvd12.test.assertion.Asserts;
import com.tvd12.test.base.BaseTest;
import com.tvd12.test.performance.Performance;
import org.testng.annotations.Test;

import java.nio.ByteBuffer;

public class EzyByteBuffersTest extends BaseTest {

    @Test
    public void test() {
        ByteBuffer buffer = ByteBuffer.allocate(8).putLong(1000L);
        buffer.flip();
        EzyByteBuffers.getRemainingBytes(buffer);

        ByteBuffer buffer1 = ByteBuffer.allocate(4).putInt(2);
        buffer1.flip();
        ByteBuffer buffer2 = ByteBuffer.allocate(4).putInt(3);
        buffer2.flip();
        ByteBuffer buffer3 = EzyByteBuffers.merge(new ByteBuffer[]{buffer1, buffer2});
        assert buffer3.capacity() == 8;
    }

    @Test
    public void merge2bytesTest() {
        // given
        byte b = 1;
        byte[] bs = new byte[] {2, 3};

        // when
        byte[] actual = EzyByteBuffers.merge2bytes(b, bs);

        // then
        Asserts.assertEquals(actual, new byte[] {1, 2, 3});
    }

    @Test
    public void mergePerformanceTest() {
        byte[] bytes = new byte[20000];
        long time1 = Performance.create()
            .loop(1000)
            .test(() -> EzyBytes.merge((byte) 1, bytes))
            .getTime();
        long time2 = Performance.create()
            .loop(1000)
            .test(() -> EzyByteBuffers.merge2bytes((byte) 1, bytes))
            .getTime();
        System.out.println("mergePerformanceTest.time1 = " + time1);
        System.out.println("mergePerformanceTest.time2 = " + time2);
    }

    @Test
    public void getRemainingBytesTest() {
        // given
        ByteBuffer buffer = ByteBuffer.wrap(new byte[]{1, 2, 3, 4});
        buffer.get();

        // when
        byte[] actual = EzyByteBuffers.getRemainingBytes(buffer);

        // then
        Asserts.assertEquals(ByteBuffer.wrap(actual), ByteBuffer.wrap(new byte[]{2, 3, 4}));
        Asserts.assertEquals(buffer.remaining(), 0);
    }

    @Test
    public void getRemainingBytesDoesNotFlipConsumedBuffer() {
        // given
        ByteBuffer buffer = ByteBuffer.wrap(new byte[]{1, 2, 3});
        buffer.get(new byte[3]);

        // when
        byte[] actual = EzyByteBuffers.getRemainingBytes(buffer);

        // then
        Asserts.assertEquals(actual.length, 0);
        Asserts.assertEquals(buffer.position(), 3);
        Asserts.assertEquals(buffer.limit(), 3);
    }

    @Test
    public void mergeDoesNotConsumeSourceBuffers() {
        // given
        ByteBuffer buffer1 = ByteBuffer.wrap(new byte[]{1, 2, 3});
        buffer1.get();
        ByteBuffer buffer2 = ByteBuffer.wrap(new byte[]{4, 5});

        // when
        ByteBuffer actual = EzyByteBuffers.merge(new ByteBuffer[]{buffer1, buffer2});

        // then
        Asserts.assertEquals(actual, ByteBuffer.wrap(new byte[]{2, 3, 4, 5}));
        Asserts.assertEquals(buffer1.position(), 1);
        Asserts.assertEquals(buffer1.remaining(), 2);
        Asserts.assertEquals(buffer2.position(), 0);
        Asserts.assertEquals(buffer2.remaining(), 2);
    }

    @Override
    public Class<?> getTestClass() {
        return EzyByteBuffers.class;
    }
}
