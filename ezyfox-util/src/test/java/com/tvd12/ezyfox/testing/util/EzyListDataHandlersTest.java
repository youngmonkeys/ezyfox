package com.tvd12.ezyfox.testing.util;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.util.EzyDataHandler;
import com.tvd12.ezyfox.util.EzyListDataHandlers;
import com.tvd12.test.base.BaseTest;

public class EzyListDataHandlersTest extends BaseTest {

    @Test
    public void test() {
        EzyListDataHandlers handlers = new EzyListDataHandlers();
        handlers.addDataHandler(new EzyDataHandler<Object>() {
             @Override
            public void handleData(Object data) {
                 System.out.println(data);
            }
        });
        handlers.handleData(new String("hello world"));
    }
}
