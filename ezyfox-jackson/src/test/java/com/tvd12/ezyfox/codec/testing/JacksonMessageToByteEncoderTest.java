package com.tvd12.ezyfox.codec.testing;

import com.tvd12.ezyfox.codec.JacksonCodecCreator;
import com.tvd12.ezyfox.codec.JacksonMessageToByteEncoder;
import com.tvd12.test.assertion.Asserts;
import org.testng.annotations.Test;

import java.util.HashMap;

public class JacksonMessageToByteEncoderTest {

    @Test
    public void encodeHashMapTest() {
        // given
        JacksonCodecCreator creator = new JacksonCodecCreator();
        JacksonMessageToByteEncoder encoder = (JacksonMessageToByteEncoder) creator.newEncoder();

        // when
        byte[] actual = encoder.encode(new HashMap<>());

        // then
        Asserts.assertEquals(actual, new byte[] {123, 125});
    }

    @Test
    public void encodeHashMapBytesTest() {
        // given
        JacksonCodecCreator creator = new JacksonCodecCreator();
        JacksonMessageToByteEncoder encoder = (JacksonMessageToByteEncoder) creator.newEncoder();

        // when
        byte[] actual = encoder.encode(new HashMap<>(), byte[].class);

        // then
        Asserts.assertEquals(actual, new byte[] {123, 125});
    }
}
