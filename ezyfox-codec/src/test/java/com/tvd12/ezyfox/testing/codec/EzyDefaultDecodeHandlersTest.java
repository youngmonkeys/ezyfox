package com.tvd12.ezyfox.testing.codec;

import com.tvd12.ezyfox.codec.EzyDefaultDecodeHandlers;
import com.tvd12.ezyfox.codec.EzyMessage;
import com.tvd12.ezyfox.exception.EzyMaxRequestSizeException;
import com.tvd12.test.assertion.Asserts;
import org.testng.annotations.Test;

import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.Queue;

public class EzyDefaultDecodeHandlersTest {

    @Test
    public void test() {
        EzyDefaultDecodeHandlers handlers = EzyDefaultDecodeHandlers.builder()
            .maxSize(128)
            .build();
        Queue<EzyMessage> out = new LinkedList<>();
        ByteBuffer buffer = ByteBuffer.allocate(12);
        buffer.put((byte) 0);
        buffer.putShort((short) 9);
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
        buffer.put((byte) 0);
        buffer.putShort((short) 9);
        buffer.put("01234567".getBytes());
        buffer.flip();
        handlers.handle(buffer, out);
        assert out.size() == 0;
    }

    @Test
    public void resetStateAfterErrorTest() {
        // given
        EzyDefaultDecodeHandlers handlers = EzyDefaultDecodeHandlers.builder()
            .maxSize(128)
            .build();
        Queue<EzyMessage> out = new LinkedList<>();
        ByteBuffer invalid = ByteBuffer.allocate(3);
        invalid.put((byte) 0);
        invalid.putShort((short) 1000);
        invalid.flip();

        ByteBuffer valid = ByteBuffer.allocate(12);
        valid.put((byte) 0);
        valid.putShort((short) 9);
        valid.put("012345678".getBytes());
        valid.flip();

        // when
        Throwable e = Asserts.assertThrows(() -> handlers.handle(invalid, out));
        handlers.handle(valid, out);

        // then
        Asserts.assertEqualsType(e, EzyMaxRequestSizeException.class);
        Asserts.assertEquals(out.size(), 1);
        Asserts.assertEquals(out.poll().getContent(), "012345678".getBytes());
    }
}
