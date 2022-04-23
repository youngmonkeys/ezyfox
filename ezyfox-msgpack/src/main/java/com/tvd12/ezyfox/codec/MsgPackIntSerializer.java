package com.tvd12.ezyfox.codec;

import static com.tvd12.ezyfox.codec.MsgPackConstant.MAX_POSITIVE_FIXINT;
import static com.tvd12.ezyfox.codec.MsgPackConstant.MAX_UINT16;
import static com.tvd12.ezyfox.codec.MsgPackConstant.MAX_UINT32;
import static com.tvd12.ezyfox.codec.MsgPackConstant.MAX_UINT8;
import static com.tvd12.ezyfox.codec.MsgPackConstant.MIN_INT16;
import static com.tvd12.ezyfox.codec.MsgPackConstant.MIN_INT32;
import static com.tvd12.ezyfox.codec.MsgPackConstant.MIN_INT8;
import static com.tvd12.ezyfox.codec.MsgPackConstant.MIN_NEGATIVE_FIXINT;

import com.tvd12.ezyfox.io.EzyBytes;
import com.tvd12.ezyfox.io.EzyCastToByte;

public class MsgPackIntSerializer implements EzyCastToByte {

    private static final MsgPackIntSerializer INSTANCE = new MsgPackIntSerializer();

    private MsgPackIntSerializer() {}

    public static MsgPackIntSerializer getInstance() {
        return INSTANCE;
    }

    public byte[] serialize(long value) {
        return value >= 0
                ? parsePositive(value)
                : parseNegative(value);
    }

    private byte[] parsePositive(long value) {
        if(value <= MAX_POSITIVE_FIXINT)
            return parsePositiveFix(value);
        if(value <= MAX_UINT8)
            return parseU8(value);
        if(value <= MAX_UINT16)
            return parseU16(value);
        if(value <= MAX_UINT32)
            return parseU32(value);
        return parseU64(value);
    }

    private byte[] parsePositiveFix(long value) {
        return new byte[] {cast(0x0 | value)};
    }

    private byte[] parseU8(long value) {
        return EzyBytes.getBytes(0xcc, value, 1);
    }

    private byte[] parseU16(long value) {
        return EzyBytes.getBytes(0xcd, value, 2);
    }

    private byte[] parseU32(long value) {
        return EzyBytes.getBytes(0xce, value, 4);
    }

    private byte[] parseU64(long value) {
        return EzyBytes.getBytes(0xcf, value, 8);
    }

    private byte[] parseNegative(long value) {
        if(value >= MIN_NEGATIVE_FIXINT)
            return parseNegativeFix(value);
        if(value >= MIN_INT8)
            return parse8(value);
        if(value >= MIN_INT16)
            return parse16(value);
        if(value >= MIN_INT32)
            return parse32(value);
        return parse64(value);
    }

    private byte[] parseNegativeFix(long value) {
        return new byte[] {cast(0xe0 | value)};
    }

    private byte[] parse8(long value) {
        return EzyBytes.getBytes(0xd0, value, 1);
    }

    private byte[] parse16(long value) {
        return EzyBytes.getBytes(0xd1, value, 2);
    }

    private byte[] parse32(long value) {
        return EzyBytes.getBytes(0xd2, value, 4);
    }

    private byte[] parse64(long value) {
        return EzyBytes.getBytes(0xd3, value, 8);
    }
}
