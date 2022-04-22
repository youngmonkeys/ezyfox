package com.tvd12.ezyfox.binding.testing.chat;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.binding.EzyBindingContext;
import com.tvd12.ezyfox.binding.EzyMarshaller;

public class ChatTest {

    @Test
    public void test() {
        EzyBindingContext context = EzyBindingContext.builder()
                .scan("com.tvd12.ezyfox.binding.testing.chat")
                .build();
        EzyMarshaller marshaller = context.newMarshaller();
        Object object = marshaller.marshal(new ChatMessage());
        System.out.println(object);
    }

}
