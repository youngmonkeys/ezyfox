package com.tvd12.ezyfox.codec.testing;

import static com.tvd12.ezyfox.codec.MsgPackConstant.MAX_ARRAY16_SIZE;
import static com.tvd12.ezyfox.codec.MsgPackConstant.MAX_BIN16_SIZE;
import static com.tvd12.ezyfox.codec.MsgPackConstant.MAX_BIN32_SIZE;
import static com.tvd12.ezyfox.codec.MsgPackConstant.MAX_BIN8_SIZE;
import static com.tvd12.ezyfox.codec.MsgPackConstant.MAX_FIXARRAY_SIZE;
import static com.tvd12.ezyfox.codec.MsgPackConstant.MAX_FIXMAP_SIZE;
import static com.tvd12.ezyfox.codec.MsgPackConstant.MAX_FIXSTR_SIZE;
import static com.tvd12.ezyfox.codec.MsgPackConstant.MAX_MAP16_SIZE;
import static com.tvd12.ezyfox.codec.MsgPackConstant.MAX_POSITIVE_FIXINT;
import static com.tvd12.ezyfox.codec.MsgPackConstant.MAX_SMALL_MESSAGE_SIZE;
import static com.tvd12.ezyfox.codec.MsgPackConstant.MAX_STR16_SIZE;
import static com.tvd12.ezyfox.codec.MsgPackConstant.MAX_STR32_SIZE;
import static com.tvd12.ezyfox.codec.MsgPackConstant.MAX_STR8_SIZE;
import static com.tvd12.ezyfox.codec.MsgPackConstant.MAX_UINT16;
import static com.tvd12.ezyfox.codec.MsgPackConstant.MAX_UINT32;
import static com.tvd12.ezyfox.codec.MsgPackConstant.MAX_UINT8;
import static com.tvd12.ezyfox.codec.MsgPackConstant.MIN_INT16;
import static com.tvd12.ezyfox.codec.MsgPackConstant.MIN_INT32;
import static com.tvd12.ezyfox.codec.MsgPackConstant.MIN_INT8;
import static com.tvd12.ezyfox.codec.MsgPackConstant.MIN_NEGATIVE_FIXINT;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.codec.MsgPackConstant;
import com.tvd12.test.base.BaseTest;

public class MsgPackConstantTest extends BaseTest {

    @Test
    public void test() {
        System.out.println(MAX_POSITIVE_FIXINT);//     = EzyMath.bin2int(7);
        System.out.println(MAX_UINT8);//                 = EzyMath.bin2int(8);
        System.out.println(MAX_UINT16);//                 = EzyMath.bin2int(16);
        System.out.println(MAX_UINT32);//                 = EzyMath.bin2int(32);
        System.out.println(MIN_NEGATIVE_FIXINT);//     = -EzyMath.bin2int(5);
        System.out.println(MIN_INT8);//                 = -EzyMath.bin2int(7);
        System.out.println(MIN_INT16);//                 = -EzyMath.bin2int(15);
        System.out.println(MIN_INT32);//                 = -EzyMath.bin2int(31);
        System.out.println(MAX_FIXMAP_SIZE);//             = EzyMath.bin2int(4);
        System.out.println(MAX_FIXARRAY_SIZE);//         = EzyMath.bin2int(4);
        System.out.println(MAX_ARRAY16_SIZE);//         = EzyMath.bin2int(16);
        System.out.println(MAX_FIXSTR_SIZE);//             = EzyMath.bin2int(5);
        System.out.println(MAX_STR8_SIZE);//             = EzyMath.bin2int(8);
        System.out.println(MAX_STR16_SIZE);//             = EzyMath.bin2int(16);
        System.out.println(MAX_STR32_SIZE);//             = EzyMath.bin2int(31);
        System.out.println(MAX_MAP16_SIZE);//             = EzyMath.bin2int(16);
        System.out.println(MAX_BIN8_SIZE);//             = EzyMath.bin2int(8);
        System.out.println(MAX_BIN16_SIZE);//             = EzyMath.bin2int(16);
        System.out.println(MAX_BIN32_SIZE);//             = EzyMath.bin2int(31);
        System.out.println(MAX_SMALL_MESSAGE_SIZE);//    = EzyMath.bin2int(16);
    }

    @Override
    public Class<?> getTestClass() {
        return MsgPackConstant.class;
    }

}
