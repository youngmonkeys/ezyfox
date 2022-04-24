package com.tvd12.ezyfox.codec;

public interface EzyMessageDataEncoder {

    byte[] encode(Object data) throws Exception;

    default byte[] toMessageContent(Object data) throws Exception {
        throw new UnsupportedOperationException("unsupported");
    }

    default byte[] encryptMessageContent(
        byte[] messageContent, byte[] encryptionKey) throws Exception {
        throw new UnsupportedOperationException("unsupported");
    }
}
