package com.tvd12.ezyfox.binding.reader;

public class EzyPrimitiveDoubleArrayReader extends EzyPrimitiveArrayReader {

    private static final EzyPrimitiveDoubleArrayReader INSTANCE = new EzyPrimitiveDoubleArrayReader();

    private EzyPrimitiveDoubleArrayReader() {
    }

    public static EzyPrimitiveDoubleArrayReader getInstance() {
        return INSTANCE;
    }

    @Override
    protected Class<?> getOutType() {
        return double.class;
    }

    @Override
    protected Object newArray(int length) {
        return new double[length];
    }

    @Override
    protected void setValue(Object array, int index, Object value) {
        ((double[])array)[index] = ((Number)value).doubleValue();
    }

}
