package com.tvd12.ezyfox.binding.testing.exception1;

import com.tvd12.ezyfox.binding.EzyMarshaller;
import com.tvd12.ezyfox.binding.impl.EzySimpleBindingContext;

public class Exception1Binding1 {

    public static void main(String[] args) {
        EzySimpleBindingContext context = EzySimpleBindingContext.builder()
            .addClass(Exception1ClassA.class)
            .build();
        EzyMarshaller marshaller = context.newMarshaller();
        marshaller.marshal(new Exception1ClassA());
    }
}
