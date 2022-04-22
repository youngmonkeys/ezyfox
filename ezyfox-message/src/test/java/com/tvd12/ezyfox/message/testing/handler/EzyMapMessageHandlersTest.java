package com.tvd12.ezyfox.message.testing.handler;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.message.handler.EzyMapMessageHandlers;
import com.tvd12.ezyfox.message.handler.EzyMessageHandler;
import com.tvd12.ezyfox.message.testing.entity.Message2;
import com.tvd12.test.base.BaseTest;

public class EzyMapMessageHandlersTest extends BaseTest {

    @SuppressWarnings("unchecked")
    @Test
    public void test() {
        EzyMapMessageHandlers handlers = new EzyMapMessageHandlers();
        handlers.addHandler("message2", new EzyMessageHandler<Message2>() {
            @Override
            public void handleMessage(Message2 message) {
                System.out.println(message);
            }

        });
        EzyMessageHandler<Message2> handler = handlers.getHandler("message2");
        handler.handleMessage(new Message2(1L, "chat"));
        handler.handleException(Thread.currentThread(), new Exception());
        try {
            handlers.getHandler("no one");
        }
        catch (Exception e) {
            assert e instanceof IllegalArgumentException;
        }
    }

}
