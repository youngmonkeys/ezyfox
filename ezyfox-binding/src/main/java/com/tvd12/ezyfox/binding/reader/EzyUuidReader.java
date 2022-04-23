package com.tvd12.ezyfox.binding.reader;

import com.tvd12.ezyfox.binding.EzyReader;
import com.tvd12.ezyfox.binding.EzyUnmarshaller;

import java.util.UUID;

public final class EzyUuidReader implements EzyReader<Object, UUID> {

    private static final EzyUuidReader INSTANCE = new EzyUuidReader();

    private EzyUuidReader() {}

    public static EzyUuidReader getInstance() {
        return INSTANCE;
    }

    @Override
    public UUID read(EzyUnmarshaller unmarshaller, Object value) {
        if (value instanceof UUID) {
            return (UUID) value;
        }
        if (value instanceof String) {
            return UUID.fromString((String) value);
        }
        throw new IllegalArgumentException("can't convert: " + value + " to UUID");
    }
}
