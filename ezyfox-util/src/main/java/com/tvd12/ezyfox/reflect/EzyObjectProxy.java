package com.tvd12.ezyfox.reflect;

import com.tvd12.ezyfox.builder.EzyBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class EzyObjectProxy {

    protected final Map<String, String> propertyKeys;
    protected final Map<String, Class<?>> propertyTypes;
    protected final Map<String, Function<Object, Object>> getters;
    protected final Map<String, BiConsumer<Object, Object>> setters;

    protected EzyObjectProxy(Builder builder) {
        this.setters = builder.setters;
        this.getters = builder.getters;
        this.propertyKeys = builder.propertyKeys;
        this.propertyTypes = builder.propertyTypes;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getPropertyName(String key) {
        return propertyKeys.get(key);
    }

    public Class<?> getPropertyType(String property) {
        return propertyTypes.get(property);
    }

    @SuppressWarnings("unchecked")
    public <T> T getProperty(Object object, String property) {
        Function<Object, Object> getter = getters.get(property);
        if (getter != null) {
            return (T) getter.apply(object);
        }
        return null;
    }

    public void setProperty(Object object, String property, Object value) {
        BiConsumer<Object, Object> setter = setters.get(property);
        if (setter != null) {
            setter.accept(object, value);
        }
    }

    public static class Builder implements EzyBuilder<EzyObjectProxy> {

        protected Map<String, String> propertyKeys;
        protected Map<String, Class<?>> propertyTypes;
        protected Map<String, Function<Object, Object>> getters;
        protected Map<String, BiConsumer<Object, Object>> setters;

        public Builder() {
            this.setters = new HashMap<>();
            this.getters = new HashMap<>();
            this.propertyKeys = new HashMap<>();
            this.propertyTypes = new HashMap<>();
        }

        public Builder propertyKey(String key, String property) {
            this.propertyKeys.put(key, property);
            return this;
        }

        public Builder propertyKey(Map<String, String> propertyKeys) {
            this.propertyKeys.putAll(propertyKeys);
            return this;
        }

        public Builder addPropertyType(String property, Class<?> type) {
            propertyTypes.put(property, type);
            return this;
        }

        public Builder addPropertyTypes(Map<String, Class<?>> propertyTypes) {
            this.propertyTypes.putAll(propertyTypes);
            return this;
        }

        public Builder addGetters(Map<String, Function<Object, Object>> setters) {
            this.getters.putAll(setters);
            return this;
        }

        public Builder addGetter(String property, Function<Object, Object> getter) {
            this.getters.put(property, getter);
            return this;
        }

        public Builder addSetters(Map<String, BiConsumer<Object, Object>> setters) {
            this.setters.putAll(setters);
            return this;
        }

        public Builder addSetter(String property, BiConsumer<Object, Object> setter) {
            this.setters.put(property, setter);
            return this;
        }

        @Override
        public EzyObjectProxy build() {
            for (String key : propertyKeys.keySet()) {
                String property = propertyKeys.get(key);
                getters.put(key, getters.get(property));
                setters.put(key, setters.get(property));
                propertyTypes.put(key, propertyTypes.get(property));
            }
            return new EzyObjectProxy(this);
        }

    }
}
