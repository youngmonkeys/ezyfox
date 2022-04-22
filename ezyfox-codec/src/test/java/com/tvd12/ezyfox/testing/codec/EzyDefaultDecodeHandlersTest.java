package com.tvd12.ezyfox.testing.codec;

import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.Queue;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.codec.EzyDefaultDecodeHandlers;
import com.tvd12.ezyfox.codec.EzyMessage;

public class EzyDefaultDecodeHandlersTest {

    @Test
    public void test() {
        EzyDefaultDecodeHandlers handlers = EzyDefaultDecodeHandlers.builder()
                .maxSize(128)
                .build();
        Queue<EzyMessage> out = new LinkedList<>();
        ByteBuffer buffer = ByteBuffer.allocate(12);
        buffer.put((byte)0);
        buffer.putShort((short)9);
        buffer.put("012345678".getBytes());
        buffer.flip();
        handlers.handle(buffer, out);
        assert out.size() > 0;
    }

    @Test
    public void test2() {
        EzyDefaultDecodeHandlers handlers = EzyDefaultDecodeHandlers.builder()
                .maxSize(128)
                .build();
        Queue<EzyMessage> out = new LinkedList<>();
        ByteBuffer buffer = ByteBuffer.allocate(12);
        buffer.put((byte)0);
        buffer.putShort((short)9);
        buffer.put("01234567".getBytes());
        buffer.flip();
        handlers.handle(buffer, out);
        assert out.size() == 0;
    }
}