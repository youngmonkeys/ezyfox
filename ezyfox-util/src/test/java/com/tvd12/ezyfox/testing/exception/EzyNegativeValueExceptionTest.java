package com.tvd12.ezyfox.testing.exception;

import com.tvd12.ezyfox.exception.EzyNegativeValueException;
import com.tvd12.test.base.BaseTest;
import org.testng.annotations.Test;

public class EzyNegativeValueExceptionTest extends BaseTest {

    @SuppressWarnings("ThrowableNotThrown")
    @Test
    public void test() {
        new EzyNegativeValueException(-10);
    }
}
