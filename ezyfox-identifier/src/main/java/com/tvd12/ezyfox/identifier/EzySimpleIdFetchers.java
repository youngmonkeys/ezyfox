package com.tvd12.ezyfox.identifier;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.tvd12.ezyfox.util.EzyLoggable;

public class EzySimpleIdFetchers extends EzyLoggable implements EzyIdFetchers {

    protected Map<Class<?>, EzyIdFetcher> entityIdFetchers = new ConcurrentHashMap<>();

    protected EzySimpleIdFetchers(Builder builder) {
        this.entityIdFetchers.putAll(builder.entityIdFetchers);
    }

    @Override
    public EzyIdFetcher getIdFetcher(Class<?> clazz) {
        if(entityIdFetchers.containsKey(clazz))
            return entityIdFetchers.get(clazz);
        throw new IllegalArgumentException("has no id fetcher for " + clazz);
    }

    @Override
    public Map<Class<?>, EzyIdFetcher> getIdFetchers() {
        return new HashMap<>(entityIdFetchers);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends EzyIdEncapsulationBuilder<EzyIdFetchers, Builder> {

        protected Map<Class<?>, EzyIdFetcher> entityIdFetchers = new HashMap<>();

        public Builder addIdFetcher(Class<?> clazz, EzyIdFetcher fetcher) {
            this.entityIdFetchers.put(clazz, fetcher);
            return this;
        }

        public Builder addIdFetchers(Map<Class<?>, EzyIdFetcher> fetchers) {
            for (Class<?> key : fetchers.keySet())
                this.addIdFetcher(key, fetchers.get(key));
            return this;
        }

        @Override
        protected EzyIdFetchers newProduct() {
            return new EzySimpleIdFetchers(this);
        }

        @Override
        protected void parseEntityClasses() {
            for (Class<?> entityClass : entityClasses) {
                EzyIdFetcher fetcher = newIdFetcher(entityClass);
                entityIdFetchers.put(entityClass, fetcher);
            }
        }

        protected EzyIdFetcher newIdFetcher(Class<?> clazz) {
            EzyIdFetcherImplementer implementer = newIdFetcherImplementer(clazz);
            return implementer.implement();
        }

        protected EzyIdFetcherImplementer newIdFetcherImplementer(Class<?> clazz) {
            return new EzySimpleIdFetcherImplementer(clazz);
        }

    }}