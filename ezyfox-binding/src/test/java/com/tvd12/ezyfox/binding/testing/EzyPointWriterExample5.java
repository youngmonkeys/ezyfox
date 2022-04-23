package com.tvd12.ezyfox.binding.testing;

import com.tvd12.ezyfox.binding.EzyMarshaller;
import com.tvd12.ezyfox.binding.exception.EzyWriteValueException;
import com.tvd12.ezyfox.binding.impl.EzySimpleBindingContext;
import com.tvd12.ezyfox.entity.EzyObject;
import org.testng.annotations.Test;

public class EzyPointWriterExample5 {

    @Test
    public void test() throws Exception {
        EzySimpleBindingContext context = EzySimpleBindingContext.builder()
            .addClass(Point.class)
            .addClass(EzyTestData.class)
            .addClass(PointException.class)
            .addTemplate(new TestData1WriterImpl())
            .build();

        EzyMarshaller marshaller = context.newMarshaller();

        System.out.println(marshaller.marshal(new Point()).toString());

        try {
            marshaller.marshal(new PointException());
        } catch (Exception e) {
            assert e instanceof EzyWriteValueException;
            EzyWriteValueException ex = (EzyWriteValueException) e;
            assert ex.getOutType() == EzyObject.class;
            assert ex.getValue() instanceof PointException;
        }
    }

    public static class PointException {

        public String getValue() {
            throw new IllegalStateException("test");
        }

    }
}
