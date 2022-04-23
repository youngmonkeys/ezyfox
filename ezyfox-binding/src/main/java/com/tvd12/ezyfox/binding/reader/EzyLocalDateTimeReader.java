package com.tvd12.ezyfox.binding.reader;

import java.time.LocalDateTime;

import com.tvd12.ezyfox.binding.EzyReader;
import com.tvd12.ezyfox.binding.EzyUnmarshaller;
import com.tvd12.ezyfox.io.EzyDates;

public final class EzyLocalDateTimeReader implements EzyReader<Object, LocalDateTime> {

    private static final EzyLocalDateTimeReader INSTANCE = new EzyLocalDateTimeReader();
    
    private EzyLocalDateTimeReader() {
    }
    
    public static EzyLocalDateTimeReader getInstance() {
        return INSTANCE;
    }
    
    @Override
    public LocalDateTime read(EzyUnmarshaller unmarshaller, Object value) {
        if(value instanceof LocalDateTime)
            return (LocalDateTime)value;
        if(value instanceof Long)
            return EzyDates.millisToDateTime((Long)value);
        if(value instanceof String)
            return EzyDates.parseDateTime((String)value);
        throw new IllegalArgumentException("can't convert: " + value + " to LocalDateTime");
    }
}
