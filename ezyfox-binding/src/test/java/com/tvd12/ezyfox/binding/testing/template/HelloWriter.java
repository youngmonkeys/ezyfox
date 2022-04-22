package com.tvd12.ezyfox.binding.testing.template;

import com.tvd12.ezyfox.binding.EzyMarshaller;
import com.tvd12.ezyfox.binding.EzyWriter;
import com.tvd12.ezyfox.binding.annotation.EzyWriterImpl;

@EzyWriterImpl
public class HelloWriter implements EzyWriter<Hello, String> {

    @Override
    public String write(EzyMarshaller marshaller, Hello object) {
        return object.getWho();
    }
}