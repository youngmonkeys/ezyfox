package com.tvd12.ezyfox.codec.testing;

import com.tvd12.ezyfox.codec.JacksonByteToMessageDecoder;
import com.tvd12.ezyfox.codec.JacksonCodecCreator;
import com.tvd12.ezyfox.codec.JacksonMessageToByteEncoder;
import com.tvd12.test.assertion.Asserts;
import org.testng.annotations.Test;

public class JacksonCodecCreatorTest {

    @Test
    public void test() {
        // given
        JacksonCodecCreator creator = new JacksonCodecCreator();

        // when
        Object encoder= creator.newEncoder();
        Object decoder = creator.newDecoder(1000);

        // then
        Asserts.assertTrue(encoder instanceof JacksonMessageToByteEncoder);
        Asserts.assertTrue(decoder instanceof JacksonByteToMessageDecoder);
    }
}
