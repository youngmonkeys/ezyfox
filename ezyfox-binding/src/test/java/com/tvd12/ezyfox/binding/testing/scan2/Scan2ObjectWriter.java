package com.tvd12.ezyfox.binding.testing.scan2;

import com.tvd12.ezyfox.binding.EzyMarshaller;
import com.tvd12.ezyfox.binding.annotation.EzyWriterImpl;
import com.tvd12.ezyfox.binding.impl.EzyAbstractWriter;
import com.tvd12.ezyfox.builder.EzyObjectBuilder;
import com.tvd12.ezyfox.entity.EzyObject;

@EzyWriterImpl
public class Scan2ObjectWriter extends EzyAbstractWriter<Scan2Object, EzyObject> {

    @Override
    public EzyObject write(EzyMarshaller marshaller, Scan2Object object) {
        EzyObjectBuilder builder = newObjectBuilder();
        return builder.build();
    }

}
