package com.tvd12.ezyfox.identifier;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.tvd12.ezyfox.util.EzyLoggable;

public class EzySimpleIdSetters extends EzyLoggable implements EzyIdSetters {

    protected Map<Class<?>, EzyIdSetter> entityIdSetters = new ConcurrentHashMap<>();
    
    protected EzySimpleIdSetters(Builder builder) {
        this.entityIdSetters.putAll(builder.entityIdSetters);
    }
    
    @Override
    public EzyIdSetter getIdSetter(Class<?> clazz) {
        if(entityIdSetters.containsKey(clazz))
            return entityIdSetters.get(clazz);
        throw new IllegalArgumentException("has no id setter for " + clazz);
    }
    
    @Override
    public Map<Class<?>, EzyIdSetter> getIdSetters() {
        return new HashMap<>(entityIdSetters);
    }
    
    public static Builder builder() {
        return new Builder();
    }
    
    public static class Builder extends EzyIdEncapsulationBuilder<EzySimpleIdSetters, Builder> {

        protected Map<Class<?>, EzyIdSetter> entityIdSetters = new HashMap<>();

        public Builder addIdSetter(Class<?> clazz, EzyIdSetter setter) {
            this.entityIdSetters.put(clazz, setter);
            return this;
        }

        public Builder addIdSetters(Map<Class<?>, EzyIdSetter> setters) {
            for (Class<?> key : setters.keySet())
                this.addIdSetter(key, setters.get(key));
            return this;
        }


        @Override
        protected EzySimpleIdSetters newProduct() {
            return new EzySimpleIdSetters(this);
        }
        
        @Override
        protected void parseEntityClasses() {
            for (Class<?> entityClass : entityClasses) {
                EzyIdSetter setter = newIdSetter(entityClass);
                entityIdSetters.put(entityClass, setter);
            }
        }

        protected EzyIdSetter newIdSetter(Class<?> clazz) {
            EzyIdSetterImplementer implementer = newIdSetterImplementer(clazz);
            return implementer.implement();
        }

        protected EzyIdSetterImplementer newIdSetterImplementer(Class<?> clazz) {
            return new EzySimpleIdSetterImplementer(clazz);
        }

    }
}
