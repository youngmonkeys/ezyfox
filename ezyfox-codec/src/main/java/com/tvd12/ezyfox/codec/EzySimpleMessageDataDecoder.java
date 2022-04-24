package com.tvd12.ezyfox.codec;

import com.tvd12.ezyfox.callback.EzyCallback;

import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.Queue;

public class EzySimpleMessageDataDecoder
    extends EzyAbstractMessageDataDecoder<EzyByteToObjectDecoder>
    implements EzyMessageDataDecoder {

    private final Queue<EzyMessage> queue;

    public EzySimpleMessageDataDecoder(EzyByteToObjectDecoder decoder) {
        super(decoder);
        this.queue = new LinkedList<>();
    }

    @Override
    public Object decode(EzyMessage message, byte[] decryptionKey) throws Exception {
        return decoder.decode(message, decryptionKey);
    }

    @Override
    public void decode(
        byte[] bytes, EzyCallback<EzyMessage> callback) throws Exception {
        preDecode(bytes);
        decoder.decode(buffer, queue);
        handleQueue(callback);
        postDecode();
    }

    private void handleQueue(EzyCallback<EzyMessage> callback) throws Exception {
        while (queue.size() > 0 && active) {
            do {
                EzyMessage message = queue.poll();
                callback.call(message);
            } while (queue.size() > 0);

            if (buffer.hasRemaining()) {
                decoder.decode(buffer, queue);
            }
        }
    }

    private void preDecode(byte[] bytes) {
        if (buffer == null) {
            buffer = newBuffer(bytes);
        } else {
            buffer = mergeBytes(bytes);
        }
    }

    private void postDecode() {
        buffer = getRemainBytes(buffer);
    }

    private ByteBuffer newBuffer(byte[] bytes) {
        return ByteBuffer.wrap(bytes);
    }

    private ByteBuffer mergeBytes(byte[] bytes) {
        int capacity = buffer.remaining() + bytes.length;
        ByteBuffer merge = ByteBuffer.allocate(capacity).put(buffer).put(bytes);
        merge.flip();
        return merge;
    }

    private ByteBuffer getRemainBytes(ByteBuffer old) {
        if (!old.hasRemaining()) {
            return null;
        }
        byte[] bytes = new byte[old.remaining()];
        old.get(bytes);
        return ByteBuffer.wrap(bytes);
    }

    @Override
    public void reset() {
        queue.clear();
    }
}
