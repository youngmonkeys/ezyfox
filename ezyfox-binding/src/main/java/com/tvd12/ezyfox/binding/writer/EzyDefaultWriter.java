package com.tvd12.ezyfox.binding.writer;

import com.tvd12.ezyfox.binding.EzyMarshaller;
import com.tvd12.ezyfox.binding.EzyWriter;

public final class EzyDefaultWriter implements EzyWriter<Object, Object> {

    private static final EzyDefaultWriter INSTANCE = new EzyDefaultWriter();

    private EzyDefaultWriter() {
    }

    public static EzyDefaultWriter getInstance() {
        return INSTANCE;
    }

    @Override
    public Object write(EzyMarshaller marshaller, Object object) {
        return object;
    }


}