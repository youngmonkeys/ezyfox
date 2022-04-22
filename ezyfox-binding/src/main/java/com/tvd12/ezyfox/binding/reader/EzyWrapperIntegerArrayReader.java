package com.tvd12.ezyfox.binding.reader;

public class EzyWrapperIntegerArrayReader extends EzyWrapperArrayReader {

    private static final EzyWrapperIntegerArrayReader INSTANCE = new EzyWrapperIntegerArrayReader();

    private EzyWrapperIntegerArrayReader() {
    }

    public static EzyWrapperIntegerArrayReader getInstance() {
        return INSTANCE;
    }

    @Override
    protected Class<?> getOutType() {
        return Integer.class;
    }

    @Override
    protected Object[] newArray(int length) {
        return new Integer[length];
    }

}
