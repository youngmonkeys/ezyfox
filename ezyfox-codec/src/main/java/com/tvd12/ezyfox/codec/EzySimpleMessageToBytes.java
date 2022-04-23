package com.tvd12.ezyfox.codec;

import java.nio.ByteBuffer;

public class EzySimpleMessageToBytes implements EzyMessageToBytes {

    @SuppressWarnings("unchecked")
    @Override
    public byte[] convert(EzyMessage message) {
        ByteBuffer buffer = newByteBuffer(message);
        writeHeader(buffer, message);
        writeSize(buffer, message);
        writeContent(buffer, message);
        byte[] answer = new byte[buffer.position()];
        buffer.flip();
        buffer.get(answer);
        return answer;
    }

    private void writeHeader(ByteBuffer buffer, EzyMessage message) {
        writeHeader(buffer, message.getHeader());
    }

    private void writeHeader(ByteBuffer buffer, EzyMessageHeader header) {
        byte headerByte = 0;
        headerByte |= header.isBigSize() ? 1 << 0 : 0;
        headerByte |= header.isEncrypted() ? 1 << 1 : 0;
        headerByte |= header.isCompressed() ? 1 << 2 : 0;
        headerByte |= header.isText() ? 1 << 3 : 0;
        buffer.put(headerByte);
    }

    private void writeSize(ByteBuffer buffer, EzyMessage message) {
        if(message.hasBigSize())
            buffer.putInt(message.getSize());
        else
            buffer.putShort((short)message.getSize());
    }

    private void writeContent(ByteBuffer buffer, EzyMessage message) {
        buffer.put(message.getContent());
    }

    private ByteBuffer newByteBuffer(EzyMessage message) {
        int capacity = getCapacity(message);
        return ByteBuffer.allocate(capacity);
    }

    private int getCapacity(EzyMessage message){
        return message.getByteCount();
    }

}
