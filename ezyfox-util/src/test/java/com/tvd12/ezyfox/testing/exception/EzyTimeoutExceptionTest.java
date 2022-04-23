package com.tvd12.ezyfox.testing.exception;

import com.tvd12.ezyfox.exception.EzyTimeoutException;
import org.testng.annotations.Test;

public class EzyTimeoutExceptionTest {

    @SuppressWarnings("ThrowableNotThrown")
    @Test
    public void test() {
        new EzyTimeoutException("");
        new EzyTimeoutException("", new Exception());
    }
}
