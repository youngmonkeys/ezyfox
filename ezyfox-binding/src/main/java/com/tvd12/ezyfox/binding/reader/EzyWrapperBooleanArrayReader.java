package com.tvd12.ezyfox.binding.reader;

public class EzyWrapperBooleanArrayReader extends EzyWrapperArrayReader {

    private static final EzyWrapperBooleanArrayReader INSTANCE = new EzyWrapperBooleanArrayReader();

    private EzyWrapperBooleanArrayReader() {
    }

    public static EzyWrapperBooleanArrayReader getInstance() {
        return INSTANCE;
    }

    @Override
    protected Class<?> getOutType() {
        return Boolean.class;
    }

    @Override
    protected Object[] newArray(int length) {
        return new Boolean[length];
    }

}
