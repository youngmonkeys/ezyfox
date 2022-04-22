package com.tvd12.ezyfox.testing.codec;

import java.nio.ByteBuffer;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.callback.EzyCallback;
import com.tvd12.ezyfox.codec.EzyByteToObjectDecoder;
import com.tvd12.ezyfox.codec.EzyDefaultDecodeHandlers;
import com.tvd12.ezyfox.codec.EzyMessage;
import com.tvd12.ezyfox.codec.EzySimpleMessageDataDecoder;
import com.tvd12.ezyfox.io.EzyBytes;
import com.tvd12.test.assertion.Asserts;
import com.tvd12.test.util.RandomUtil;

import static org.mockito.Mockito.*;

public class EzySimpleMessageDataDecoderTest {

    @Test
    public void test() throws Exception {
        EzyByteToObjectDecoder d = new ExByteToObjectDecoder();
        EzySimpleMessageDataDecoder decoder = new EzySimpleMessageDataDecoder(d);
        ByteBuffer buffer = ByteBuffer.allocate(12);
        buffer.put((byte)0);
        buffer.putShort((short)9);
        buffer.put("012345678".getBytes());
        buffer.flip();
        byte[] bytes = EzyBytes.getBytes(buffer);
        AtomicBoolean called = new AtomicBoolean(false);
        decoder.decode(bytes, new EzyCallback<EzyMessage>() {
            @Override
            public void call(EzyMessage data) {
                called.set(true);
            }
        });
        assert called.get();
    }

    @Test
    public void test2() throws Exception {
        EzyByteToObjectDecoder d = new ExByteToObjectDecoder();
        EzySimpleMessageDataDecoder decoder = new EzySimpleMessageDataDecoder(d);
        ByteBuffer buffer = ByteBuffer.allocate(12 + 6);
        buffer.put((byte)0);
        buffer.putShort((short)9);
        buffer.put("012345678012345".getBytes());
        buffer.flip();
        byte[] bytes = EzyBytes.getBytes(buffer);
        AtomicBoolean called = new AtomicBoolean(false);
        decoder.decode(bytes, new EzyCallback<EzyMessage>() {
            @Override
            public void call(EzyMessage data) {
                called.set(true);
                try {
                    decoder.decode(data, null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        assert called.get();
        decoder.decode(bytes, new EzyCallback<EzyMessage>() {
            @Override
            public void call(EzyMessage data) {
                called.set(true);
            }
        });
        decoder.reset();
    }

    @Test
    public void test3() throws Exception {
        EzyByteToObjectDecoder d = new ExByteToObjectDecoder2();
        EzySimpleMessageDataDecoder decoder = new EzySimpleMessageDataDecoder(d);
        ByteBuffer buffer = ByteBuffer.allocate(12 * 2);
        buffer.put((byte)0);
        buffer.putShort((short)9);
        buffer.put("012345678".getBytes());
        buffer.put((byte)0);
        buffer.putShort((short)9);
        buffer.put("012345678".getBytes());
        buffer.flip();
        byte[] bytes = EzyBytes.getBytes(buffer);
        AtomicInteger count = new AtomicInteger();
        decoder.decode(bytes, new EzyCallback<EzyMessage>() {
            @Override
            public void call(EzyMessage data) {
                count.incrementAndGet();
            }
        });
        assert count.get() == 2;
    }

    @Test
    public void test4() throws Exception {
        EzyByteToObjectDecoder d = new ExByteToObjectDecoder();
        EzySimpleMessageDataDecoder decoder = new EzySimpleMessageDataDecoder(d);
        ByteBuffer buffer = ByteBuffer.allocate(12);
        buffer.put((byte)0);
        buffer.putShort((short)9);
        buffer.put("012345678".getBytes());
        buffer.flip();
        byte[] bytes = EzyBytes.getBytes(buffer);
        AtomicBoolean called = new AtomicBoolean(false);
        decoder.destroy();
        decoder.decode(bytes, new EzyCallback<EzyMessage>() {
            @Override
            public void call(EzyMessage data) {
                called.set(true);
            }
        });
        assert !called.get();
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
        public Object decode(EzyMessage message) throws Exception {
            return message;
        }

        @Override
        public void decode(ByteBuffer bytes, Queue<EzyMessage> queue) throws Exception {
            handlers.handle(bytes, queue);
        }

        @Override
        public void reset() {
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
        public Object decode(EzyMessage message) throws Exception {
            return message;
        }

        @Override
        public void decode(ByteBuffer bytes, Queue<EzyMessage> queue) throws Exception {
            handlers.handle(bytes, queue);
            handlers.handle(bytes, queue);
        }

        @Override
        public void reset() {
        }

    }
}