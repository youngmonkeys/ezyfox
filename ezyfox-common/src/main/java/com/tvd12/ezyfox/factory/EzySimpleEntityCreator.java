package com.tvd12.ezyfox.factory;

import com.tvd12.ezyfox.builder.EzyArrayBuilder;
import com.tvd12.ezyfox.builder.EzyObjectBuilder;
import com.tvd12.ezyfox.builder.EzySimpleArrayBuilder;
import com.tvd12.ezyfox.builder.EzySimpleObjectBuilder;
import com.tvd12.ezyfox.entity.EzyArray;
import com.tvd12.ezyfox.entity.EzyArrayList;
import com.tvd12.ezyfox.entity.EzyHashMap;
import com.tvd12.ezyfox.entity.EzyObject;
import com.tvd12.ezyfox.io.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

public final class EzySimpleEntityCreator implements EzyEntityCreator {

    @SuppressWarnings("rawtypes")
    private final Map<Class, Supplier> suppliers;

    private static final EzySimpleEntityCreator INSTANCE =
        new EzySimpleEntityCreator();

    private EzySimpleEntityCreator() {
        this.suppliers = defaultSuppliers();
    }

    public static EzySimpleEntityCreator getInstance() {
        return INSTANCE;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T create(Class<T> productType) {
        Supplier<T> supplier = suppliers.get(productType);
        return supplier.get();
    }

    public EzyObject newObject() {
        return new EzyHashMap(
            getInputTransformer(),
            getOutputTransformer()
        );
    }

    public EzyArray newArray() {
        return new EzyArrayList(
            getInputTransformer(),
            getOutputTransformer(),
            getCollectionConverter()
        );
    }

    public EzyObjectBuilder newObjectBuilder() {
        return new EzySimpleObjectBuilder(
            getInputTransformer(),
            getOutputTransformer());
    }

    public EzyArrayBuilder newArrayBuilder() {
        return new EzySimpleArrayBuilder(
            getInputTransformer(),
            getOutputTransformer(),
            getCollectionConverter()
        );
    }

    private EzyInputTransformer getInputTransformer() {
        return EzySingletonInputTransformer.getInstance();
    }

    private EzyOutputTransformer getOutputTransformer() {
        return EzySingletonOutputTransformer.getInstance();
    }

    private EzyCollectionConverter getCollectionConverter() {
        return EzySingletonCollectionConverter.getInstance();
    }

    @SuppressWarnings("rawtypes")
    private Map<Class, Supplier> defaultSuppliers() {
        Map<Class, Supplier> answer = new ConcurrentHashMap<>();
        answer.put(EzyObject.class, this::newObject);
        answer.put(EzyArray.class, this::newArray);
        answer.put(EzyObjectBuilder.class, this::newObjectBuilder);
        answer.put(EzyArrayBuilder.class, this::newArrayBuilder);
        return answer;
    }
}
