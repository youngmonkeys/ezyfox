package com.tvd12.ezyfox.testing.pattern;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.pattern.EzyDataHandler;
import com.tvd12.ezyfox.pattern.EzyMapDataHandlers;

public class EzyMapDataHandlersTest {

    @SuppressWarnings("unchecked")
    @Test
    public void test() {
        EzyMapDataHandlers handlers = new EzyMapDataHandlers();
        handlers.addHandler("1", new EzyDataHandler<String>() {
            @Override
            public void handleData(String data) {
            }
        });
        EzyDataHandler<String> handler = handlers.getHandler("1");
        handler.handleData("a");
        handler.handleException(Thread.currentThread(), new Exception());
        try {
            handlers.getHandler("abc");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
