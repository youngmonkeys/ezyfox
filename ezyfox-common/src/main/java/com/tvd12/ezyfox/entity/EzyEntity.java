package com.tvd12.ezyfox.entity;

import com.tvd12.ezyfox.util.EzyProperties;

import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Each model in application should have properties,
 * and we think key/value is good idea.
 *
 * @author tavandung12
 */
public abstract class EzyEntity implements EzyProperties {

    // map of key/value properties of model
    protected final Map<Object, Object> properties
        = new ConcurrentHashMap<>();

    /*
     * (non-Javadoc)
     * @see com.tvd12.ezyfox.entities.EzyFoxProperties#setProperty(java.lang.Object, java.lang.Object)
     */
    @Override
    public void setProperty(Object key, Object value) {
        properties.put(key, value);
    }

    /*
     * (non-Javadoc)
     * @see com.tvd12.ezyfox.entities.EzyFoxProperties#getProperty(java.lang.Object)
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T> T getProperty(Object key) {
        return (T) properties.get(key);
    }

    /*
     * (non-Javadoc)
     * @see com.tvd12.ezyfox.entities.EzyFoxProperties#getProperty(java.lang.Object, java.lang.Class)
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T> T getProperty(Object key, Class<T> clazz) {
        return (T) properties.get(key);
    }

    /**
     * removes the mapping for a key from the map.
     *
     * @param key the key
     * @see java.util.Map#remove(Object)
     */
    @Override
    public void removeProperty(Object key) {
        properties.remove(key);
    }

    /**
     * (non-Javadoc).
     *
     * @see java.util.Map#containsKey(Object)
     */
    @Override
    public boolean containsKey(Object key) {
        return properties.containsKey(key);
    }

    @Override
    public Properties getProperties() {
        Properties prop = new Properties();
        prop.putAll(properties);
        return prop;
    }

    @Override
    public void setProperties(Map<?, ?> map) {
        properties.putAll(map);
    }
}
