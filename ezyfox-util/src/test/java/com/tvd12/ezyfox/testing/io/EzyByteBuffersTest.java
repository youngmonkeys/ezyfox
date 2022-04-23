package com.tvd12.ezyfox.testing.io;

import com.tvd12.ezyfox.io.EzyByteBuffers;
import com.tvd12.test.base.BaseTest;
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

    @Override
    public Class<?> getTestClass() {
        return EzyByteBuffers.class;
    }
}
