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
        int sizeLength = getSizeLength();
        if (isRawBytes()) {
            sizeBytes = EzyBytes.copy(buffer, sizeLength);
            return EzyInts.bin2uint(sizeBytes);
        }
        int answer = 0;
        for (int i = 0; i < sizeLength; ++i) {
            answer = (answer << 8) | (buffer.get() & 0xFF);
        }
        return answer;
    }

    @Override
    protected void readMessageContent(ByteBuffer buffer, byte[] content, int offset, int length) {
        buffer.get(content, offset, length);
    }
}
