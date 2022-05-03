package com.tvd12.ezyfox.codec.testing;

import com.tvd12.ezyfox.codec.MsgPackConstant;
import com.tvd12.test.assertion.Asserts;
import com.tvd12.test.base.BaseTest;
import org.testng.annotations.Test;

import static com.tvd12.ezyfox.codec.MsgPackConstant.*;

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

        Asserts.assertEquals(
            MIN_NEGATIVE_FIXINT,
            (byte) 0xe0,
            false
        );
        Asserts.assertEquals(
            MIN_INT8,
            Byte.MIN_VALUE,
            false
        );
        Asserts.assertEquals(
            MIN_INT16,
            Short.MIN_VALUE,
            false
        );
        Asserts.assertEquals(
            MIN_INT32,
            Integer.MIN_VALUE,
            false
        );
    }

    @Override
    public Class<?> getTestClass() {
        return MsgPackConstant.class;
    }
}
