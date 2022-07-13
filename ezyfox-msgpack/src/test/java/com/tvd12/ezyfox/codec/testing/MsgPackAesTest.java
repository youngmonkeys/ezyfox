package com.tvd12.ezyfox.codec.testing;

import com.tvd12.ezyfox.codec.*;
import com.tvd12.ezyfox.security.EzyAesCrypt;
import com.tvd12.test.assertion.Asserts;
import com.tvd12.test.util.RandomUtil;
import org.testng.annotations.Test;

import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.Queue;

import static org.mockito.Mockito.*;

public class MsgPackAesTest {

    @Test
    public void aesTest() throws Exception {
        // given
        MsgPackCodecCreator codecCreator = new MsgPackCodecCreator(true);
        EzyObjectToByteEncoder encoder = codecCreator.newEncoder();
        EzyByteToObjectDecoder decoder = codecCreator.newDecoder(10000);

        String message = "HelloWorld";
        byte[] key = EzyAesCrypt.randomKey();
        Queue<EzyMessage> messageQueue = new LinkedList<>();

        // when
        byte[] messageContent = encoder.toMessageContent(message);
        byte[] encrypted = encoder.encryptMessageContent(messageContent, key);
        decoder.decode(ByteBuffer.wrap(encrypted), messageQueue);
        String decryption = (String) decoder.decode(messageQueue.poll(), key);

        // then
        Asserts.assertEquals(message, decryption);
    }

    @Test
    public void normalEncode() throws Exception {
        // given
        MsgPackCodecCreator codecCreator = new MsgPackCodecCreator(false);
        EzyObjectToByteEncoder encoder = codecCreator.newEncoder();
        EzyByteToObjectDecoder decoder = codecCreator.newDecoder(10000);

        String message = "HelloWorld";
        byte[] key = EzyAesCrypt.randomKey();
        Queue<EzyMessage> messageQueue = new LinkedList<>();

        // when
        byte[] messageContent = encoder.toMessageContent(message);
        byte[] encrypted = encoder.encryptMessageContent(messageContent, key);
        decoder.decode(ByteBuffer.wrap(encrypted), messageQueue);
        decoder.decode(messageQueue.peek(), null);
        String decryption = (String) decoder.decode(messageQueue.poll(), key);

        // then
        Asserts.assertEquals(message, decryption);
    }

    @Test
    public void normalEncodeDecode() throws Exception {
        // given
        MsgPackCodecCreator codecCreator = new MsgPackCodecCreator(false);
        EzyObjectToByteEncoder encoder = codecCreator.newEncoder();
        EzyByteToObjectDecoder decoder = codecCreator.newDecoder(10000);

        String message = "HelloWorld";
        Queue<EzyMessage> messageQueue = new LinkedList<>();

        // when
        byte[] messageContent = encoder.toMessageContent(message);
        byte[] encrypted = encoder.encryptMessageContent(messageContent, null);
        decoder.decode(ByteBuffer.wrap(encrypted), messageQueue);
        String decryption = (String) decoder.decode(messageQueue.poll(), null);

        // then
        Asserts.assertEquals(message, decryption);
    }

    @Test
    public void decryptMessageContentNoKeyTest() throws Exception {
        // given
        EzyMessageDeserializer deserializer = mock(EzyMessageDeserializer.class);
        MsgPackAesByteToObjectDecoder sut = new MsgPackAesByteToObjectDecoder(deserializer, 100);

        // when
        byte[] messageContent = RandomUtil.randomShortByteArray();
        EzyMessage message = mock(EzyMessage.class);
        EzyMessageHeader header = mock(EzyMessageHeader.class);
        when(header.isEncrypted()).thenReturn(true);
        when(message.getHeader()).thenReturn(header);
        when(message.getContent()).thenReturn(messageContent);

        sut.decode(message, null);

        // then
        verify(deserializer, times(1)).deserialize(messageContent);
    }

    @Test
    public void noDecryptMessageContentNoKeyTest() throws Exception {
        // given
        EzyMessageDeserializer deserializer = mock(EzyMessageDeserializer.class);
        MsgPackAesByteToObjectDecoder sut = new MsgPackAesByteToObjectDecoder(deserializer, 100);

        // when
        byte[] messageContent = RandomUtil.randomShortByteArray();
        EzyMessage message = mock(EzyMessage.class);
        EzyMessageHeader header = mock(EzyMessageHeader.class);
        when(header.isEncrypted()).thenReturn(false);
        when(message.getHeader()).thenReturn(header);
        when(message.getContent()).thenReturn(messageContent);

        sut.decode(message, null);

        // then
        verify(deserializer, times(1)).deserialize(messageContent);
    }
}
