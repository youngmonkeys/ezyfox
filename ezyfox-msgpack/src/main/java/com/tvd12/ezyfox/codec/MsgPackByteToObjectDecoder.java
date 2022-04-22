package com.tvd12.ezyfox.codec;

import java.nio.ByteBuffer;
import java.util.Queue;

public class MsgPackByteToObjectDecoder implements EzyByteToObjectDecoder {

    protected final EzyDefaultDecodeHandlers handlers;
    protected final EzyMessageDeserializer deserializer;

    public MsgPackByteToObjectDecoder(
            EzyMessageDeserializer deserializer, int maxSize) {
        this.deserializer = deserializer;
        this.handlers = EzyDefaultDecodeHandlers.builder()
                .maxSize(maxSize)
                .build();
    }

    @Override
    public Object decode(EzyMessage message) throws Exception {
        return deserializer.deserialize(message.getContent());
    }

    @Override
    public Object decode(EzyMessage message, byte[] decryptionKey) throws Exception {
        byte[] encryptedContent = message.getContent();
        if(message.getHeader().isEncrypted())
            encryptedContent = decryptMessageContent(encryptedContent, decryptionKey);
        return deserializer.deserialize(encryptedContent);
    }

    protected byte[] decryptMessageContent(
            byte[] content, byte[] decryptionKey) throws Exception {
        return content;
    }

    @Override
    public void decode(ByteBuffer bytes, Queue<EzyMessage> out) throws Exception {
        handlers.handle(bytes, out);
    }

    @Override
    public void reset() {
        handlers.reset();
    }
}