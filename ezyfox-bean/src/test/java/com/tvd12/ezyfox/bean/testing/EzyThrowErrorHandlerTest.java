package com.tvd12.ezyfox.bean.testing;

import com.tvd12.ezyfox.bean.impl.EzyThrowErrorHandler;

import org.testng.annotations.Test;

public class EzyThrowErrorHandlerTest {

    @Test
    public void test() {
        EzyThrowErrorHandler handler = new EzyThrowErrorHandler();
        try {
            handler.handle(new IllegalArgumentException("hello"));
        }
        catch (Exception e) {
            assert e instanceof IllegalStateException;
        }

    }
}