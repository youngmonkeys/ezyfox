package com.tvd12.ezyfox.testing.collect;

import com.tvd12.ezyfox.callback.EzyCallback;
import org.testng.annotations.Test;

public class EzyCallbackTest {

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Test
    public void test() {
        ((EzyCallback) data -> {
        })
            .call(new Object(), 10);
    }
}
