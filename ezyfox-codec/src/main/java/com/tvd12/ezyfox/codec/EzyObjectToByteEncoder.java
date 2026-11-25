package com.tvd12.ezyfox.codec;

public interface EzyObjectToByteEncoder {

    byte[] encode(Object msg) throws Exception;

    default byte[] toMessageContent(Object data) throws Exception {
        throw new UnsupportedOperationException("unsupported");
    }

    default byte[] compressMessageContent(
        byte[] messageContent
    ) throws Exception {
        return EzyDeflateCompressor.compress(messageContent);
    }

    default byte[] encryptMessageContent(
        byte[] messageContent,
        byte[] encryptionKey
    ) throws Exception {
        throw new UnsupportedOperationException("unsupported");
    }

    default byte[] encryptMessageContent(
        byte[] messageContent,
        byte[] encryptionKey,
        boolean compressed
    ) throws Exception {
        if (compressed) {
            throw new UnsupportedOperationException("unsupported");
        }
        return encryptMessageContent(messageContent, encryptionKey);
    }
}
