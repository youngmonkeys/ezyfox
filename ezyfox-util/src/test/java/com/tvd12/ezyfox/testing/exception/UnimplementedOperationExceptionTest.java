package com.tvd12.ezyfox.testing.exception;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.exception.UnimplementedOperationException;

public class UnimplementedOperationExceptionTest {

    @Test
    public void test() {
        new UnimplementedOperationException("", new Exception());
    }
}