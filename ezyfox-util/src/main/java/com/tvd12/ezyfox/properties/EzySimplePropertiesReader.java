package com.tvd12.ezyfox.properties;

import com.tvd12.ezyfox.io.EzySimpleValueConverter;
import com.tvd12.ezyfox.io.EzyValueConverter;
import com.tvd12.ezyfox.util.EzyLoggable;

import java.util.Map;

@SuppressWarnings({"rawtypes"})
public class EzySimplePropertiesReader
    extends EzyLoggable
    implements EzyPropertiesReader {

    protected final EzyValueConverter converter;

    public EzySimplePropertiesReader() {
        this(EzySimpleValueConverter.getSingleton());
    }

    public EzySimplePropertiesReader(EzyValueConverter converter) {
        this.converter = converter;
    }

    @Override
    public <T> T get(Map properties, Object key) {
        Object value = properties.get(key);
        if (value == null) {
            value = System.getProperty(key.toString());
        }
        if (value == null) {
            value = System.getenv(key.toString());
        }
        //noinspection unchecked
        return (T) value;
    }

    @Override
    public <T> T get(Map properties, Object key, Class<T> outType) {
        return converter.convert(get(properties, key), outType);
    }

    @Override
    public <T> T get(Map properties, Object key, T defValue) {
        return containsKey(properties, key) ? get(properties, key) : defValue;
    }

    @Override
    public <T> T get(Map properties, Object key, Class<T> outType, T defValue) {
        return containsKey(properties, key) ? get(properties, key, outType) : defValue;
    }

    @Override
    public boolean containsKey(Map properties, Object key) {
        return properties.containsKey(key);
    }
}
