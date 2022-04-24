package com.tvd12.ezyfox.testing.codec;

import com.tvd12.ezyfox.codec.EzyDecodeState;
import com.tvd12.ezyfox.codec.EzyIDecodeState;
import com.tvd12.test.assertion.Asserts;
import com.tvd12.test.base.BaseTest;
import org.testng.annotations.Test;

public class EzyDecodeState1Test extends BaseTest {

    @Test
    public void test() {
        Asserts.assertEquals(EzyDecodeState.PREPARE_MESSAGE.getId(), 0);
        Asserts.assertEquals(EzyDecodeState.READ_MESSAGE_HEADER.getId(), 1);
        Asserts.assertEquals(EzyDecodeState.READ_MESSAGE_SIZE.getId(), 2);
        Asserts.assertEquals(EzyDecodeState.READ_MESSAGE_CONTENT.getId(), 3);
    }
}
