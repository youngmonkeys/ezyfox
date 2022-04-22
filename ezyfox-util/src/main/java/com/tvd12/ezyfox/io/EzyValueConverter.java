package com.tvd12.ezyfox.io;

public interface EzyValueConverter {

    <T> T convert(Object value, Class<T> outType);
}