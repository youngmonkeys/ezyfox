package com.tvd12.ezyfox.testing.util;

import com.tvd12.ezyfox.util.EzyExceptionHandler;
import com.tvd12.ezyfox.util.EzyExceptionHandlers;
import com.tvd12.ezyfox.util.EzyListExceptionHandlers;
import com.tvd12.test.assertion.Asserts;
import com.tvd12.test.base.BaseTest;
import org.testng.annotations.Test;

import static org.mockito.Mockito.*;

public class EzyListExceptionHandlersTest extends BaseTest {

    @Test
    public void test() {
        // given
        EzyExceptionHandler handler = mock(EzyExceptionHandler.class);
        EzyExceptionHandlers sut = new EzyListExceptionHandlers();

        // when
        sut.addExceptionHandler(handler);
        sut.addExceptionHandler((thread, throwable) -> {});

        Thread thread = Thread.currentThread();
        Exception exception = new Exception("just test");
        sut.handleException(thread, exception);

        // then
        Asserts.assertFalse(sut.isEmpty());
        verify(handler, times(1)).handleException(thread, exception);
    }
}
