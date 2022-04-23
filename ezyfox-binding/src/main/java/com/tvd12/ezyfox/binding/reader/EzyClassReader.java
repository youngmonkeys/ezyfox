package com.tvd12.ezyfox.binding.reader;

import com.tvd12.ezyfox.binding.EzyReader;
import com.tvd12.ezyfox.binding.EzyUnmarshaller;
import com.tvd12.ezyfox.reflect.EzyClasses;

@SuppressWarnings("rawtypes")
public final class EzyClassReader implements EzyReader<Object, Class> {

    private static final EzyClassReader INSTANCE = new EzyClassReader();

    private EzyClassReader() {}

    public static EzyClassReader getInstance() {
        return INSTANCE;
    }

    @Override
    public Class read(EzyUnmarshaller unmarshaller, Object value) {
        if (value instanceof Class) {
            return (Class) value;
        }
        if (value instanceof String) {
            return EzyClasses.getClass((String) value);
        }
        throw new IllegalArgumentException("can't convert: " + value + " to Class");
    }
}
