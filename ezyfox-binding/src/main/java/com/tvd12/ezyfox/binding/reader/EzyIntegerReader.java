package com.tvd12.ezyfox.binding.reader;

import com.tvd12.ezyfox.binding.EzyReader;
import com.tvd12.ezyfox.binding.EzyUnmarshaller;

public final class EzyIntegerReader implements EzyReader<Number, Integer> {

    private static final EzyIntegerReader INSTANCE = new EzyIntegerReader();

    private EzyIntegerReader() {
    }

    public static EzyIntegerReader getInstance() {
        return INSTANCE;
    }

    @Override
    public Integer read(EzyUnmarshaller unmarshaller, Number value) {
        return value.intValue();
    }
}
