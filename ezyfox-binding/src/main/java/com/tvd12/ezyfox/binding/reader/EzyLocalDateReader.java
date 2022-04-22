package com.tvd12.ezyfox.binding.reader;

import java.time.LocalDate;

import com.tvd12.ezyfox.binding.EzyReader;
import com.tvd12.ezyfox.binding.EzyUnmarshaller;
import com.tvd12.ezyfox.io.EzyDates;

public final class EzyLocalDateReader implements EzyReader<Object, LocalDate> {

    private static final EzyLocalDateReader INSTANCE = new EzyLocalDateReader();
    
    private EzyLocalDateReader() {
    }
    
    public static EzyLocalDateReader getInstance() {
        return INSTANCE;
    }
    
    @Override
    public LocalDate read(EzyUnmarshaller unmarshaller, Object value) {
        if(value instanceof LocalDate)
            return (LocalDate)value;
        if(value instanceof Long)
            return EzyDates.millisToDateTime((Long)value).toLocalDate();
        if(value instanceof String)
            return EzyDates.parseDate((String)value, "yyyy-MM-dd");
        throw new IllegalArgumentException("can't convert: " + value + " to LocalDate");
    }
    
}
