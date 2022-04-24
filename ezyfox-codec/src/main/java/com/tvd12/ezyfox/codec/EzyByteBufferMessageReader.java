package com.tvd12.ezyfox.codec;

import com.tvd12.ezyfox.io.EzyBytes;
import com.tvd12.ezyfox.io.EzyInts;

import java.nio.ByteBuffer;

public class EzyByteBufferMessageReader extends EzyMessageReader<ByteBuffer> {

    @Override
    protected byte readByte(ByteBuffer buffer) {
        return buffer.get();
    }

    @Override
    protected int remaining(ByteBuffer buffer) {
        return buffer.remaining();
    }

    @Override
    protected int readMessageSize(ByteBuffer buffer) {
        sizeBytes = EzyBytes.copy(buffer, getSizeLength());
        return EzyInts.bin2uint(sizeBytes);
    }

    @Override
    protected void readMessageContent(ByteBuffer buffer, byte[] content, int offset, int length) {
        buffer.get(content, offset, length);
    }
}
