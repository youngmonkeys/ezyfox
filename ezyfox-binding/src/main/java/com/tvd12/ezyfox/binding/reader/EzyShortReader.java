package com.tvd12.ezyfox.binding.reader;

import com.tvd12.ezyfox.binding.EzyReader;
import com.tvd12.ezyfox.binding.EzyUnmarshaller;

public final class EzyShortReader implements EzyReader<Number, Short> {

    private static final EzyShortReader INSTANCE = new EzyShortReader();

    private EzyShortReader() {
    }

    public static EzyShortReader getInstance() {
        return INSTANCE;
    }

    @Override
    public Short read(EzyUnmarshaller unmarshaller, Number value) {
        return value.shortValue();
    }
}