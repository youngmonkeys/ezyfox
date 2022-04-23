package com.tvd12.ezyfox.binding.reader;

import com.tvd12.ezyfox.binding.EzyReader;
import com.tvd12.ezyfox.binding.EzyUnmarshaller;
import com.tvd12.ezyfox.io.EzyDates;

import java.util.Date;

public final class EzyDateReader implements EzyReader<Object, Date> {

    private static final EzyDateReader INSTANCE = new EzyDateReader();

    private EzyDateReader() {}

    public static EzyDateReader getInstance() {
        return INSTANCE;
    }

    @Override
    public Date read(EzyUnmarshaller unmarshaller, Object value) {
        if (value instanceof Date) {
            return (Date) value;
        }
        if (value instanceof Number) {
            return new Date(((Number) value).longValue());
        }
        if (value instanceof String) {
            return EzyDates.parse((String) value);
        }
        throw new IllegalArgumentException("can't convert: " + value + " to date");
    }
}
