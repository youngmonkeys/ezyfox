package com.tvd12.ezyfox.codec;

import java.nio.ByteBuffer;
import java.util.Queue;

public class MsgPackByteToObjectDecoder implements EzyByteToObjectDecoder {

    protected final int maxSize;
    protected final EzyDefaultDecodeHandlers handlers;
    protected final EzyMessageDeserializer deserializer;

    public MsgPackByteToObjectDecoder(
        EzyMessageDeserializer deserializer,
        int maxSize
    ) {
        this.maxSize = maxSize;
        this.deserializer = deserializer;
        this.handlers = EzyDefaultDecodeHandlers.builder()
            .maxSize(maxSize)
            .build();
    }

    @Override
    public Object decode(EzyMessage message) {
        byte[] content = message.getContent();
        if (message.getHeader().isCompressed()) {
            content = decompressMessageContent(content);
        }
        return deserializer.deserialize(content);
    }

    @Override
    public Object decode(
        EzyMessage message,
        byte[] decryptionKey
    ) throws Exception {
        byte[] content = message.getContent();
        EzyMessageHeader header = message.getHeader();
        if (header.isEncrypted()) {
            content = decryptMessageContent(
                content,
                decryptionKey
            );
        }
        if (header.isCompressed()) {
            content = decompressMessageContent(content);
        }
        return deserializer.deserialize(content);
    }

    @Override
    public void decode(ByteBuffer bytes, Queue<EzyMessage> out) {
        handlers.handle(bytes, out);
    }

    protected byte[] decompressMessageContent(byte[] content) {
        return EzyDeflateCompressor.decompress(content, maxSize);
    }

    protected byte[] decryptMessageContent(
        byte[] content,
        byte[] decryptionKey
    ) throws Exception {
        return content;
    }

    @Override
    public void reset() {
        handlers.reset();
    }
}
