package com.tvd12.ezyfox.io;

public final class EzySingletonCollectionConverter extends EzySimpleCollectionConverter {
    private static final long serialVersionUID = -2019822241062310763L;

    private static final EzySingletonCollectionConverter INSTANCE = new EzySingletonCollectionConverter();

    private EzySingletonCollectionConverter() {
        super(EzySingletonOutputTransformer.getInstance());
    }

    public static EzySingletonCollectionConverter getInstance() {
        return INSTANCE;
    }
}