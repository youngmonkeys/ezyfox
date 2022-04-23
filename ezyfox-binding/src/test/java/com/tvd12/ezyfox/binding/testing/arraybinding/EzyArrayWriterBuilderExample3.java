package com.tvd12.ezyfox.binding.testing.arraybinding;

import com.tvd12.ezyfox.binding.EzyMarshaller;
import com.tvd12.ezyfox.binding.impl.EzyArrayWriterBuilder;
import com.tvd12.ezyfox.binding.impl.EzySimpleBindingContext;
import com.tvd12.ezyfox.entity.EzyArray;
import org.testng.annotations.Test;

public class EzyArrayWriterBuilderExample3 {

    @Test
    public void test() throws Exception {
        EzyArrayWriterBuilder.setDebug(true);
        EzySimpleBindingContext context = EzySimpleBindingContext.builder()
            .scan("com.tvd12.ezyfox.binding.testing.arraybinding")
            .build();
        EzyMarshaller marshaller = context.newMarshaller();
        EzyArrayWriterBuilder.setDebug(true);
        EzyArray array = marshaller.marshal(new ClassB1());
        System.out.println(array);
    }
}
