package com.tvd12.ezyfox.binding.reader;

public class EzyWrapperLongArrayReader extends EzyWrapperArrayReader {

    private static final EzyWrapperLongArrayReader INSTANCE = new EzyWrapperLongArrayReader();

    private EzyWrapperLongArrayReader() {}

    public static EzyWrapperLongArrayReader getInstance() {
        return INSTANCE;
    }

    @Override
    protected Class<?> getOutType() {
        return Long.class;
    }

    @Override
    protected Object[] newArray(int length) {
        return new Long[length];
    }
}
