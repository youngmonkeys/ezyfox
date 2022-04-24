package com.tvd12.ezyfox.codec.testing;

import com.tvd12.ezyfox.codec.MsgPackCodecCreator;
import org.testng.annotations.Test;

public class MsgPackCodecCreatorTest {

    @Test
    public void test() {
        MsgPackCodecCreator codecCreator = new MsgPackCodecCreator();
        codecCreator.newDecoder(100);
        codecCreator.newEncoder();
    }
}
