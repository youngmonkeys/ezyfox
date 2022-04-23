package com.tvd12.ezyfox.codec;

import com.tvd12.ezyfox.io.EzyBytes;
import com.tvd12.ezyfox.io.EzyCastToByte;

public class MsgPackDoubleSerializer implements EzyCastToByte {

    private static final MsgPackDoubleSerializer INSTANCE = new MsgPackDoubleSerializer();

    private MsgPackDoubleSerializer() {}

    public static MsgPackDoubleSerializer getInstance() {
        return INSTANCE;
    }

    public byte[] serialize(double value) {
        return EzyBytes.getBytes(cast(0xcb), value);
    }

}
