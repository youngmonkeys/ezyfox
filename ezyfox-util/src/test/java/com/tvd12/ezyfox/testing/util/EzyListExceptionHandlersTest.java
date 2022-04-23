package com.tvd12.ezyfox.testing.util;

import com.tvd12.ezyfox.util.EzyExceptionHandler;
import com.tvd12.ezyfox.util.EzyExceptionHandlers;
import com.tvd12.ezyfox.util.EzyListExceptionHandlers;
import com.tvd12.test.base.BaseTest;
import org.testng.annotations.Test;

public class EzyListExceptionHandlersTest extends BaseTest {

    @Test
    public void test() {
        EzyExceptionHandlers handlers = new EzyListExceptionHandlers();
        handlers.addExceptionHandler(new EzyExceptionHandler() {
            @Override
            public void handleException(Thread thread, Throwable throwable) {
            }
        });
        handlers.handleException(Thread.currentThread(), new Exception());
    }
}
