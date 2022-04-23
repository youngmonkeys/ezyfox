package com.tvd12.ezyfox.testing.exception;

import com.tvd12.ezyfox.exception.InternalServerErrorException;
import org.testng.annotations.Test;

public class InternalServerErrorExceptionTest {

    @Test
    public void test() {
        new InternalServerErrorException();
        new InternalServerErrorException("");
        new InternalServerErrorException(new Exception());
        new InternalServerErrorException("", new Exception());
    }
}
