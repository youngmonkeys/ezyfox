package com.tvd12.ezyfox.codec;

import static com.tvd12.ezyfox.codec.MsgPackConstant.MAX_FIXMAP_SIZE;
import static com.tvd12.ezyfox.codec.MsgPackConstant.MAX_MAP16_SIZE;

import com.tvd12.ezyfox.io.EzyBytes;
import com.tvd12.ezyfox.io.EzyCastToByte;

public class MsgPackMapSizeSerializer implements EzyCastToByte {

    private static final MsgPackMapSizeSerializer INSTANCE = new MsgPackMapSizeSerializer();

    private MsgPackMapSizeSerializer() {}

    public static MsgPackMapSizeSerializer getInstance() {
        return INSTANCE;
    }

    public byte[] serialize(int size) {
        if(size <= MAX_FIXMAP_SIZE)
            return parseFix(size);
        if(size <= MAX_MAP16_SIZE)
            return parse16(size);
        return parse32(size);
    }

    private byte[] parseFix(int size) {
        return new byte[] {
            cast(0x80 | size)
        };
    }

    private byte[] parse16(int size) {
        return EzyBytes.getBytes(0xde, size, 2);
    }

    private byte[] parse32(int size) {
        return EzyBytes.getBytes(0xdf, size, 4);
    }
}
