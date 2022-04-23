package com.tvd12.ezyfox.properties;

import com.tvd12.ezyfox.io.EzySimpleValueConverter;
import com.tvd12.ezyfox.io.EzyValueConverter;
import com.tvd12.ezyfox.util.EzyLoggable;

import java.util.Map;

@SuppressWarnings({"rawtypes", "unchecked"})
public class EzySimplePropertiesReader
    extends EzyLoggable
    implements EzyPropertiesReader {

    protected final EzyValueConverter conveter;

    public EzySimplePropertiesReader() {
        this(EzySimpleValueConverter.getSingleton());
    }

    public EzySimplePropertiesReader(EzyValueConverter conveter) {
        this.conveter = conveter;
    }

    @Override
    public <T> T get(Map properties, Object key) {
        return (T) properties.get(key);
    }

    @Override
    public <T> T get(Map properties, Object key, Class<T> outType) {
        return conveter.convert(get(properties, key), outType);
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
