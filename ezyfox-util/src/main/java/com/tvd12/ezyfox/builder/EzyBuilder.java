package com.tvd12.ezyfox.builder;

public interface EzyBuilder<T> {

    /**
     * build a product.
     *
     * @return the constructed product
     */
    T build();
}
