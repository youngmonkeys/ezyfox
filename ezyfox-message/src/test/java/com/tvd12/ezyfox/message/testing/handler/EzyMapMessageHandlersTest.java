package com.tvd12.ezyfox.message.testing.handler;

import com.tvd12.ezyfox.message.handler.EzyMapMessageHandlers;
import com.tvd12.ezyfox.message.handler.EzyMessageHandler;
import com.tvd12.ezyfox.message.testing.entity.Message2;
import com.tvd12.test.base.BaseTest;
import org.testng.annotations.Test;

public class EzyMapMessageHandlersTest extends BaseTest {

    @SuppressWarnings("unchecked")
    @Test
    public void test() {
        EzyMapMessageHandlers handlers = new EzyMapMessageHandlers();
        handlers.addHandler(
            "message2",
            (EzyMessageHandler<Message2>) System.out::println
        );
        EzyMessageHandler<Message2> handler = handlers.getHandler("message2");
        handler.handleMessage(new Message2(1L, "chat"));
        handler.handleException(Thread.currentThread(), new Exception());
        try {
            handlers.getHandler("no one");
        } catch (Exception e) {
            assert e instanceof IllegalArgumentException;
        }
    }
}
