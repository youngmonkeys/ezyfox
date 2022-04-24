package com.tvd12.ezyfox.testing.codec;

import com.tvd12.ezyfox.exception.EzyCodecException;
import com.tvd12.test.assertion.Asserts;
import com.tvd12.test.util.RandomUtil;
import org.testng.annotations.Test;

public class EzyCodecExceptionTest {

    @Test
    public void test() {
        // given
        String message = RandomUtil.randomShortAlphabetString();
        EzyCodecException sut = new EzyCodecException(message, new Exception());

        // when
        // then
        Asserts.assertEquals(sut.getMessage(), message);
    }
}
