package com.tvd12.ezyfox.factory;

import com.tvd12.ezyfox.builder.EzyArrayBuilder;
import com.tvd12.ezyfox.builder.EzyObjectBuilder;
import com.tvd12.ezyfox.entity.EzyArray;
import com.tvd12.ezyfox.entity.EzyEmptyArray;
import com.tvd12.ezyfox.entity.EzyEmptyObject;
import com.tvd12.ezyfox.entity.EzyObject;

public final class EzyEntityFactory {

    private static final EzyEntityCreator CREATOR
            = EzySimpleEntityCreator.getInstance();
    public static final EzyArray EMPTY_ARRAY
            = EzyEmptyArray.getInstance();
    public static final EzyObject EMPTY_OBJECT
            = EzyEmptyObject.getInstance();

    private EzyEntityFactory() {
        // do nothing
    }

    public static <T> T create(Class<T> productType) {
        return CREATOR.create(productType);
    }

    public static EzyObject newObject() {
        return CREATOR.newObject();
    }

    public static EzyArray newArray() {
        return CREATOR.newArray();
    }

    public static EzyObjectBuilder newObjectBuilder() {
        return CREATOR.newObjectBuilder();
    }

    public static EzyArrayBuilder newArrayBuilder() {
        return CREATOR.newArrayBuilder();
    }
}
