package com.tvd12.ezyfox.testing.exception;

import com.tvd12.ezyfox.exception.EzyNotImplementedException;
import org.testng.annotations.Test;

public class EzyNotImplementedExceptionTest {

    @Test
    public void test() {
        new EzyNotImplementedException();
        new EzyNotImplementedException("");
        new EzyNotImplementedException("", new Exception());
    }
}
