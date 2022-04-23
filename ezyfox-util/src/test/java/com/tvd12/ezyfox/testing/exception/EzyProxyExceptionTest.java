package com.tvd12.ezyfox.testing.exception;

import com.tvd12.ezyfox.exception.EzyProxyException;
import org.testng.annotations.Test;

public class EzyProxyExceptionTest {

    @SuppressWarnings("ThrowableNotThrown")
    @Test
    public void test() {
        new EzyProxyException(new Exception());
    }
}
