package com.tvd12.ezyfox.codec;

public abstract class EzyAbstractToBytesSerializer
    extends EzyAbstractSerializer<byte[]> {

    @Override
    public byte[] serialize(Object value) {
        return value == null
            ? parseNil()
            : parseNotNull(value);
    }
}
