package com.tvd12.ezyfox.binding.reader;

public class EzyWrapperFloatArrayReader extends EzyWrapperArrayReader {

    private static final EzyWrapperFloatArrayReader INSTANCE = new EzyWrapperFloatArrayReader();

    private EzyWrapperFloatArrayReader() {
    }

    public static EzyWrapperFloatArrayReader getInstance() {
        return INSTANCE;
    }

    @Override
    protected Class<?> getOutType() {
        return Float.class;
    }

    @Override
    protected Object[] newArray(int length) {
        return new Float[length];
    }

}
