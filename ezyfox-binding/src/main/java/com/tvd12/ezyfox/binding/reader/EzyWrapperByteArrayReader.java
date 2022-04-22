package com.tvd12.ezyfox.binding.reader;

public class EzyWrapperByteArrayReader extends EzyWrapperArrayReader {

    private static final EzyWrapperByteArrayReader INSTANCE = new EzyWrapperByteArrayReader();

    private EzyWrapperByteArrayReader() {
    }

    public static EzyWrapperByteArrayReader getInstance() {
        return INSTANCE;
    }

    @Override
    protected Class<?> getOutType() {
        return Byte.class;
    }

    @Override
    protected Object[] newArray(int length) {
        return new Byte[length];
    }

}
