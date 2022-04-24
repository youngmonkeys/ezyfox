package com.tvd12.ezyfox.codec.testing;

import com.tvd12.ezyfox.codec.MsgPackType;
import com.tvd12.ezyfox.codec.MsgPackTypeParser;
import org.testng.annotations.Test;

public class MsgPackTypeParserTest {

    private final MsgPackTypeParser parser = new MsgPackTypeParser();

    @Test
    public void test0() {
        assert parser.parse(-1) == null;
    }

    @Test
    public void test1() {
        assert parser.parse(0x00) == MsgPackType.POSITIVE_FIXINT;
        assert parser.parse(1) == MsgPackType.POSITIVE_FIXINT;
        assert parser.parse(0x7f) == MsgPackType.POSITIVE_FIXINT;
        assert parser.parse(0x7f - 1) == MsgPackType.POSITIVE_FIXINT;
    }

    @Test
    public void test2() {
        assert parser.parse(0xc1) == MsgPackType.NEVER_USED;
        assert parser.parse(0xd4) == MsgPackType.FIXEXT1;
        assert parser.parse(0xd5) == MsgPackType.FIXEXT2;
        assert parser.parse(0xd6) == MsgPackType.FIXEXT4;
        assert parser.parse(0xd7) == MsgPackType.FIXEXT8;
        assert parser.parse(0xd8) == MsgPackType.FIXEXT16;

        assert parser.parse(0xc7) == MsgPackType.EXT8;
        assert parser.parse(0xc8) == MsgPackType.EXT16;
        assert parser.parse(0xc9) == MsgPackType.EXT32;
    }

    @Test
    public void test3() {
        assert parser.parse(256) == null;
    }
}
