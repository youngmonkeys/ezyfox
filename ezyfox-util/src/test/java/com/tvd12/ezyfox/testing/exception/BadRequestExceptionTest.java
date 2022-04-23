package com.tvd12.ezyfox.testing.exception;

import com.tvd12.ezyfox.exception.BadRequestException;
import org.testng.annotations.Test;

public class BadRequestExceptionTest {

    @Test
    public void test() {
        new BadRequestException();
        new BadRequestException(new Exception(""));
        new BadRequestException(1, "");
        assert new BadRequestException(1, "", new Exception("")).getCode() == 1;
    }
}
