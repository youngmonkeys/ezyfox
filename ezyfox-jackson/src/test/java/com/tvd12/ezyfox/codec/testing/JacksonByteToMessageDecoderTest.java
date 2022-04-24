package com.tvd12.ezyfox.codec.testing;

import com.tvd12.ezyfox.codec.JacksonByteToMessageDecoder;
import com.tvd12.ezyfox.codec.JacksonCodecCreator;
import com.tvd12.ezyfox.util.EzyEntityArrays;
import com.tvd12.test.assertion.Asserts;
import org.testng.annotations.Test;

import java.nio.charset.StandardCharsets;

public class JacksonByteToMessageDecoderTest {

    @Test
    public void decodeArrayTest() {
        // given
        JacksonCodecCreator creator = new JacksonCodecCreator();
        JacksonByteToMessageDecoder decoder = (JacksonByteToMessageDecoder) creator.newDecoder(128);

        // when
        Object actual = decoder.decode("[1, 2, 3]");

        // then
        Asserts.assertEquals(
            actual,
            EzyEntityArrays.newArray(1, 2, 3)
        );
    }

    @Test
    public void decodeArrayByBytesTest() {
        // given
        JacksonCodecCreator creator = new JacksonCodecCreator();
        JacksonByteToMessageDecoder decoder = (JacksonByteToMessageDecoder) creator.newDecoder(128);

        // when
        Object actual = decoder.decode("[1, 2, 3]".getBytes(StandardCharsets.UTF_8));

        // then
        Asserts.assertEquals(
            actual,
            EzyEntityArrays.newArray(1, 2, 3)
        );
    }
}
