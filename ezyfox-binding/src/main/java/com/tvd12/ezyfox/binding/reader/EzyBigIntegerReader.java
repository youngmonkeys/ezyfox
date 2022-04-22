package com.tvd12.ezyfox.binding.reader;

import java.math.BigInteger;

import com.tvd12.ezyfox.binding.EzyReader;
import com.tvd12.ezyfox.binding.EzyUnmarshaller;

public final class EzyBigIntegerReader implements EzyReader<Object, BigInteger> {

    private static final EzyBigIntegerReader INSTANCE = new EzyBigIntegerReader();

    private EzyBigIntegerReader() {
    }

    public static EzyBigIntegerReader getInstance() {
        return INSTANCE;
    }

    @Override
    public BigInteger read(EzyUnmarshaller unmarshaller, Object value) {
        if(value instanceof BigInteger)
            return (BigInteger)value;
        if(value instanceof Number)
            return BigInteger.valueOf(((Number)value).longValue());
        if(value instanceof String)
            return new BigInteger((String)value);
        throw new IllegalArgumentException("can't convert: " + value + " to BigInteger");
    }

}
