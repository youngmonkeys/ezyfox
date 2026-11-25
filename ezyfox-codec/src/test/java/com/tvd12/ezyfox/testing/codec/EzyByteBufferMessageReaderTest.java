package com.tvd12.ezyfox.testing.codec;

import com.tvd12.ezyfox.codec.EzyByteBufferMessageReader;
import com.tvd12.ezyfox.exception.EzyMaxRequestSizeException;
import com.tvd12.test.assertion.Asserts;
import org.testng.annotations.Test;

import java.nio.ByteBuffer;

public class EzyByteBufferMessageReaderTest {

    @Test
    public void test() {
        EzyByteBufferMessageReader reader = new EzyByteBufferMessageReader();
        ByteBuffer buffer = ByteBuffer.allocate(12);
        buffer.put((byte) 0);
        buffer.putShort((short) 9);
        buffer.put("012345678".getBytes());
        buffer.flip();
        Asserts.assertTrue(reader.readHeader(buffer));
        Asserts.assertTrue(reader.readSize(buffer, 128));
        Asserts.assertTrue(reader.readContent(buffer));
        Asserts.assertEquals(reader.get().getSize(), 9);
        Asserts.assertFalse(reader.readHeader(buffer));
        Asserts.assertFalse(reader.readSize(buffer, 128));
        Asserts.assertFalse(reader.readContent(buffer));
    }

    @Test
    public void test2() {
        EzyByteBufferMessageReader reader = new EzyByteBufferMessageReader();
        ByteBuffer buffer = ByteBuffer.allocate(12);
        byte header = (byte) 0;
        header |= (1) & 0xFF;
        buffer.put(header);
        buffer.putInt(7);
        buffer.put("0123456".getBytes());
        buffer.flip();
        Asserts.assertTrue(reader.readHeader(buffer));
        Asserts.assertTrue(reader.readSize(buffer, 128));
    }

    @Test(expectedExceptions = EzyMaxRequestSizeException.class)
    public void test3() {
        EzyByteBufferMessageReader reader = new EzyByteBufferMessageReader();
        ByteBuffer buffer = ByteBuffer.allocate(12);
        byte header = (byte) 0;
        header |= (1) & 0xFF;
        buffer.put(header);
        buffer.putInt(7);
        buffer.put("0123456".getBytes());
        buffer.flip();
        reader.readHeader(buffer);
        reader.readSize(buffer, 1);
    }

    @Test
    public void testRawBytes() {
        EzyByteBufferMessageReader reader = new EzyByteBufferMessageReader();
        ByteBuffer buffer = ByteBuffer.allocate(12);
        byte header = (byte) 0;
        header |= (1) & 0xFF;
        header |= (1 << 4) & 0xFF;
        buffer.put(header);
        buffer.putInt(7);
        buffer.put("0123456".getBytes());
        buffer.flip();
        reader.readHeader(buffer);
        reader.readSize(buffer, 128);
        reader.readContent(buffer);
    }

    @Test
    public void readContentByChunksTest() {
        // given
        int size = 10000;
        byte[] body = newBody(size);
        ByteBuffer buffer = ByteBuffer.allocate(5);
        buffer.put((byte) 1);
        buffer.putInt(size);
        buffer.flip();

        EzyByteBufferMessageReader reader = new EzyByteBufferMessageReader();
        reader.readHeader(buffer);
        reader.readSize(buffer, size);

        // when
        int offset = 0;
        int chunkSize = 333;
        boolean done = false;
        while (offset < size) {
            int length = Math.min(chunkSize, size - offset);
            Asserts.assertFalse(done);
            done = reader.readContent(ByteBuffer.wrap(body, offset, length));
            offset += length;
        }

        // then
        Asserts.assertTrue(done);
        Asserts.assertEquals(reader.get().getSize(), size);
        Asserts.assertEquals(reader.get().getContent(), body);
    }

    @Test
    public void readRawBytesContentByChunksTest() {
        // given
        int size = 3000;
        byte[] body = newBody(size);
        byte header = (byte) (1 | (1 << 4));
        ByteBuffer buffer = ByteBuffer.allocate(5);
        buffer.put(header);
        buffer.putInt(size);
        buffer.flip();

        EzyByteBufferMessageReader reader = new EzyByteBufferMessageReader();
        reader.readHeader(buffer);
        reader.readSize(buffer, size);

        // when
        Asserts.assertFalse(reader.readContent(ByteBuffer.wrap(body, 0, 100)));
        Asserts.assertFalse(reader.readContent(ByteBuffer.wrap(body, 100, 2000)));
        Asserts.assertTrue(reader.readContent(ByteBuffer.wrap(body, 2100, 900)));

        // then
        ByteBuffer expected = ByteBuffer.allocate(5 + size);
        expected.put(header);
        expected.putInt(size);
        expected.put(body);
        Asserts.assertEquals(reader.get().getContent(), expected.array());
        Asserts.assertEquals(reader.get().getSize(), size);
    }

    private static byte[] newBody(int size) {
        byte[] body = new byte[size];
        for (int i = 0; i < size; ++i) {
            body[i] = (byte) i;
        }
        return body;
    }
}
