package com.tvd12.ezyfox.binding.reader;

import com.tvd12.ezyfox.binding.EzyReader;
import com.tvd12.ezyfox.binding.EzyUnmarshaller;

import java.math.BigDecimal;

public final class EzyBigDecimalReader implements EzyReader<Object, BigDecimal> {

    private static final EzyBigDecimalReader INSTANCE = new EzyBigDecimalReader();

    private EzyBigDecimalReader() {}

    public static EzyBigDecimalReader getInstance() {
        return INSTANCE;
    }

    @Override
    public BigDecimal read(EzyUnmarshaller unmarshaller, Object value) {
        if (value instanceof BigDecimal) {
            return (BigDecimal) value;
        }
        if (value instanceof Double) {
            return BigDecimal.valueOf((Double) value);
        }
        if (value instanceof Float) {
            return BigDecimal.valueOf((Float) value);
        }
        if (value instanceof Number) {
            return new BigDecimal(((Number) value).longValue());
        }
        if (value instanceof String) {
            return new BigDecimal((String) value);
        }
        throw new IllegalArgumentException("can't convert: " + value + " to BigDecimal");
    }
}
