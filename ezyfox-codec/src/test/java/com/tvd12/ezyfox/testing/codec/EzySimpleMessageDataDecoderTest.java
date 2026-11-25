package com.tvd12.ezyfox.testing.codec;

import com.tvd12.ezyfox.codec.EzyByteToObjectDecoder;
import com.tvd12.ezyfox.codec.EzyDefaultDecodeHandlers;
import com.tvd12.ezyfox.codec.EzyMessage;
import com.tvd12.ezyfox.codec.EzySimpleMessageDataDecoder;
import com.tvd12.ezyfox.exception.EzyZeroRequestSizeException;
import com.tvd12.ezyfox.io.EzyBytes;
import com.tvd12.test.assertion.Asserts;
import com.tvd12.test.util.RandomUtil;
import org.testng.annotations.Test;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static org.mockito.Mockito.*;

public class EzySimpleMessageDataDecoderTest {

    @Test
    public void test() throws Exception {
        EzyByteToObjectDecoder d = new ExByteToObjectDecoder();
        EzySimpleMessageDataDecoder decoder = new EzySimpleMessageDataDecoder(d);
        ByteBuffer buffer = ByteBuffer.allocate(12);
        buffer.put((byte) 0);
        buffer.putShort((short) 9);
        buffer.put("012345678".getBytes());
        buffer.flip();
        byte[] bytes = EzyBytes.getBytes(buffer);
        AtomicBoolean called = new AtomicBoolean(false);
        decoder.decode(bytes, data -> called.set(true));
        assert called.get();
    }

