package com.tvd12.ezyfox.codec;

public class EzySimpleMessageDataEncoder implements EzyMessageDataEncoder {

    private final EzyObjectToByteEncoder encoder;

    public EzySimpleMessageDataEncoder(EzyObjectToByteEncoder encoder) {
        this.encoder = encoder;
    }

    @Override
    public byte[] encode(Object data) throws Exception {
        return encoder.encode(data);
    }

    @Override
    public byte[] toMessageContent(Object data) throws Exception {
        return encoder.toMessageContent(data);
    }

    @Override
    public byte[] compressMessageContent(
        byte[] messageContent
    ) throws Exception {
        return encoder.compressMessageContent(messageContent);
    }

    @Override
    public byte[] encryptMessageContent(
        byte[] messageContent,
        byte[] encryptionKey
    ) throws Exception {
        return encoder.encryptMessageContent(
            messageContent,
            encryptionKey
        );
    }

    @Override
    public byte[] encryptMessageContent(
        byte[] messageContent,
        byte[] encryptionKey,
        boolean compressed
    ) throws Exception {
        return encoder.encryptMessageContent(
            messageContent,
            encryptionKey,
            compressed
        );
    }
}
