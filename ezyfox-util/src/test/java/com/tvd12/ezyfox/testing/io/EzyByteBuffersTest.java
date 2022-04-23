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
        EzyByteBuffers.getBytes(buffer);

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

    @Override
    public Class<?> getTestClass() {
        return EzyByteBuffers.class;
    }
}
