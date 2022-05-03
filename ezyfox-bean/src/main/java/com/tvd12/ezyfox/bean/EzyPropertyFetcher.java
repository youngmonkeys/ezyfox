package com.tvd12.ezyfox.bean;

public interface EzyPropertyFetcher {

    <T> T getProperty(Object key, Class<T> outType);

    default <T> T getProperty(Object key, Class<T> outType, T defaultValue) {
        T value = getProperty(key, outType);
        return value != null ? value : defaultValue;

    }
}
