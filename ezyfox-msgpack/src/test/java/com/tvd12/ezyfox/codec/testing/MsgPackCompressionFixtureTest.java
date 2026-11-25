package com.tvd12.ezyfox.codec.testing;

import com.tvd12.ezyfox.codec.EzyByteToObjectDecoder;
import com.tvd12.ezyfox.codec.EzyDeflateCompressor;
import com.tvd12.ezyfox.codec.EzyMessage;
import com.tvd12.ezyfox.codec.MsgPackCodecCreator;
import com.tvd12.ezyfox.entity.EzyArray;
import com.tvd12.test.assertion.Asserts;
import org.testng.annotations.Test;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.LinkedList;
import java.util.Queue;

public class MsgPackCompressionFixtureTest {

    public static final String COMPRESSED_MESSAGE_BASE64 =
        "BAA2m8R4i9U/taoyLb9CIbkov7hYIScxL700MT1VITk/t6Aotbg4Mz9PYVTFqIpRFaMqRlUQoQIA";
    public static final String RAW_DEFLATE_BASE64 =
        "y0jNyclXKEosV0hJTctJLElVyBgVGRUBiwAA";

    @Test
    public void decodeCompressedMessageFixtureTest() throws Exception {
        // given
        EzyByteToObjectDecoder decoder = new MsgPackCodecCreator(false)
            .newDecoder(100000);
        byte[] bytes = Base64.getDecoder().decode(COMPRESSED_MESSAGE_BASE64);
        Queue<EzyMessage> queue = new LinkedList<>();

        // when
        decoder.decode(ByteBuffer.wrap(bytes), queue);
        EzyMessage message = queue.poll();
        EzyArray data = (EzyArray) decoder.decode(message, null);

        // then
        Asserts.assertTrue(message.getHeader().isCompressed());
        Asserts.assertFalse(message.getHeader().isEncrypted());
        Asserts.assertEquals(data.get(0, int.class), 1);
        Asserts.assertEquals(data.get(1, String.class), fixtureText());
    }

    @Test
    public void decompressRawDeflateFixtureTest() {
        // given
        byte[] compressed = Base64.getDecoder().decode(RAW_DEFLATE_BASE64);

        // when
        byte[] decompressed = EzyDeflateCompressor.decompress(compressed, 0);

        // then
        Asserts.assertEquals(
            new String(decompressed, StandardCharsets.UTF_8),
            repeat("hello raw deflate ", 30)
        );
    }

    public static String fixtureText() {
        return repeat("ezyfox cross language compression ", 40).trim();
    }

    private static String repeat(String value, int times) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < times; ++i) {
            builder.append(value);
        }
        return builder.toString();
    }
}
