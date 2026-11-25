package com.tvd12.ezyfox.codec.testing;

import com.tvd12.ezyfox.codec.EzyByteToObjectDecoder;
import com.tvd12.ezyfox.codec.EzyMessage;
import com.tvd12.ezyfox.codec.EzyObjectToByteEncoder;
import com.tvd12.ezyfox.codec.MsgPackCodecCreator;
import com.tvd12.ezyfox.exception.EzyCodecException;
import com.tvd12.ezyfox.exception.EzyMaxRequestSizeException;
import com.tvd12.ezyfox.security.EzyAesCrypt;
import com.tvd12.test.assertion.Asserts;
import org.testng.annotations.Test;

import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.Queue;

public class MsgPackCompressionTest {

    @Test
    public void compressTest() throws Exception {
        // given
        MsgPackCodecCreator codecCreator = new MsgPackCodecCreator(false);
        EzyObjectToByteEncoder encoder = codecCreator.newEncoder();
        EzyByteToObjectDecoder decoder = codecCreator.newDecoder(100000);

        String message = newLongMessage();
        Queue<EzyMessage> messageQueue = new LinkedList<>();

        // when
        byte[] messageContent = encoder.toMessageContent(message);
        byte[] compressedContent = encoder.compressMessageContent(messageContent);
        byte[] bytes = encoder.encryptMessageContent(compressedContent, null, true);
        decoder.decode(ByteBuffer.wrap(bytes), messageQueue);
        EzyMessage received = messageQueue.poll();

        // then
        Asserts.assertTrue(bytes.length < messageContent.length);
        Asserts.assertTrue(received.getHeader().isCompressed());
        Asserts.assertFalse(received.getHeader().isEncrypted());
        Asserts.assertEquals(decoder.decode(received), message);
        Asserts.assertEquals(decoder.decode(received, null), message);
    }

    @Test
    public void compressAndEncryptTest() throws Exception {
        // given
        MsgPackCodecCreator codecCreator = new MsgPackCodecCreator(true);
        EzyObjectToByteEncoder encoder = codecCreator.newEncoder();
        EzyByteToObjectDecoder decoder = codecCreator.newDecoder(100000);

        String message = newLongMessage();
        byte[] key = EzyAesCrypt.randomKey();
        Queue<EzyMessage> messageQueue = new LinkedList<>();

        // when
        byte[] messageContent = encoder.toMessageContent(message);
        byte[] compressedContent = encoder.compressMessageContent(messageContent);
        byte[] bytes = encoder.encryptMessageContent(compressedContent, key, true);
        decoder.decode(ByteBuffer.wrap(bytes), messageQueue);
        EzyMessage received = messageQueue.poll();

        // then
        Asserts.assertTrue(received.getHeader().isCompressed());
        Asserts.assertTrue(received.getHeader().isEncrypted());
        Asserts.assertEquals(decoder.decode(received, key), message);
    }

    @Test
    public void decompressOverMaxSizeTest() throws Exception {
        // given
        MsgPackCodecCreator codecCreator = new MsgPackCodecCreator(false);
        EzyObjectToByteEncoder encoder = codecCreator.newEncoder();
        EzyByteToObjectDecoder decoder = codecCreator.newDecoder(1024);

        String message = newLongMessage();
        Queue<EzyMessage> messageQueue = new LinkedList<>();

        byte[] messageContent = encoder.toMessageContent(message);
        byte[] compressedContent = encoder.compressMessageContent(messageContent);
        byte[] bytes = encoder.encryptMessageContent(compressedContent, null, true);
        decoder.decode(ByteBuffer.wrap(bytes), messageQueue);
        EzyMessage received = messageQueue.poll();

        // when
        Throwable e = Asserts.assertThrows(() -> decoder.decode(received, null));

        // then
        Asserts.assertEqualsType(e, EzyMaxRequestSizeException.class);
    }

    @Test
    public void decodeInvalidCompressedContentTest() throws Exception {
        // given
        MsgPackCodecCreator codecCreator = new MsgPackCodecCreator(false);
        EzyObjectToByteEncoder encoder = codecCreator.newEncoder();
        EzyByteToObjectDecoder decoder = codecCreator.newDecoder(100000);
        byte[] invalidContent = new byte[] {(byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF};
        byte[] bytes = encoder.encryptMessageContent(invalidContent, null, true);
        Queue<EzyMessage> messageQueue = new LinkedList<>();
        decoder.decode(ByteBuffer.wrap(bytes), messageQueue);
        EzyMessage received = messageQueue.poll();

        // when
        Throwable e = Asserts.assertThrows(() -> decoder.decode(received));

        // then
        Asserts.assertEqualsType(e, EzyCodecException.class);
    }

    @Test
    public void notCompressedMessageTest() throws Exception {
        // given
        MsgPackCodecCreator codecCreator = new MsgPackCodecCreator(false);
        EzyObjectToByteEncoder encoder = codecCreator.newEncoder();
        EzyByteToObjectDecoder decoder = codecCreator.newDecoder(100000);
        String message = newLongMessage();
        Queue<EzyMessage> messageQueue = new LinkedList<>();

        // when
        byte[] bytes = encoder.encode(message);
        decoder.decode(ByteBuffer.wrap(bytes), messageQueue);
        EzyMessage received = messageQueue.poll();

        // then
        Asserts.assertFalse(received.getHeader().isCompressed());
        Asserts.assertEquals(decoder.decode(received, null), message);
    }

    private static String newLongMessage() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 500; ++i) {
            builder.append("hello world ");
        }
        return builder.toString();
    }
}
