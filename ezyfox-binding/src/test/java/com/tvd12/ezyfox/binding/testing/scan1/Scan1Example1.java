package com.tvd12.ezyfox.binding.testing.scan1;

import com.tvd12.ezyfox.binding.EzyMarshaller;
import com.tvd12.ezyfox.binding.EzyUnmarshaller;
import com.tvd12.ezyfox.binding.impl.*;
import com.tvd12.ezyfox.entity.EzyObject;
import org.testng.annotations.Test;

public class Scan1Example1 {

    public static void main(String[] args) {
        new Scan1Example1().test();
    }

    @Test
    public void test() {
        EzyObjectReaderBuilder.setDebug(true);
        EzyArrayReaderBuilder.setDebug(true);
        EzyObjectWriterBuilder.setDebug(true);
        EzyArrayWriterBuilder.setDebug(true);

        EzySimpleBindingContext context = EzySimpleBindingContext.builder()
            .scan("com.tvd12.ezyfox.binding.testing.scan1")
            .addTemplate(new Scan1ClassCWriterImpl())
            .addTemplate(new Scan1ClassCReaderImpl())
            .build();

        EzyMarshaller marshaller = context.newMarshaller();
        EzyObject outObject = marshaller.marshal(new Scan1ClassA());
        System.out.println(outObject);
        outObject.put("hung", "xau trai");
        EzyUnmarshaller unmarshaller = context.newUnmarshaller();
        Scan1ClassA outEntity = unmarshaller.unmarshal(outObject, Scan1ClassA.class);
        System.out.println(outEntity);
    }
}
