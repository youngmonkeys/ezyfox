package com.tvd12.ezyfox.database.testing;

import com.tvd12.ezyfox.database.exception.EzyTransactionException;
import org.testng.annotations.Test;

public class EzyTransactionExceptionTest {

    @SuppressWarnings("ThrowableNotThrown")
    @Test
    public void test() {
        new EzyTransactionException();
        new EzyTransactionException("");
        new EzyTransactionException("", new Throwable());
    }
}
