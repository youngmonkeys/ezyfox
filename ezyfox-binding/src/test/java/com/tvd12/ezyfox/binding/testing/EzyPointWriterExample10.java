package com.tvd12.ezyfox.binding.testing;

import com.tvd12.ezyfox.binding.EzyMarshaller;
import com.tvd12.ezyfox.binding.EzyUnmarshaller;
import com.tvd12.ezyfox.binding.exception.EzyReadValueException;
import com.tvd12.ezyfox.binding.impl.EzyObjectReaderBuilder;
import com.tvd12.ezyfox.binding.impl.EzySimpleBindingContext;
import com.tvd12.ezyfox.binding.testing.scan1.Scan1ClassA;
import com.tvd12.ezyfox.entity.EzyObject;
import org.testng.annotations.Test;

public class EzyPointWriterExample10 {

    @Test
    public void test() throws Exception {
        EzyObjectReaderBuilder.setDebug(true);
        EzySimpleBindingContext context = EzySimpleBindingContext.builder()
            .scan("com.tvd12.ezyfox.binding.testing.scan1")
            .build();

        EzyMarshaller marshaller = context.newMarshaller();
        EzyObject outObject = marshaller.marshal(new Scan1ClassA());
        outObject.put("hung", "world");
        System.out.println(outObject);
        EzyUnmarshaller unmarshaller = context.newUnmarshaller();
        Scan1ClassA outEntity = unmarshaller.unmarshal(outObject, Scan1ClassA.class);
        System.out.println(outEntity);

        try {
            outObject.put("d", "abc");
            unmarshaller.unmarshal(outObject, Scan1ClassA.class);
        } catch (Exception e) {
            assert e instanceof EzyReadValueException;
            EzyReadValueException ex = (EzyReadValueException) e;
            assert ex.getOutType() == Scan1ClassA.class;
            assert ex.getValue() == outObject;
        }
    }
}
