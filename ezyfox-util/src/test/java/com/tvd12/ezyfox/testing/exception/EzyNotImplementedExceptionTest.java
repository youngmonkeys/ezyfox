package com.tvd12.ezyfox.testing.exception;

import com.tvd12.ezyfox.exception.EzyNotImplementedException;
import org.testng.annotations.Test;

public class EzyNotImplementedExceptionTest {

    @SuppressWarnings("ThrowableNotThrown")
    @Test
    public void test() {
        new EzyNotImplementedException();
        new EzyNotImplementedException("");
        new EzyNotImplementedException("", new Exception());
    }
}
