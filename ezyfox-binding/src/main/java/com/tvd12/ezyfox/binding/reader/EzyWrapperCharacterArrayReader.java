package com.tvd12.ezyfox.binding.reader;

public class EzyWrapperCharacterArrayReader extends EzyWrapperArrayReader {

    private static final EzyWrapperCharacterArrayReader INSTANCE = new EzyWrapperCharacterArrayReader();

    private EzyWrapperCharacterArrayReader() {
    }

    public static EzyWrapperCharacterArrayReader getInstance() {
        return INSTANCE;
    }

    @Override
    protected Class<?> getOutType() {
        return Character.class;
    }

    @Override
    protected Object[] newArray(int length) {
        return new Character[length];
    }
}