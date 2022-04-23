package com.tvd12.ezyfox.testing.exception;

import com.tvd12.ezyfox.exception.EzyNotFoundException;
import org.testng.annotations.Test;

public class EzyNotFoundExceptionTest {

    @SuppressWarnings("ThrowableNotThrown")
    @Test
    public void test() {
        new EzyNotFoundException();
        new EzyNotFoundException("");
        new EzyNotFoundException(new Exception());
        new EzyNotFoundException("", new Exception());
    }
}
