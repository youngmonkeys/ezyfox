package com.tvd12.ezyfox.testing.codec;

import com.tvd12.ezyfox.codec.EzyDecodeState;
import com.tvd12.ezyfox.codec.EzyIDecodeState;
import com.tvd12.test.assertion.Asserts;
import com.tvd12.test.base.BaseTest;
import org.testng.annotations.Test;

public class EzyDecodeStateTest extends BaseTest {

    @Test
    public void test() {
        EzyIDecodeState state = EzyDecodeState.PREPARE_MESSAGE;
        assert state.getId() == 0;
        Asserts.assertEquals(
            EzyDecodeState.valueOf("PREPARE_MESSAGE"),
            EzyDecodeState.PREPARE_MESSAGE
        );
    }
}
