package com.tvd12.ezyfox.codec.testing;

import com.tvd12.ezyfox.codec.JacksonCodecCreator;

import org.testng.annotations.Test;

public class JacksonCodecCreatorTest {

    @Test
    public void test() {
        JacksonCodecCreator creator = new JacksonCodecCreator();
        creator.newDecoder(1000);
        creator.newEncoder();
    }

}
