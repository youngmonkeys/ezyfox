package com.tvd12.ezyfox.builder;

public interface EzyBuilder<T> {

    /**
     * build a project.
     *
     * @return the constructed product
     */
    T build();
}
