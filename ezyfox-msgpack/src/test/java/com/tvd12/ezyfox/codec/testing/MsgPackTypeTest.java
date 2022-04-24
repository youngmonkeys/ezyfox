package com.tvd12.ezyfox.codec.testing;

import com.tvd12.ezyfox.codec.MsgPackType;
import com.tvd12.test.base.BaseTest;
import org.testng.annotations.Test;

public class MsgPackTypeTest extends BaseTest {

    @Test
    public void test() {
        assert MsgPackType.POSITIVE_FIXINT.getId() == 0;
        assert MsgPackType.POSITIVE_FIXINT.getName().equals("POSITIVE_FIXINT");
        MsgPackType.valueOf("POSITIVE_FIXINT");
    }
}
