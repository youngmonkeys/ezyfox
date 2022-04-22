package com.tvd12.ezyfox.binding.testing;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.binding.EzyMarshaller;
import com.tvd12.ezyfox.binding.impl.EzyObjectWriterBuilder;
import com.tvd12.ezyfox.binding.impl.EzySimpleBindingContext;
import com.tvd12.ezyfox.binding.testing.scan1.Scan1ClassA;

public class EzyPointWriterExample6 {

    @Test
    public void test() throws Exception {
        EzyObjectWriterBuilder.setDebug(true);
        EzySimpleBindingContext context = EzySimpleBindingContext.builder()
                .scan("com.tvd12.ezyfox.binding.testing.scan1")
                .build();

        EzyMarshaller marshaller = context.newMarshaller();

        System.out.println(marshaller.marshal(new Scan1ClassA()).toString());
    }
}
