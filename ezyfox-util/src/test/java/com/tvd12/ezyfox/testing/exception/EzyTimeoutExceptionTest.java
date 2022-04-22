package com.tvd12.ezyfox.testing.exception;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.exception.EzyTimeoutException;

public class EzyTimeoutExceptionTest {

    @Test
    public void test() {
        new EzyTimeoutException("");
        new EzyTimeoutException("", new Exception());
    }

}
