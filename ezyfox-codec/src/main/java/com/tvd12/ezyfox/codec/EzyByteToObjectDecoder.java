package com.tvd12.ezyfox.codec;

import com.tvd12.ezyfox.util.EzyResettable;

import java.nio.ByteBuffer;
import java.util.Queue;

public interface EzyByteToObjectDecoder extends EzyResettable {

    Object decode(EzyMessage message) throws Exception;

    void decode(ByteBuffer bytes, Queue<EzyMessage> queue) throws Exception;

    default Object decode(EzyMessage message, byte[] encryptionKey) throws Exception {
        throw new UnsupportedOperationException("unsupported");
    }
}
