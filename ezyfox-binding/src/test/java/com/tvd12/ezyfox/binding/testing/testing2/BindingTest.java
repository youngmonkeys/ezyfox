package com.tvd12.ezyfox.binding.testing.testing2;

import com.tvd12.ezyfox.binding.EzyBindingContext;
import com.tvd12.ezyfox.binding.EzyMarshaller;
import com.tvd12.ezyfox.binding.EzyUnmarshaller;
import com.tvd12.ezyfox.binding.impl.EzyArrayReaderBuilder;
import com.tvd12.ezyfox.binding.impl.EzyArrayWriterBuilder;
import com.tvd12.ezyfox.binding.impl.EzyObjectReaderBuilder;
import com.tvd12.ezyfox.binding.impl.EzyObjectWriterBuilder;
import com.tvd12.ezyfox.entity.EzyObject;
import com.tvd12.test.base.BaseTest;
import org.testng.annotations.Test;

public class BindingTest extends BaseTest {

    @Test
    public void test() {
        EzyArrayWriterBuilder.setDebug(true);
        EzyObjectWriterBuilder.setDebug(true);
        EzyArrayReaderBuilder.setDebug(true);
        EzyObjectReaderBuilder.setDebug(true);
        EzyBindingContext context = EzyBindingContext.builder()
            .scan("com.tvd12.ezyfox.binding.testing.testing2")
            .build();
        EzyMarshaller marshaller = context.newMarshaller();
        EzyObject object = marshaller.marshal(new ClassA());
        EzyUnmarshaller unmarshaller = context.newUnmarshaller();
        InterfaceA interfaceA = unmarshaller.unmarshal(object, InterfaceA.class);
        System.out.println(interfaceA);
    }
}
