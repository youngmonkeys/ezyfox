package com.tvd12.ezyfox.codec.testing;

import java.util.HashMap;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.codec.JacksonCodecCreator;
import com.tvd12.ezyfox.codec.JacksonMessageToByteEncoder;

public class JacksonMessageToByteEncoderTest {

    @Test
    public void test() throws Exception {
        JacksonCodecCreator creator = new JacksonCodecCreator();
        JacksonMessageToByteEncoder encoder = (JacksonMessageToByteEncoder) creator.newEncoder();
        System.out.println(encoder.encode(new HashMap<>()));
        System.out.println(encoder.encode(new HashMap<>(), byte[].class));
    }

}
