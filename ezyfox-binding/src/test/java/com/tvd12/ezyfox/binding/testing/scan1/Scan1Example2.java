package com.tvd12.ezyfox.binding.testing.scan1;

import com.tvd12.ezyfox.binding.EzyMarshaller;
import com.tvd12.ezyfox.binding.EzyUnmarshaller;
import com.tvd12.ezyfox.binding.impl.*;
import com.tvd12.ezyfox.binding.testing.scan1.ClassA.ClassB;
import com.tvd12.ezyfox.entity.EzyObject;

public class Scan1Example2 {

    public static void main(String[] args) {
        new Scan1Example2().test();
    }

    @SuppressWarnings("rawtypes")
    public void test() {

        EzyObjectReaderBuilder.setDebug(true);
        EzyArrayReaderBuilder.setDebug(true);
        EzyObjectWriterBuilder.setDebug(true);
        EzyArrayWriterBuilder.setDebug(true);

        EzySimpleBindingContext context = EzySimpleBindingContext.builder()
            .addClass(ClassA.class)
            .addClass(ClassB.class)
            .build();

        EzyMarshaller marshaller = context.newMarshaller();
        EzyObject outObject = marshaller.marshal(new ClassA());
        System.out.println(outObject);
        outObject.put("hung", "xau trai");
        EzyUnmarshaller unmarshaller = context.newUnmarshaller();
        ClassA outEntity = unmarshaller.unmarshal(outObject, ClassA.class);
        System.out.println(outEntity);
    }
}
