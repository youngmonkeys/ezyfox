package com.tvd12.ezyfox.codec.testing;

import com.tvd12.ezyfox.codec.EzyMessage;
import com.tvd12.ezyfox.codec.EzyMessageDeserializer;
import com.tvd12.ezyfox.codec.MsgPackByteToObjectDecoder;
import org.testng.annotations.Test;

import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

import static org.mockito.Mockito.mock;

public class MsgPackByteToObjectDecoderTest {

    @Test
    public void test() throws Exception {
        EzyMessageDeserializer deserializer = mock(EzyMessageDeserializer.class);
        MsgPackByteToObjectDecoder decoder = new MsgPackByteToObjectDecoder(deserializer, 128);
        ByteBuffer buffer = ByteBuffer.allocate(12);
        buffer.put((byte) 0);
        buffer.putShort((short) 9);
        buffer.put("012345678".getBytes());
        buffer.flip();
        Queue<EzyMessage> out = new LinkedList<>();
        decoder.decode(buffer, out);
        decoder.decode(Objects.requireNonNull(out.poll()));
        decoder.reset();
    }
}
