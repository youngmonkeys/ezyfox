package com.tvd12.ezyfox.codec;

import com.tvd12.ezyfox.io.EzyStrings;

import java.nio.ByteBuffer;

public interface EzyObjectDeserializer {

    <T> T deserialize(ByteBuffer buffer);

    default <T> T read(ByteBuffer buffer) {
        return deserialize(buffer);
    }

    default <T> T read(byte[] buffer) {
        return read(ByteBuffer.wrap(buffer));
    }

    default <T> T deserialize(byte[] data) {
        return deserialize(ByteBuffer.wrap(data));
    }

    default <T> T deserialize(String text) {
        return deserialize(EzyStrings.getUtfBytes(text));
    }
}
