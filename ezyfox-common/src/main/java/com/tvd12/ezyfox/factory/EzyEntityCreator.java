package com.tvd12.ezyfox.factory;

import com.tvd12.ezyfox.builder.EzyArrayBuilder;
import com.tvd12.ezyfox.builder.EzyObjectBuilder;
import com.tvd12.ezyfox.entity.EzyArray;
import com.tvd12.ezyfox.entity.EzyObject;

public interface EzyEntityCreator {

    /**
     * create a product.
     *
     * @param <T>         the clazz type
     * @param productType the product type
     * @return the created product
     */
    <T> T create(Class<T> productType);

    EzyObject newObject();

    EzyArray newArray();

    EzyObjectBuilder newObjectBuilder();

    EzyArrayBuilder newArrayBuilder();
}
