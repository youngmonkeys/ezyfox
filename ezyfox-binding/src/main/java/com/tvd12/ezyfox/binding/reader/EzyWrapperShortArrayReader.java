package com.tvd12.ezyfox.binding.reader;

public class EzyWrapperShortArrayReader extends EzyWrapperArrayReader {

    private static final EzyWrapperShortArrayReader INSTANCE = new EzyWrapperShortArrayReader();

    private EzyWrapperShortArrayReader() {}

    public static EzyWrapperShortArrayReader getInstance() {
        return INSTANCE;
    }

    @Override
    protected Class<?> getOutType() {
        return Short.class;
    }

    @Override
    protected Object[] newArray(int length) {
        return new Short[length];
    }
}
