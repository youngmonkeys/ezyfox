package com.tvd12.ezyfox.binding.testing.template;

import com.tvd12.ezyfox.binding.EzyBindingContext;
import com.tvd12.ezyfox.binding.EzyMarshaller;
import com.tvd12.ezyfox.binding.EzyUnmarshaller;

public class MainTest {

    public static void main(String[] args) {
        EzyBindingContext ctx = EzyBindingContext.builder()
                .scan("com.tvd12.ezyfox.binding.testing.template")
                .build();
        EzyMarshaller marshaller = ctx.newMarshaller();
        System.out.println("marshall: " + marshaller.marshal(new Hello("world")));
        EzyUnmarshaller unmarshaller = ctx.newUnmarshaller();
        System.out.println("unmarshall: " + unmarshaller.unmarshal("z-war", Hello.class));
    }

}