    @Test
    public void test2() throws Exception {
        EzyByteToObjectDecoder d = new ExByteToObjectDecoder();
        EzySimpleMessageDataDecoder decoder = new EzySimpleMessageDataDecoder(d);
        ByteBuffer buffer = ByteBuffer.allocate(12 + 6);
        buffer.put((byte) 0);
        buffer.putShort((short) 9);
        buffer.put("012345678012345".getBytes());
        buffer.flip();
        byte[] bytes = EzyBytes.getBytes(buffer);
        AtomicBoolean called = new AtomicBoolean(false);
        decoder.decode(bytes, data -> {
            called.set(true);
            try {
                decoder.decode(data, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        assert called.get();
        decoder.decode(bytes, data -> called.set(true));
        decoder.reset();
    }

    @Test
    public void test3() throws Exception {
        EzyByteToObjectDecoder d = new ExByteToObjectDecoder2();
        EzySimpleMessageDataDecoder decoder = new EzySimpleMessageDataDecoder(d);
        ByteBuffer buffer = ByteBuffer.allocate(12 * 2);
        buffer.put((byte) 0);
        buffer.putShort((short) 9);
        buffer.put("012345678".getBytes());
        buffer.put((byte) 0);
        buffer.putShort((short) 9);
        buffer.put("012345678".getBytes());
        buffer.flip();
        byte[] bytes = EzyBytes.getBytes(buffer);
        AtomicInteger count = new AtomicInteger();
        decoder.decode(bytes, data -> count.incrementAndGet());
        assert count.get() == 2;
    }

    @Test
    public void test4() throws Exception {
        EzyByteToObjectDecoder d = new ExByteToObjectDecoder();
        EzySimpleMessageDataDecoder decoder = new EzySimpleMessageDataDecoder(d);
        ByteBuffer buffer = ByteBuffer.allocate(12);
        buffer.put((byte) 0);
        buffer.putShort((short) 9);
        buffer.put("012345678".getBytes());
        buffer.flip();
        byte[] bytes = EzyBytes.getBytes(buffer);
        AtomicBoolean called = new AtomicBoolean(false);
        decoder.destroy();
        decoder.decode(bytes, data -> called.set(true));
        assert !called.get();
    }

    @Test
    public void decodeByChunksTest() throws Exception {
        // given
        EzyByteToObjectDecoder d = new ExByteToObjectDecoder();
        EzySimpleMessageDataDecoder decoder = new EzySimpleMessageDataDecoder(d);
        int size = 5000;
        byte[] body = new byte[size];
        for (int i = 0; i < size; ++i) {
            body[i] = (byte) i;
        }
        ByteBuffer buffer = ByteBuffer.allocate((5 + size) * 2);
        for (int i = 0; i < 2; ++i) {
            buffer.put((byte) 1);
            buffer.putInt(size);
            buffer.put(body);
        }
        byte[] bytes = buffer.array();
        List<EzyMessage> messages = new ArrayList<>();

        // when
        int chunkSize = 7;
        for (int offset = 0; offset < bytes.length; offset += chunkSize) {
            int length = Math.min(chunkSize, bytes.length - offset);
            decoder.decode(
                Arrays.copyOfRange(bytes, offset, offset + length),
                messages::add
            );
        }

        // then
        Asserts.assertEquals(messages.size(), 2);
        for (EzyMessage message : messages) {
            Asserts.assertEquals(message.getSize(), size);
            Asserts.assertEquals(message.getContent(), body);
        }
    }

    @Test
    public void decodeAfterErrorTest() throws Exception {
        // given
        EzySimpleMessageDataDecoder decoder = new EzySimpleMessageDataDecoder(
            new ResettableByteToObjectDecoder()
        );
        ByteBuffer invalid = ByteBuffer.allocate(5);
        invalid.put((byte) 0);
        invalid.putShort((short) 0);
        invalid.put((byte) 1);
        invalid.put((byte) 2);
        invalid.flip();
        List<EzyMessage> messages = new ArrayList<>();

        // when
        Throwable e = Asserts.assertThrows(() ->
            decoder.decode(EzyBytes.getBytes(invalid), messages::add)
        );
        decoder.decode(newMessageBytes("012345678"), messages::add);

        // then
        Asserts.assertEqualsType(e, EzyZeroRequestSizeException.class);
        Asserts.assertEquals(messages.size(), 1);
        Asserts.assertEquals(messages.get(0).getContent(), "012345678".getBytes());
    }

    @Test
    public void resetDropsPendingBytesTest() throws Exception {
        // given
        EzySimpleMessageDataDecoder decoder = new EzySimpleMessageDataDecoder(
            new ResettableByteToObjectDecoder()
        );
        byte[] bytes = newMessageBytes("012345678");
        List<EzyMessage> messages = new ArrayList<>();
        decoder.decode(Arrays.copyOfRange(bytes, 0, 7), messages::add);

        // when
        decoder.reset();
        decoder.decode(newMessageBytes("abc"), messages::add);

        // then
        Asserts.assertEquals(messages.size(), 1);
        Asserts.assertEquals(messages.get(0).getContent(), "abc".getBytes());
    }

    private static byte[] newMessageBytes(String content) {
        byte[] body = content.getBytes();
        ByteBuffer buffer = ByteBuffer.allocate(3 + body.length);
        buffer.put((byte) 0);
        buffer.putShort((short) body.length);
        buffer.put(body);
        return buffer.array();
    }

    @Test
    public void decodeWithKeyTest() throws Exception {
        // given
        EzyMessage message = mock(EzyMessage.class);
        byte[] decryptionKey = RandomUtil.randomShortByteArray();
        Object result = new Object();

        EzyByteToObjectDecoder decoder = mock(EzyByteToObjectDecoder.class);
        when(decoder.decode(message, decryptionKey)).thenReturn(result);

        EzySimpleMessageDataDecoder sut = new EzySimpleMessageDataDecoder(decoder);

        // when
        Object actual = sut.decode(message, decryptionKey);

        // then
        Asserts.assertEquals(result, actual);
        verify(decoder, times(1)).decode(message, decryptionKey);
    }

    public static class ExByteToObjectDecoder implements EzyByteToObjectDecoder {

        protected final EzyDefaultDecodeHandlers handlers;

        public ExByteToObjectDecoder() {
            this.handlers = EzyDefaultDecodeHandlers.builder()
                .maxSize(128 * 1000000)
                .build();
        }

        @Override
        public Object decode(EzyMessage message) {
            return message;
        }

        @Override
        public void decode(ByteBuffer bytes, Queue<EzyMessage> queue) {
            handlers.handle(bytes, queue);
        }

        @Override
        public void reset() {
        }

    }

    public static class ResettableByteToObjectDecoder extends ExByteToObjectDecoder {

        @Override
        public void reset() {
            handlers.reset();
        }
    }

    public static class ExByteToObjectDecoder2 implements EzyByteToObjectDecoder {

        protected final EzyDefaultDecodeHandlers handlers;

        public ExByteToObjectDecoder2() {
            this.handlers = EzyDefaultDecodeHandlers.builder()
                .maxSize(128 * 1000000)
                .build();
        }

        @Override
        public Object decode(EzyMessage message) {
            return message;
        }

        @Override
        public void decode(ByteBuffer bytes, Queue<EzyMessage> queue) {
            handlers.handle(bytes, queue);
            handlers.handle(bytes, queue);
        }

        @Override
        public void reset() {
        }

    }
}
