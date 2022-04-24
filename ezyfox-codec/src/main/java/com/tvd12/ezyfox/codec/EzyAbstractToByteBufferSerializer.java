package com.tvd12.ezyfox.codec;

import java.nio.ByteBuffer;

public abstract class EzyAbstractToByteBufferSerializer
    extends EzyAbstractSerializer<ByteBuffer> {

    @Override
    public byte[] serialize(Object value) {
        ByteBuffer buffer = write(value);
        byte[] bytes = new byte[buffer.remaining()];
        buffer.get(bytes);
        return bytes;
    }

    @Override
    public ByteBuffer write(Object value) {
        return value == null
            ? parseNil()
            : parseNotNull(value);
    }
}
