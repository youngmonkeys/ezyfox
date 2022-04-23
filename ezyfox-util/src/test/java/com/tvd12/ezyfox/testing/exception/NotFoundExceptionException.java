package com.tvd12.ezyfox.testing.exception;

import com.tvd12.ezyfox.exception.NotFoundException;
import org.testng.annotations.Test;

public class NotFoundExceptionException {

    @SuppressWarnings("ThrowableNotThrown")
    @Test
    public void test() {
        new NotFoundException();
        new NotFoundException("");
        new NotFoundException(new Exception());
        new NotFoundException("", new Exception());
    }
}
