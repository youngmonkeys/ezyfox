package com.tvd12.ezyfox.testing.exception;

import com.tvd12.ezyfox.exception.UnimplementedOperationException;
import org.testng.annotations.Test;

public class UnimplementedOperationExceptionTest {

    @SuppressWarnings("ThrowableNotThrown")
    @Test
    public void test() {
        new UnimplementedOperationException("", new Exception());
    }
}
