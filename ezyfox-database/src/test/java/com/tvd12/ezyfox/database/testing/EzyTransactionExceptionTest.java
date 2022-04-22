package com.tvd12.ezyfox.database.testing;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.database.exception.EzyTransactionException;

public class EzyTransactionExceptionTest {

    @Test
    public void test() {
        new EzyTransactionException();
        new EzyTransactionException("");
        new EzyTransactionException("", new Throwable());
    }
}