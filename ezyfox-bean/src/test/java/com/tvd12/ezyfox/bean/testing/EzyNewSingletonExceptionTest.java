package com.tvd12.ezyfox.bean.testing;

import com.tvd12.ezyfox.bean.exception.EzyNewSingletonException;
import com.tvd12.test.assertion.Asserts;
import org.testng.annotations.Test;

public class EzyNewSingletonExceptionTest {

    @Test
    public void test() {
        // given
        EzyNewSingletonException sut = new EzyNewSingletonException(
            Object.class,
            Object.class,
            "object"
        );

        // when
        // then
        Asserts.assertEquals(sut.getSingletonClass(), Object.class);
    }
}
