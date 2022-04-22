package com.tvd12.ezyfox.binding.reader;

public class EzyWrapperDoubleArrayReader extends EzyWrapperArrayReader {

    private static final EzyWrapperDoubleArrayReader INSTANCE = new EzyWrapperDoubleArrayReader();

    private EzyWrapperDoubleArrayReader() {
    }

    public static EzyWrapperDoubleArrayReader getInstance() {
        return INSTANCE;
    }

    @Override
    protected Class<?> getOutType() {
        return Double.class;
    }

    @Override
    protected Object[] newArray(int length) {
        return new Double[length];
    }
}