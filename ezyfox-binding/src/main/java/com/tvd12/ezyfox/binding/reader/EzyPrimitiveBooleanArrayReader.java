package com.tvd12.ezyfox.binding.reader;

public class EzyPrimitiveBooleanArrayReader extends EzyPrimitiveArrayReader {

    private static final EzyPrimitiveBooleanArrayReader INSTANCE = new EzyPrimitiveBooleanArrayReader();

    private EzyPrimitiveBooleanArrayReader() {
    }

    public static EzyPrimitiveBooleanArrayReader getInstance() {
        return INSTANCE;
    }

    @Override
    protected Class<?> getOutType() {
        return boolean.class;
    }

    @Override
    protected Object newArray(int length) {
        return new boolean[length];
    }

    @Override
    protected void setValue(Object array, int index, Object value) {
        ((boolean[])array)[index] = (Boolean)value;
    }
}
