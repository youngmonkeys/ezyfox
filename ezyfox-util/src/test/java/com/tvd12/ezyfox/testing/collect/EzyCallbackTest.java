package com.tvd12.ezyfox.testing.collect;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.callback.EzyCallback;

public class EzyCallbackTest {

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Test
    public void test() {
        new EzyCallback() {
            @Override
            public void call(Object data) {
            }
        }
        .call(new Object(), 10);;
    }

}
