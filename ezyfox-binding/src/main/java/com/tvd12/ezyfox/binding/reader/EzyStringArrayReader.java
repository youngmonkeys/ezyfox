package com.tvd12.ezyfox.binding.reader;

public class EzyStringArrayReader extends EzyWrapperArrayReader {

    private static final EzyStringArrayReader INSTANCE = new EzyStringArrayReader();

    private EzyStringArrayReader() {}

    public static EzyStringArrayReader getInstance() {
        return INSTANCE;
    }

    @Override
    protected Class<?> getOutType() {
        return String.class;
    }

    @Override
    protected Object[] newArray(int length) {
        return new String[length];
    }
}
