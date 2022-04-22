package com.tvd12.ezyfox.testing.exception;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.exception.InternalServerErrorException;

public class InternalServerErrorExceptionTest {

    @Test
    public void test() {
        new InternalServerErrorException();
        new InternalServerErrorException("");
        new InternalServerErrorException(new Exception());
        new InternalServerErrorException("", new Exception());
    }
}