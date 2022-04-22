package com.tvd12.ezyfox.codec;

import static com.tvd12.ezyfox.codec.MsgPackConstant.MAX_ARRAY16_SIZE;
import static com.tvd12.ezyfox.codec.MsgPackConstant.MAX_FIXARRAY_SIZE;

import com.tvd12.ezyfox.io.EzyBytes;
import com.tvd12.ezyfox.io.EzyCastToByte;

public class MsgPackArraySizeSerializer implements EzyCastToByte {

    private static final MsgPackArraySizeSerializer INSTANCE = new MsgPackArraySizeSerializer();

    private MsgPackArraySizeSerializer() {}

    public static MsgPackArraySizeSerializer getInstance() {
        return INSTANCE;
    }


    public byte[] serialize(int size) {
        if(size <= MAX_FIXARRAY_SIZE)
            return parseFix(size);
        if(size <= MAX_ARRAY16_SIZE)
            return parse16(size);
        return parse32(size);
    }

    private byte[] parseFix(int size) {
        return new byte[] {
            cast(0x90 | size)
        };
    }

    private byte[] parse16(int size) {
        return EzyBytes.getBytes(0xdc, size, 2);
    }

    private byte[] parse32(int size) {
        return EzyBytes.getBytes(0xdd, size, 4);
    }
}