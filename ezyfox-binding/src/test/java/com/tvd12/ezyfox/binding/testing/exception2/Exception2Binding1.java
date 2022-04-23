package com.tvd12.ezyfox.binding.testing.exception2;

import com.tvd12.ezyfox.binding.EzyMarshaller;
import com.tvd12.ezyfox.binding.impl.EzySimpleBindingContext;

public class Exception2Binding1 {

    public static void main(String[] args) throws Exception {
        EzySimpleBindingContext context = EzySimpleBindingContext.builder()
            .addClass(Exception2ClassA.class)
            .build();
        EzyMarshaller marshaller = context.newMarshaller();
        marshaller.marshal(new Exception2ClassA());
    }
}
