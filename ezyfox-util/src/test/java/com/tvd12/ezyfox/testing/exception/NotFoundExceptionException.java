package com.tvd12.ezyfox.testing.exception;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.exception.NotFoundException;

public class NotFoundExceptionException {

    @Test
    public void test() {
        new NotFoundException();
        new NotFoundException("");
        new NotFoundException(new Exception());
        new NotFoundException("", new Exception());
    }

}
