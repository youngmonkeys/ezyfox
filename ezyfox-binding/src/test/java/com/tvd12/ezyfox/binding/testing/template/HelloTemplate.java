package com.tvd12.ezyfox.binding.testing.template;

import com.tvd12.ezyfox.binding.EzyMarshaller;
import com.tvd12.ezyfox.binding.EzyTemplate;
import com.tvd12.ezyfox.binding.EzyUnmarshaller;
import com.tvd12.ezyfox.binding.annotation.EzyTemplateImpl;

@EzyTemplateImpl
public class HelloTemplate implements EzyTemplate<String, Hello> {

    @Override
    public Hello read(EzyUnmarshaller unmarshaller, String value) {
        return new Hello(value);
    }

    @Override
    public String write(EzyMarshaller marshaller, Hello object) {
        return object.getWho();
    }
}
