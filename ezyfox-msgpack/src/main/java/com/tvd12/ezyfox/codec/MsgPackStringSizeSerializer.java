package com.tvd12.ezyfox.codec;

import static com.tvd12.ezyfox.codec.MsgPackConstant.MAX_FIXSTR_SIZE;
import static com.tvd12.ezyfox.codec.MsgPackConstant.MAX_STR16_SIZE;
import static com.tvd12.ezyfox.codec.MsgPackConstant.MAX_STR8_SIZE;

import com.tvd12.ezyfox.io.EzyBytes;
import com.tvd12.ezyfox.io.EzyCastToByte;

public class MsgPackStringSizeSerializer implements EzyCastToByte {

    private static final MsgPackStringSizeSerializer INSTANCE = new MsgPackStringSizeSerializer();

    private MsgPackStringSizeSerializer() {}

    public static MsgPackStringSizeSerializer getInstance() {
        return INSTANCE;
    }

    public byte[] serialize(int size) {
        if(size <= MAX_FIXSTR_SIZE)
            return parseFix(size);
        if(size <= MAX_STR8_SIZE)
            return parse8(size);
        if(size <= MAX_STR16_SIZE)
            return parse16(size);
        return parse32(size);
    }

    private byte[] parseFix(int size) {
        return new byte[] {
            cast(0xa0 | size)
        };
    }

    private byte[] parse8(int size) {
        return EzyBytes.getBytes(0xd9, size, 1);
    }

    private byte[] parse16(int size) {
        return EzyBytes.getBytes(0xda, size, 2);
    }

    private byte[] parse32(int size) {
        return EzyBytes.getBytes(0xdb, size, 4);
    }
}
