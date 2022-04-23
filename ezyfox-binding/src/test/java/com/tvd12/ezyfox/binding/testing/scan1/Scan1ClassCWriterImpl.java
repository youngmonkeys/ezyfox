package com.tvd12.ezyfox.binding.testing.scan1;

import com.tvd12.ezyfox.binding.EzyMarshaller;
import com.tvd12.ezyfox.binding.annotation.EzyWriterImpl;
import com.tvd12.ezyfox.binding.impl.EzyAbstractWriter;
import com.tvd12.ezyfox.entity.EzyArray;

@EzyWriterImpl
public class Scan1ClassCWriterImpl
        extends EzyAbstractWriter<Scan1ClassC, EzyArray> {

    public EzyArray write(EzyMarshaller marshaller, Scan1ClassC object) {
        return newArrayBuilder()
                .append(object.getX())
                .append(object.getY())
                .append(object.getZ())
                .build();
    }
}
