package com.tvd12.ezyfox.testing.constant;

import com.tvd12.ezyfox.constant.EzyLogLevel;
import com.tvd12.test.assertion.Asserts;
import org.testng.annotations.Test;

public class EzyLogLevelTest {

    @Test
    public void test() {
        // given
        EzyLogLevel sut = EzyLogLevel.ofName("info");

        // then
        Asserts.assertEquals(sut.getId(), 3);
        Asserts.assertEquals(sut.getName(), "info");
    }
}
