package com.tvd12.ezyfox.binding.reader;

import com.tvd12.ezyfox.binding.EzyReader;
import com.tvd12.ezyfox.binding.EzyUnmarshaller;

public final class EzyDoubleReader implements EzyReader<Number, Double> {

    private static final EzyDoubleReader INSTANCE = new EzyDoubleReader();

    private EzyDoubleReader() {
    }

    public static EzyDoubleReader getInstance() {
        return INSTANCE;
    }

    @Override
    public Double read(EzyUnmarshaller unmarshaller, Number value) {
        return value.doubleValue();
    }
}
