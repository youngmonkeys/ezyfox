package com.tvd12.ezyfox.codec;

import com.tvd12.ezyfox.sercurity.EzyAesCrypt;

public class MsgPackAesByteToObjectDecoder extends MsgPackByteToObjectDecoder {

    private final EzyAesCrypt cryptor = EzyAesCrypt.getDefault();

    public MsgPackAesByteToObjectDecoder(
        EzyMessageDeserializer deserializer,
        int maxSize
    ) {
        super(deserializer, maxSize);
    }

    @Override
    protected byte[] decryptMessageContent(
        byte[] content,
        byte[] decryptionKey
    ) throws Exception {
        if (decryptionKey == null) {
            return content;
        }
        return cryptor.decrypt(content, decryptionKey);
    }
}
