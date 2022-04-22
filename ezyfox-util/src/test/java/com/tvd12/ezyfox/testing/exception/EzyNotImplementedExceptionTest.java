package com.tvd12.ezyfox.testing.exception;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.exception.EzyNotImplementedException;

public class EzyNotImplementedExceptionTest {

    @Test
    public void test() {
        new EzyNotImplementedException();
        new EzyNotImplementedException("");
        new EzyNotImplementedException("", new Exception());
    }

}
