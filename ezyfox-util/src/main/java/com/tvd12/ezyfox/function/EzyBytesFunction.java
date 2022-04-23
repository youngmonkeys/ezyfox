package com.tvd12.ezyfox.function;

import java.util.function.Function;

public interface EzyBytesFunction<R> extends Function<byte[], R> {

    @Override
    R apply(byte[] bytes);
}
