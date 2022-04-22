package com.tvd12.ezyfox.binding.reader;

public class EzyPrimitiveIntArrayReader extends EzyPrimitiveArrayReader {

    private static final EzyPrimitiveIntArrayReader INSTANCE = new EzyPrimitiveIntArrayReader();

    private EzyPrimitiveIntArrayReader() {
    }

    public static EzyPrimitiveIntArrayReader getInstance() {
        return INSTANCE;
    }

    @Override
    protected Class<?> getOutType() {
        return int.class;
    }

    @Override
    protected Object newArray(int length) {
        return new int[length];
    }

    @Override
    protected void setValue(Object array, int index, Object value) {
        ((int[])array)[index] = ((Number)value).intValue();
    }
}