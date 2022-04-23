package com.tvd12.ezyfox.binding.testing.testing1;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.binding.EzyBindingContext;
import com.tvd12.ezyfox.binding.EzyMarshaller;
import com.tvd12.ezyfox.binding.EzyUnmarshaller;
import com.tvd12.ezyfox.binding.impl.EzyArrayReaderBuilder;
import com.tvd12.ezyfox.binding.impl.EzyObjectReaderBuilder;
import com.tvd12.ezyfox.binding.impl.EzyArrayWriterBuilder;
import com.tvd12.ezyfox.binding.impl.EzyObjectWriterBuilder;
import com.tvd12.ezyfox.entity.EzyArray;
import com.tvd12.test.base.BaseTest;

public class BindingTest extends BaseTest {

    @Test
    public void test() {
        EzyArrayWriterBuilder.setDebug(true);
        EzyObjectWriterBuilder.setDebug(true);
        EzyArrayReaderBuilder.setDebug(true);
        EzyObjectReaderBuilder.setDebug(true);
        EzyBindingContext context = EzyBindingContext.builder()
                .scan("com.tvd12.ezyfox.binding.testing.testing1")
                .build();
        EzyMarshaller marshaller = context.newMarshaller();
        EzyArray array = marshaller.marshal(new ClassA());
        EzyUnmarshaller unmarshaller = context.newUnmarshaller();
        InterfaceA interfaceA = unmarshaller.unmarshal(array, InterfaceA.class);
        System.out.println(interfaceA);
    }
}
