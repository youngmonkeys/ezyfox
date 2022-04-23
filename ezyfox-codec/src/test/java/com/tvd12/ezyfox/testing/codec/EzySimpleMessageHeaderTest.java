package com.tvd12.ezyfox.testing.codec;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.codec.EzySimpleMessageHeader;

public class EzySimpleMessageHeaderTest {

    @Test
    public void test() {
        EzySimpleMessageHeader header = new EzySimpleMessageHeader(true, true, true, true, true, true);
        assert header.isBigSize();
        assert header.isEncrypted();
        assert header.isCompressed();
        assert header.isText();
        assert header.isRawBytes();
        assert header.isUdpHandshake();
    }
}
